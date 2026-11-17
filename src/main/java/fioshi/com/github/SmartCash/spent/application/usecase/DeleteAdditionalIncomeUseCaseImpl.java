package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.DeleteAdditionalIncomeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;

public class DeleteAdditionalIncomeUseCaseImpl implements DeleteAdditionalIncomeUseCase {

    private final AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort;
    private final TransactionPort transactionPort;

    public DeleteAdditionalIncomeUseCaseImpl(
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort,
            TransactionPort transactionPort
    ) {
        this.additionalIncomeRepositoryPort = additionalIncomeRepositoryPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(Long id) {
        transactionPort.execute(() -> {
            var income = additionalIncomeRepositoryPort.findById(id)
                    .orElseThrow(() -> new BusinessException("Rendimento extra nao encontrado"));
            additionalIncomeRepositoryPort.delete(income);
        });
    }
}
