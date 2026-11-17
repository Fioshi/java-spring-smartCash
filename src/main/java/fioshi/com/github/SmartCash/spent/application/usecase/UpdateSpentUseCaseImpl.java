package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.UpdateSpentUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.MonthlyExpenseRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;

public class UpdateSpentUseCaseImpl implements UpdateSpentUseCase {

    private final SpentRepositoryPort spentRepositoryPort;
    private final MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort;
    private final TransactionPort transactionPort;

    public UpdateSpentUseCaseImpl(
            SpentRepositoryPort spentRepositoryPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            TransactionPort transactionPort
    ) {
        this.spentRepositoryPort = spentRepositoryPort;
        this.monthlyExpenseRepositoryPort = monthlyExpenseRepositoryPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public Spent execute(Long id, UpdateSpentCommand command) {
        return transactionPort.execute(() -> updateInternal(id, command));
    }

    private Spent updateInternal(Long id, UpdateSpentCommand command) {
        var spent = spentRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("Transacao nao encontrada"));

        var monthList = monthlyExpenseRepositoryPort.findAllBySpentId(id);

        if (command.installments() < monthList.size()) {
            for (int i = command.installments(); i < monthList.size(); i++) {
                var monthlyExpense = monthList.get(i);
                monthlyExpense.removeSpent(spent);
                monthlyExpenseRepositoryPort.save(monthlyExpense);
            }
        }

        spent.update(
                command.typeSpent(),
                command.installments(),
                command.value(),
                command.place(),
                command.item(),
                command.categorie()
        );

        return spentRepositoryPort.save(spent);
    }
}
