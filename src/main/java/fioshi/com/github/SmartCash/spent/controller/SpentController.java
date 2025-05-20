package fioshi.com.github.SmartCash.spent.controller;

import fioshi.com.github.SmartCash.spent.domain.dto.*;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.service.SpentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Month;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/spent")
public class SpentController {

    private final SpentService spentService;

    public SpentController(SpentService spentService)
    {
        this.spentService = spentService;
    }

    @PostMapping("transactions")
    private ResponseEntity<SpentDtoDetail> insertSpent(
            @RequestBody @Valid SpentDtoInsert dtoInsert,
            UriComponentsBuilder uriBuilder
    ){
        var spent = spentService.insertSpent(dtoInsert);

        var uri = uriBuilder
                .path("api/spents/transactions/{id}")
                .buildAndExpand(spent.getInstallments())
                .toUri();

        return ResponseEntity.created(uri).body(new SpentDtoDetail(spent));
    }

    @GetMapping("transactions")
    private ResponseEntity<List<MonthlySpentDtoList>> listMontrlySpence(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(spentService.listMontlhySpenceFiltered(userId));
    }

    @GetMapping("transactions/resume")
    public ResponseEntity<MonthlySpentDtoList> getSpentResume(
            @RequestParam Long userId,
            @RequestParam Month month,
            @RequestParam int year
    ) {
        return ResponseEntity.ok().body(spentService.getSpentResume(userId, month, year));
    }

    @GetMapping("transactions/detail")
    public ResponseEntity<SpentDtoDetail> getSpentDetail (
            @RequestParam Long userId,
            @RequestParam Long spentId
    ) {
        return ResponseEntity.ok().body(spentService.getSpentDetail(userId ,spentId));
    }


    @GetMapping("transactions/categories")
    public ResponseEntity<List<SpentCategorieDtoList>> getCategories()
    {
        return ResponseEntity.ok().body(spentService.getCategories());
    }

    @PatchMapping("transactions/update/{id}")
    public ResponseEntity<String> updateSpent(
            @PathVariable Long id,
            @RequestBody @Valid SpentDtoUpdate dtoUpdate
    ){
        var spent = spentService.updateSpent(id, dtoUpdate);
        return ResponseEntity.ok().body("FoiFoi");
    }

    @DeleteMapping("transactions/delete/{id}")
    public ResponseEntity<String> deleteSpent(
            @PathVariable Long id
    ){
        spentService.deleteSpent(id);
        return ResponseEntity.noContent().build();
    }

}
