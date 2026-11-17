package fioshi.com.github.SmartCash.spent.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.ExportPdfUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.GetSpentResumeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.ListMonthlySpentsUseCase;
import fioshi.com.github.SmartCash.spent.application.port.out.PdfGeneratorPort;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;

import java.time.Month;

public class ExportPdfUseCaseImpl implements ExportPdfUseCase {

    private final SpentUserLookupPort userLookupPort;
    private final ListMonthlySpentsUseCase listMonthlySpentsUseCase;
    private final GetSpentResumeUseCase getSpentResumeUseCase;
    private final PdfGeneratorPort pdfGeneratorPort;

    public ExportPdfUseCaseImpl(
            SpentUserLookupPort userLookupPort,
            ListMonthlySpentsUseCase listMonthlySpentsUseCase,
            GetSpentResumeUseCase getSpentResumeUseCase,
            PdfGeneratorPort pdfGeneratorPort
    ) {
        this.userLookupPort = userLookupPort;
        this.listMonthlySpentsUseCase = listMonthlySpentsUseCase;
        this.getSpentResumeUseCase = getSpentResumeUseCase;
        this.pdfGeneratorPort = pdfGeneratorPort;
    }

    @Override
    public byte[] exportAllMonths(Long userId) {
        var user = userLookupPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        var monthlyExpenses = listMonthlySpentsUseCase.execute(userId);
        if (monthlyExpenses.isEmpty()) {
            throw new BusinessException("Nenhum gasto encontrado para este usuario");
        }

        return pdfGeneratorPort.exportMonthlyExpensesToPdf(monthlyExpenses, user);
    }

    @Override
    public byte[] exportSingleMonth(Long userId, Month month, int year) {
        var user = userLookupPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado"));

        var monthlyExpense = getSpentResumeUseCase.execute(userId, month, year);
        return pdfGeneratorPort.exportSingleMonthToPdf(monthlyExpense, user);
    }
}
