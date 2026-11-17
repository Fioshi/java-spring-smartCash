package fioshi.com.github.SmartCash.spent.adapter.in.web;

import fioshi.com.github.SmartCash.spent.adapter.in.web.dto.*;
import fioshi.com.github.SmartCash.spent.application.port.in.*;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertSpentUseCase.InsertSpentCommand;
import fioshi.com.github.SmartCash.spent.application.port.in.UpdateSpentUseCase.UpdateSpentCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/spent")
public class SpentController {

    private final InsertSpentUseCase insertSpentUseCase;
    private final ListMonthlySpentsUseCase listMonthlySpentsUseCase;
    private final GetSpentResumeUseCase getSpentResumeUseCase;
    private final GetSpentDetailUseCase getSpentDetailUseCase;
    private final GetCategoriesUseCase getCategoriesUseCase;
    private final UpdateSpentUseCase updateSpentUseCase;
    private final DeleteSpentUseCase deleteSpentUseCase;
    private final ExportPdfUseCase exportPdfUseCase;

    public SpentController(
            InsertSpentUseCase insertSpentUseCase,
            ListMonthlySpentsUseCase listMonthlySpentsUseCase,
            GetSpentResumeUseCase getSpentResumeUseCase,
            GetSpentDetailUseCase getSpentDetailUseCase,
            GetCategoriesUseCase getCategoriesUseCase,
            UpdateSpentUseCase updateSpentUseCase,
            DeleteSpentUseCase deleteSpentUseCase,
            ExportPdfUseCase exportPdfUseCase
    ) {
        this.insertSpentUseCase = insertSpentUseCase;
        this.listMonthlySpentsUseCase = listMonthlySpentsUseCase;
        this.getSpentResumeUseCase = getSpentResumeUseCase;
        this.getSpentDetailUseCase = getSpentDetailUseCase;
        this.getCategoriesUseCase = getCategoriesUseCase;
        this.updateSpentUseCase = updateSpentUseCase;
        this.deleteSpentUseCase = deleteSpentUseCase;
        this.exportPdfUseCase = exportPdfUseCase;
    }

    @PostMapping("transactions")
    public ResponseEntity<SpentDtoDetail> insertSpent(
            @RequestBody @Valid SpentDtoInsert dtoInsert,
            UriComponentsBuilder uriBuilder
    ) {
        var command = new InsertSpentCommand(
                dtoInsert.idUser(),
                dtoInsert.typeSpent(),
                dtoInsert.installments(),
                dtoInsert.value(),
                dtoInsert.place(),
                dtoInsert.item(),
                dtoInsert.categorie()
        );
        var spent = insertSpentUseCase.execute(command);

        var uri = uriBuilder
                .path("api/spents/transactions/{id}")
                .buildAndExpand(spent.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new SpentDtoDetail(spent));
    }

    @GetMapping("transactions")
    public ResponseEntity<List<MonthlySpentDtoList>> listMontrlySpence(@RequestParam Long userId) {
        var result = listMonthlySpentsUseCase.execute(userId).stream()
                .map(MonthlySpentDtoList::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("transactions/resume")
    public ResponseEntity<MonthlySpentDtoList> getSpentResume(
            @RequestParam Long userId,
            @RequestParam Month month,
            @RequestParam int year
    ) {
        var summary = getSpentResumeUseCase.execute(userId, month, year);
        return ResponseEntity.ok().body(new MonthlySpentDtoList(summary));
    }

    @GetMapping("transactions/detail")
    public ResponseEntity<SpentDtoDetail> getSpentDetail(
            @RequestParam Long userId,
            @RequestParam Long spentId
    ) {
        var spent = getSpentDetailUseCase.execute(userId, spentId);
        return ResponseEntity.ok().body(new SpentDtoDetail(spent));
    }

    @GetMapping("transactions/categories")
    public ResponseEntity<List<SpentCategorieDtoList>> getCategories() {
        var categories = getCategoriesUseCase.execute().stream()
                .map(SpentCategorieDtoList::new)
                .toList();
        return ResponseEntity.ok().body(categories);
    }

    @PatchMapping("transactions/update/{id}")
    public ResponseEntity<String> updateSpent(
            @PathVariable Long id,
            @RequestBody @Valid SpentDtoUpdate dtoUpdate
    ) {
        var command = new UpdateSpentCommand(
                dtoUpdate.typeSpent(),
                dtoUpdate.installments(),
                dtoUpdate.value(),
                dtoUpdate.place(),
                dtoUpdate.item(),
                dtoUpdate.categorie()
        );
        updateSpentUseCase.execute(id, command);
        return ResponseEntity.ok().body("FoiFoi");
    }

    @DeleteMapping("transactions/delete/{id}")
    public ResponseEntity<String> deleteSpent(@PathVariable Long id) {
        deleteSpentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("transactions/export/pdf")
    public ResponseEntity<byte[]> exportAllMonthsToPdf(@RequestParam Long userId) {
        byte[] pdfBytes = exportPdfUseCase.exportAllMonths(userId);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=gastos-completo.pdf")
                .body(pdfBytes);
    }

    @GetMapping("transactions/export/pdf/month")
    public ResponseEntity<byte[]> exportSingleMonthToPdf(
            @RequestParam Long userId,
            @RequestParam Month month,
            @RequestParam int year
    ) {
        byte[] pdfBytes = exportPdfUseCase.exportSingleMonth(userId, month, year);

        String filename = String.format("gastos-%s-%d.pdf", month.toString().toLowerCase(), year);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(pdfBytes);
    }
}
