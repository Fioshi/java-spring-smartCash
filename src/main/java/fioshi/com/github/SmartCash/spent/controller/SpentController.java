package fioshi.com.github.SmartCash.spent.controller;

import fioshi.com.github.SmartCash.spent.domain.dto.*;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.service.SpentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/spent")
public class SpentController {

    private final SpentService spentService;

    public SpentController(SpentService spentService) {
        this.spentService = spentService;
    }

    @PostMapping("transactions")
    private ResponseEntity<String> insertSpent(
            @RequestBody SpentDtoInsert dtoInsert
    ){
        spentService.insertSpent(dtoInsert);
        return ResponseEntity.ok("Cadastrado com sucesso");
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
    public ResponseEntity<List<SpentCategorieDtoList>> getCategories(){
        return ResponseEntity.ok().body(spentService.getCategories());
    }

    @PatchMapping("transactions/update/{id}")
    public ResponseEntity<String> updateSpent(
            @PathVariable Long id,
            @RequestBody SpentDtoUpdate dtoUpdate
    ){
        var spent = spentService.updateSpent(id, dtoUpdate);
        return ResponseEntity.ok().body("FoiFoi");
    }

}
