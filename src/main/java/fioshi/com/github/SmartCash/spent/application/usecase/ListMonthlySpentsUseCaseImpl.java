package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.ListMonthlySpentsUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.MonthlyExpenseRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.util.List;

public class ListMonthlySpentsUseCaseImpl implements ListMonthlySpentsUseCase {

    private final SpentUserLookupPort userLookupPort;
    private final MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort;
    private final AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort;

    public ListMonthlySpentsUseCaseImpl(
            SpentUserLookupPort userLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort
    ) {
        this.userLookupPort = userLookupPort;
        this.monthlyExpenseRepositoryPort = monthlyExpenseRepositoryPort;
        this.additionalIncomeRepositoryPort = additionalIncomeRepositoryPort;
    }

    @Override
    public List<MonthlySpentSummary> execute(Long userId) {
        var user = userLookupPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        double salarioMensal = user.getSalarioMensal() != null ? user.getSalarioMensal() : 0.0;

        return monthlyExpenseRepositoryPort.findAllByUserId(userId).stream()
                .map(monthlyExpense -> {
                    double additionalIncome = additionalIncomeRepositoryPort
                            .findByUserIdAndAnoAndMes(userId, monthlyExpense.getYear(), monthlyExpense.getMonth())
                            .stream()
                            .mapToDouble(AdditionalIncome::getValor)
                            .sum();
                    return MonthlySpentSummary.from(monthlyExpense, salarioMensal, additionalIncome);
                })
                .toList();
    }
}
