package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentCategorieDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoDetail;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;
import fioshi.com.github.SmartCash.spent.repository.MonthlyExpenseRepository;
import fioshi.com.github.SmartCash.spent.repository.SpentRepository;
import fioshi.com.github.SmartCash.spent.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class SpentServiceImp implements SpentService {

    private final SpentRepository spentRepository;
    private final UserRepository userRepository;
    private final MonthlyExpenseRepository monthlyExpenseRepository;
    private final List<SpentValidation> validation;

    public SpentServiceImp(
            SpentRepository spentRepository,
            UserRepository userRepository,
            MonthlyExpenseRepository monthlyExpenseRepository,
            List<SpentValidation> validation
    ) {
        this.spentRepository = spentRepository;
        this.userRepository = userRepository;
        this.monthlyExpenseRepository = monthlyExpenseRepository;
        this.validation = validation;
    }

    @Override
    @Transactional(rollbackOn = BusinessException.class)
    public void insertSpent(SpentDtoInsert dtoInsert) {

        validation.forEach(validation -> validation.validation(dtoInsert));

        var currentMonth = LocalDate.now().getMonth();
        var currentYear = Year.now().getValue();

        var user = userRepository.findById(dtoInsert.idUser())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        var existingMonthlyExpenses = monthlyExpenseRepository
                .findAllByUserId(dtoInsert.idUser());

        var recurringSpents = spentRepository
                .findAllByUserIdAndIsMonthlyTrue(dtoInsert.idUser());

        var newSpent = new Spent(dtoInsert, user);

        spentRepository.save(newSpent);

        int monthsToProcess = newSpent.isMonthly()
                ? existingMonthlyExpenses.size()
                : newSpent.getInstallments();

        for (int i = 0; i < monthsToProcess; i++) {
            if (currentMonth.plus(i) == Month.JANUARY) {
                currentYear = currentYear + 1;
            }

            int monthOffset = i;
            int targetYear = currentYear;

            var optionalMonthlyExpense = existingMonthlyExpenses
                    .stream()
                    .filter(expense -> expense.getMonth() == currentMonth.plus(monthOffset)
                            && expense.getYear() == targetYear)
                    .findFirst();

            var targetMonthlyExpense = optionalMonthlyExpense.orElseGet(() ->
                    new MonthlyExpense(
                            targetYear,
                            currentMonth.plus(monthOffset),
                            new LinkedList<>(),
                            user
                    )
            );

            existingMonthlyExpenses.add(targetMonthlyExpense);

            targetMonthlyExpense.addSpent(newSpent);
            monthlyExpenseRepository.save(targetMonthlyExpense);
        }

        recurringSpents.forEach(recurring -> {
            existingMonthlyExpenses.forEach(monthlyExpense -> {
                if (!monthlyExpense.getSpents().contains(recurring)) {
                    monthlyExpense.addSpent(recurring);
                }
            });
        });
    }

    @Override
    public List<MonthlySpentDtoList> listMontlhySpenceFiltered(Long id) {
        return monthlyExpenseRepository
                .findAllByUserId(id)
                .stream()
                .map(MonthlySpentDtoList::new)
                .toList();
    }

    @Override
    @Transactional()
    public MonthlySpentDtoList getSpentResume(Long userId, Month month, int year) {
        var monthlyExpense = monthlyExpenseRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (monthlyExpense.isPresent())
            return new MonthlySpentDtoList(monthlyExpense.get());
        throw new BusinessException("Nao ha registros desse mes para esse usuario");
    }

    @Override
    public SpentDtoDetail getSpentDetail(Long userId, Long spentId) {
        var spent = spentRepository.findByIdAndUserId(spentId, userId);
        if (spent.isPresent())
            return new SpentDtoDetail(spent.get());
        throw new BusinessException("Transacao nao encontrada");
    }

    @Override
    public List<SpentCategorieDtoList> getCategories() {
        return Arrays.stream(SpentCategorie.values()).map(SpentCategorieDtoList::new).toList();
    }
}
