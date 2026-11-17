package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertSpentUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.MonthlyExpenseRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;
import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;
import fioshi.com.github.SmartCash.spent.application.usecase.validation.SpentValidation;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public class InsertSpentUseCaseImpl implements InsertSpentUseCase {

    private final SpentRepositoryPort spentRepositoryPort;
    private final SpentUserLookupPort userLookupPort;
    private final MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort;
    private final List<SpentValidation> validations;
    private final TransactionPort transactionPort;

    public InsertSpentUseCaseImpl(
            SpentRepositoryPort spentRepositoryPort,
            SpentUserLookupPort userLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            List<SpentValidation> validations,
            TransactionPort transactionPort
    ) {
        this.spentRepositoryPort = spentRepositoryPort;
        this.userLookupPort = userLookupPort;
        this.monthlyExpenseRepositoryPort = monthlyExpenseRepositoryPort;
        this.validations = validations;
        this.transactionPort = transactionPort;
    }

    @Override
    public Spent execute(InsertSpentCommand command) {
        return transactionPort.execute(() -> insertInternal(command));
    }

    private Spent insertInternal(InsertSpentCommand command) {
        validations.forEach(validation -> validation.validate(command));

        var currentMonth = LocalDate.now().getMonth();
        var currentYear = Year.now().getValue();

        var user = userLookupPort.findById(command.userId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        var existingMonthlyExpenses = new ArrayList<>(
                monthlyExpenseRepositoryPort.findAllByUserId(command.userId())
        );

        var recurringSpents = spentRepositoryPort.findAllByUserIdAndIsMonthlyTrue(command.userId());

        var newSpent = spentRepositoryPort.save(Spent.createNew(
                command.typeSpent(),
                command.installments(),
                command.value(),
                command.place(),
                command.categorie(),
                command.item(),
                user
        ));

        int monthsToProcess = newSpent.isMonthly()
                ? existingMonthlyExpenses.size()
                : newSpent.getInstallments();

        Set<MonthlyExpense> modifiedExpenses = Collections.newSetFromMap(new IdentityHashMap<>());

        for (int i = 0; i < monthsToProcess; i++) {
            if (currentMonth.plus(i) == Month.JANUARY) {
                currentYear = currentYear + 1;
            }

            int monthOffset = i;
            int targetYear = currentYear;
            Month targetMonth = currentMonth.plus(monthOffset);

            var optionalMonthlyExpense = existingMonthlyExpenses.stream()
                    .filter(expense -> expense.getMonth() == targetMonth && expense.getYear() == targetYear)
                    .findFirst();

            var targetMonthlyExpense = optionalMonthlyExpense.orElseGet(() -> {
                var created = MonthlyExpense.createNew(targetYear, targetMonth, user);
                existingMonthlyExpenses.add(created);
                return created;
            });

            targetMonthlyExpense.addSpent(newSpent);
            modifiedExpenses.add(targetMonthlyExpense);
        }

        for (Spent recurring : recurringSpents) {
            for (MonthlyExpense monthlyExpense : existingMonthlyExpenses) {
                if (!monthlyExpense.getSpents().contains(recurring)) {
                    monthlyExpense.addSpent(recurring);
                    modifiedExpenses.add(monthlyExpense);
                }
            }
        }

        for (MonthlyExpense modified : modifiedExpenses) {
            monthlyExpenseRepositoryPort.save(modified);
        }

        return newSpent;
    }
}
