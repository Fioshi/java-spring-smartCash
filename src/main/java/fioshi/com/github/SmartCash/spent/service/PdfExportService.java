package fioshi.com.github.SmartCash.spent.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoList;
import fioshi.com.github.SmartCash.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfExportService {

    private static final DeviceRgb HEADER_COLOR = new DeviceRgb(41, 128, 185);
    private static final DeviceRgb TOTAL_COLOR = new DeviceRgb(52, 152, 219);
    private static final DeviceRgb ALTERNATE_ROW_COLOR = new DeviceRgb(236, 240, 241);

    public byte[] exportMonthlyExpensesToPdf(List<MonthlySpentDtoList> monthlyExpenses, User user) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            addDocumentHeader(document, user, "Relatório Completo de Gastos");

            for (MonthlySpentDtoList monthlyExpense : monthlyExpenses) {
                addMonthlySection(document, monthlyExpense);
            }

            addGeneralSummary(document, monthlyExpenses);

            addFooter(document);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }

        return baos.toByteArray();
    }

    public byte[] exportSingleMonthToPdf(MonthlySpentDtoList monthlyExpense, User user) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            String title = String.format("Relatório de Gastos - %s/%d",
                monthlyExpense.month(), monthlyExpense.year());
            addDocumentHeader(document, user, title);

            addMonthlySection(document, monthlyExpense);

            addCategoryAnalysis(document, monthlyExpense);

            addFooter(document);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }

        return baos.toByteArray();
    }

    private void addDocumentHeader(Document document, User user, String title) {
        Paragraph titleParagraph = new Paragraph(title)
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(titleParagraph);

        Paragraph userInfo = new Paragraph(String.format("Usuário: %s %s", user.getNome(), user.getSurname()))
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
        document.add(userInfo);

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Paragraph dateInfo = new Paragraph(String.format("Gerado em: %s", currentDate))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(dateInfo);

        document.add(new Paragraph("\n"));
    }

    private void addMonthlySection(Document document, MonthlySpentDtoList monthlyExpense) {
        
        Paragraph monthTitle = new Paragraph(String.format("%s/%d", 
                monthlyExpense.month(), monthlyExpense.year()))
                .setFontSize(16)
                .setBold()
                .setMarginTop(15)
                .setMarginBottom(10);
        document.add(monthTitle);

        if (monthlyExpense.rendimentosExtras() > 0) {
            Paragraph rendimentosInfo = new Paragraph(String.format("Rendimentos Extras: R$ %.2f", 
                    monthlyExpense.rendimentosExtras()))
                    .setFontSize(11)
                    .setItalic()
                    .setFontColor(new DeviceRgb(46, 204, 113))
                    .setMarginBottom(5);
            document.add(rendimentosInfo);
        }

        
        float[] columnWidths = {2, 1, 1, 1, 3};
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth();
        
        addTableHeader(table, new String[]{"Categoria", "Tipo", "Parcelas", "Valor", "Item"});

        int rowIndex = 0;
        for (SpentDtoList spent : monthlyExpense.spentDtoList()) {
            boolean isAlternate = rowIndex % 2 == 1;
            addTableRow(table, spent, isAlternate);
            rowIndex++;
        }

        
        addTotalRow(table, monthlyExpense.total());
        addSaldoRestanteRow(table, monthlyExpense.saldoRestante());

        document.add(table);
        document.add(new Paragraph("\n"));
    }

    private void addTableHeader(Table table, String[] headers) {
        for (String header : headers) {
            Cell cell = new Cell()
                    .add(new Paragraph(header).setBold().setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(HEADER_COLOR)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(8);
            table.addHeaderCell(cell);
        }
    }

    private void addTableRow(Table table, SpentDtoList spent, boolean isAlternate) {
        DeviceRgb bgColor = isAlternate ? ALTERNATE_ROW_COLOR : new DeviceRgb(255, 255, 255);

        
        table.addCell(createCell(spent.categorie().getDescricao(), bgColor));

        
        table.addCell(createCell(spent.type().toString(), bgColor));

        
        String installments = spent.installments() > 1 ? 
                String.valueOf(spent.installments()) : "-";
        table.addCell(createCell(installments, bgColor));

        
        table.addCell(createCell(String.format("R$ %.2f", spent.valueInstallment()), bgColor));

        
        table.addCell(createCell(spent.item() != null ? spent.item() : "-", bgColor));
    }

    private Cell createCell(String content, Color bgColor) {
        return new Cell()
                .add(new Paragraph(content))
                .setBackgroundColor(bgColor)
                .setPadding(5);
    }

    private void addTotalRow(Table table, double total) {
        
        for (int i = 0; i < 3; i++) {
            table.addCell(new Cell()
                    .setBackgroundColor(TOTAL_COLOR)
                    .setBorder(null));
        }

        
        table.addCell(new Cell()
                .add(new Paragraph("TOTAL:").setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(TOTAL_COLOR)
                .setTextAlignment(TextAlignment.RIGHT)
                .setPadding(8));

        
        table.addCell(new Cell()
                .add(new Paragraph(String.format("R$ %.2f", total))
                        .setBold()
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(TOTAL_COLOR)
                .setPadding(8));
    }

    private void addSaldoRestanteRow(Table table, double saldoRestante) {
        DeviceRgb saldoColor = saldoRestante >= 0 ? 
                new DeviceRgb(46, 204, 113) : 
                new DeviceRgb(231, 76, 60);
        
        for (int i = 0; i < 3; i++) {
            table.addCell(new Cell()
                    .setBackgroundColor(saldoColor)
                    .setBorder(null));
        }

        table.addCell(new Cell()
                .add(new Paragraph("SALDO RESTANTE:").setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(saldoColor)
                .setTextAlignment(TextAlignment.RIGHT)
                .setPadding(8));

        table.addCell(new Cell()
                .add(new Paragraph(String.format("R$ %.2f", saldoRestante))
                        .setBold()
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(saldoColor)
                .setPadding(8));
    }

    private void addCategoryAnalysis(Document document, MonthlySpentDtoList monthlyExpense) {
        document.add(new Paragraph("Análise por Categoria")
                .setFontSize(14)
                .setBold()
                .setMarginTop(20)
                .setMarginBottom(10));

        
        var categoryTotals = monthlyExpense.spentDtoList().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        SpentDtoList::categorie,
                        java.util.stream.Collectors.summingDouble(SpentDtoList::valueInstallment)
                ));

        
        float[] columnWidths = {3, 2, 2};
        Table categoryTable = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth();

        addTableHeader(categoryTable, new String[]{"Categoria", "Total", "% do Total"});

        categoryTotals.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    double percentage = (entry.getValue() / monthlyExpense.total()) * 100;
                    
                    categoryTable.addCell(createCell(entry.getKey().getDescricao(), ColorConstants.WHITE));
                    categoryTable.addCell(createCell(String.format("R$ %.2f", entry.getValue()), ColorConstants.WHITE));
                    categoryTable.addCell(createCell(String.format("%.1f%%", percentage), ColorConstants.WHITE));
                });

        document.add(categoryTable);
        
        if (monthlyExpense.rendimentosExtras() > 0) {
            document.add(new Paragraph(String.format("\nRendimentos Extras: R$ %.2f", monthlyExpense.rendimentosExtras()))
                    .setFontSize(12)
                    .setBold()
                    .setFontColor(new DeviceRgb(46, 204, 113))
                    .setMarginTop(10));
        }
        
        document.add(new Paragraph(String.format("Saldo Restante: R$ %.2f", monthlyExpense.saldoRestante()))
                .setFontSize(12)
                .setBold()
                .setMarginTop(5));
    }

    private void addGeneralSummary(Document document, List<MonthlySpentDtoList> monthlyExpenses) {
        document.add(new Paragraph("Resumo Geral")
                .setFontSize(16)
                .setBold()
                .setMarginTop(30)
                .setMarginBottom(10));

        double totalGeral = monthlyExpenses.stream()
                .mapToDouble(MonthlySpentDtoList::total)
                .sum();

        double mediaMensal = totalGeral / monthlyExpenses.size();

        
        var maxMonth = monthlyExpenses.stream()
                .max((m1, m2) -> Double.compare(m1.total(), m2.total()))
                .orElse(null);

        var minMonth = monthlyExpenses.stream()
                .min((m1, m2) -> Double.compare(m1.total(), m2.total()))
                .orElse(null);

        
        float[] columnWidths = {3, 2};
        Table summaryTable = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth();

            summaryTable.addCell(createCell("Total Geral:", DeviceRgb.WHITE).setBold());
        summaryTable.addCell(createCell(String.format("R$ %.2f", totalGeral), ColorConstants.WHITE));

        summaryTable.addCell(createCell("Média Mensal:", ColorConstants.WHITE).setBold());
        summaryTable.addCell(createCell(String.format("R$ %.2f", mediaMensal), ColorConstants.WHITE));

        summaryTable.addCell(createCell("Número de Meses:", ColorConstants.WHITE).setBold());
        summaryTable.addCell(createCell(String.valueOf(monthlyExpenses.size()), ColorConstants.WHITE));

        if (maxMonth != null) {
            summaryTable.addCell(createCell("Maior Gasto Mensal:", ColorConstants.WHITE).setBold());
            summaryTable.addCell(createCell(
                    String.format("%s/%d - R$ %.2f", maxMonth.month(), maxMonth.year(), maxMonth.total()),
                    ColorConstants.WHITE));
        }

        if (minMonth != null) {
            summaryTable.addCell(createCell("Menor Gasto Mensal:", ColorConstants.WHITE).setBold());
            summaryTable.addCell(createCell(
                    String.format("%s/%d - R$ %.2f", minMonth.month(), minMonth.year(), minMonth.total()),
                    ColorConstants.WHITE));
        }

        double mediaSaldoRestante = monthlyExpenses.stream()
                .mapToDouble(MonthlySpentDtoList::saldoRestante)
                .average()
                .orElse(0.0);

        summaryTable.addCell(createCell("Média de Saldo Restante:", ColorConstants.WHITE).setBold());
        summaryTable.addCell(createCell(String.format("R$ %.2f", mediaSaldoRestante), ColorConstants.WHITE));

        double totalRendimentosExtras = monthlyExpenses.stream()
                .mapToDouble(MonthlySpentDtoList::rendimentosExtras)
                .sum();

        if (totalRendimentosExtras > 0) {
            summaryTable.addCell(createCell("Total de Rendimentos Extras:", ColorConstants.WHITE).setBold());
            summaryTable.addCell(createCell(String.format("R$ %.2f", totalRendimentosExtras), ColorConstants.WHITE));
        }

        document.add(summaryTable);
    }

    private void addFooter(Document document) {
        document.add(new Paragraph("\n\n"));
        Paragraph footer = new Paragraph("SmartCash - Controle Financeiro Pessoal")
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY);
        document.add(footer);
    }
}
