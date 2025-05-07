package fioshi.com.github.SmartCash.spent.controller;

import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoList;
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

    @PostMapping
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

    @GetMapping("transactions/resume")
    public ResponseEntity<SpentDtoList> getSpentDetail (@RequestParam Long spentId) {

    }



}
