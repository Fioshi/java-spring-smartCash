package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.GetSpentResumeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.MonthlyExpenseRepositoryPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.time.Month;

public class GetSpentResumeUseCaseImpl implements GetSpentResumeUseCase {

    private final SpentUserLookupPort userLookupPort;
    private final MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort;
    private final AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort;

    public GetSpentResumeUseCaseImpl(
            SpentUserLookupPort userLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort
    ) {
        this.userLookupPort = userLookupPort;
        this.monthlyExpenseRepositoryPort = monthlyExpenseRepositoryPort;
        this.additionalIncomeRepositoryPort = additionalIncomeRepositoryPort;
    }

    @Override
    public MonthlySpentSummary execute(Long userId, Month month, int year) {
        var user = userLookupPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        double salarioMensal = user.getSalarioMensal() != null ? user.getSalarioMensal() : 0.0;

        double additionalIncome = additionalIncomeRepositoryPort
                .findByUserIdAndAnoAndMes(userId, year, month)
                .stream()
                .mapToDouble(AdditionalIncome::getValor)
                .sum();

        var monthlyExpense = monthlyExpenseRepositoryPort
                .findByUserIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new BusinessException("Nao ha registros desse mes para esse usuario"));

        return MonthlySpentSummary.from(monthlyExpense, salarioMensal, additionalIncome);
    }
}
