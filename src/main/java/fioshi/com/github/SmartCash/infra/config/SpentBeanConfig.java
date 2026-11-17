package fioshi.com.github.SmartCash.infra.config;

import fioshi.com.github.SmartCash.spent.application.port.in.*;
import fioshi.com.github.SmartCash.spent.application.port.out.*;
import fioshi.com.github.SmartCash.spent.application.usecase.*;
import fioshi.com.github.SmartCash.spent.application.usecase.validation.InstallmentsValidation;
import fioshi.com.github.SmartCash.spent.application.usecase.validation.SpentValidation;
import fioshi.com.github.SmartCash.spent.application.usecase.validation.ValueValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpentBeanConfig {

    @Bean
    public SpentValidation spentValidationInstallments() {
        return new InstallmentsValidation();
    }

    @Bean
    public SpentValidation spentValidationValue() {
        return new ValueValidation();
    }

    @Bean
    public InsertSpentUseCase insertSpentUseCase(
            SpentRepositoryPort spentRepositoryPort,
            SpentUserLookupPort spentUserLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            List<SpentValidation> validations,
            TransactionPort transactionPort
    ) {
        return new InsertSpentUseCaseImpl(
                spentRepositoryPort,
                spentUserLookupPort,
                monthlyExpenseRepositoryPort,
                validations,
                transactionPort
        );
    }

    @Bean
    public ListMonthlySpentsUseCase listMonthlySpentsUseCase(
            SpentUserLookupPort spentUserLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort
    ) {
        return new ListMonthlySpentsUseCaseImpl(
                spentUserLookupPort,
                monthlyExpenseRepositoryPort,
                additionalIncomeRepositoryPort
        );
    }

    @Bean
    public GetSpentResumeUseCase getSpentResumeUseCase(
            SpentUserLookupPort spentUserLookupPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort
    ) {
        return new GetSpentResumeUseCaseImpl(
                spentUserLookupPort,
                monthlyExpenseRepositoryPort,
                additionalIncomeRepositoryPort
        );
    }

    @Bean
    public GetSpentDetailUseCase getSpentDetailUseCase(SpentRepositoryPort spentRepositoryPort) {
        return new GetSpentDetailUseCaseImpl(spentRepositoryPort);
    }

    @Bean
    public GetCategoriesUseCase getCategoriesUseCase() {
        return new GetCategoriesUseCaseImpl();
    }

    @Bean
    public UpdateSpentUseCase updateSpentUseCase(
            SpentRepositoryPort spentRepositoryPort,
            MonthlyExpenseRepositoryPort monthlyExpenseRepositoryPort,
            TransactionPort transactionPort
    ) {
        return new UpdateSpentUseCaseImpl(
                spentRepositoryPort,
                monthlyExpenseRepositoryPort,
                transactionPort
        );
    }

    @Bean
    public DeleteSpentUseCase deleteSpentUseCase(
            SpentRepositoryPort spentRepositoryPort,
            TransactionPort transactionPort
    ) {
        return new DeleteSpentUseCaseImpl(spentRepositoryPort, transactionPort);
    }

    @Bean
    public ExportPdfUseCase exportPdfUseCase(
            SpentUserLookupPort spentUserLookupPort,
            ListMonthlySpentsUseCase listMonthlySpentsUseCase,
            GetSpentResumeUseCase getSpentResumeUseCase,
            PdfGeneratorPort pdfGeneratorPort
    ) {
        return new ExportPdfUseCaseImpl(
                spentUserLookupPort,
                listMonthlySpentsUseCase,
                getSpentResumeUseCase,
                pdfGeneratorPort
        );
    }

    @Bean
    public InsertAdditionalIncomeUseCase insertAdditionalIncomeUseCase(
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort,
            SpentUserLookupPort spentUserLookupPort,
            TransactionPort transactionPort
    ) {
        return new InsertAdditionalIncomeUseCaseImpl(
                additionalIncomeRepositoryPort,
                spentUserLookupPort,
                transactionPort
        );
    }

    @Bean
    public ListAdditionalIncomeUseCase listAdditionalIncomeUseCase(
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort
    ) {
        return new ListAdditionalIncomeUseCaseImpl(additionalIncomeRepositoryPort);
    }

    @Bean
    public DeleteAdditionalIncomeUseCase deleteAdditionalIncomeUseCase(
            AdditionalIncomeRepositoryPort additionalIncomeRepositoryPort,
            TransactionPort transactionPort
    ) {
        return new DeleteAdditionalIncomeUseCaseImpl(
                additionalIncomeRepositoryPort,
                transactionPort
        );
    }
}
