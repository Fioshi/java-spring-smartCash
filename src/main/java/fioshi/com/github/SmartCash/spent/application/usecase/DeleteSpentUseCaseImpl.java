package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.DeleteSpentUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;

public class DeleteSpentUseCaseImpl implements DeleteSpentUseCase {

    private final SpentRepositoryPort spentRepositoryPort;
    private final TransactionPort transactionPort;

    public DeleteSpentUseCaseImpl(
            SpentRepositoryPort spentRepositoryPort,
            TransactionPort transactionPort
    ) {
        this.spentRepositoryPort = spentRepositoryPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(Long id) {
        transactionPort.execute(() -> {
            var spent = spentRepositoryPort.findById(id)
                    .orElseThrow(() -> new BusinessException("Gasto nao encontrado"));
            spentRepositoryPort.delete(spent);
        });
    }
}
