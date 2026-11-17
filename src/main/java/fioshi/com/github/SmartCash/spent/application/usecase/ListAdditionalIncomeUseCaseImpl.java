package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.spent.application.port.in.ListAdditionalIncomeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.util.List;

public class ListAdditionalIncomeUseCaseImpl implements ListAdditionalIncomeUseCase {

    private final AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort;

    public ListAdditionalIncomeUseCaseImpl(AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort) {
        this.additionalIncomeRepositoryPort = additionalIncomeRepositoryPort;
    }

    @Override
    public List<AdditionalIncome> execute(Long userId) {
        return additionalIncomeRepositoryPort.findByUserId(userId);
    }
}
