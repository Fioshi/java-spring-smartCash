package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.GetSpentDetailUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentRepositoryPort;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;

public class GetSpentDetailUseCaseImpl implements GetSpentDetailUseCase {

    private final SpentRepositoryPort spentRepositoryPort;

    public GetSpentDetailUseCaseImpl(SpentRepositoryPort spentRepositoryPort) {
        this.spentRepositoryPort = spentRepositoryPort;
    }

    @Override
    public Spent execute(Long userId, Long spentId) {
        return spentRepositoryPort.findByIdAndUserId(spentId, userId)
                .orElseThrow(() -> new BusinessException("Transacao nao encontrada"));
    }
}
