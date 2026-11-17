package fioshi.com.github.SmartCash.spent.application.port.in;

import java.time.Month;

public interface ExportPdfUseCase {

    byte[] exportAllMonths(Long userId);

    byte[] exportSingleMonth(Long userId, Month month, int year);
}
