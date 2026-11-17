package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertAdditionalIncomeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;
import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

public class InsertAdditionalIncomeUseCaseImpl implements InsertAdditionalIncomeUseCase {

    private final AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort;
    private final SpentUserLookupPort userLookupPort;
    private final TransactionPort transactionPort;

    public InsertAdditionalIncomeUseCaseImpl(
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort,
            SpentUserLookupPort userLookupPort,
            TransactionPort transactionPort
    ) {
        this.additionalIncomeRepositoryPort = additionalIncomeRepositoryPort;
        this.userLookupPort = userLookupPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public AdditionalIncome execute(InsertAdditionalIncomeCommand command) {
        return transactionPort.execute(() -> {
            var user = userLookupPort.findById(command.userId())
                    .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

            var income = AdditionalIncome.createNew(
                    command.valor(),
                    command.descricao(),
                    command.ano(),
                    command.mes(),
                    user
            );

            return additionalIncomeRepositoryPort.save(income);
        });
    }
}
