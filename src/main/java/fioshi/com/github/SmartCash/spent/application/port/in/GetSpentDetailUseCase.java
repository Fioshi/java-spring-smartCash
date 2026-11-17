package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;

public interface GetSpentDetailUseCase {

    Spent execute(Long userId, Long spentId);
}
