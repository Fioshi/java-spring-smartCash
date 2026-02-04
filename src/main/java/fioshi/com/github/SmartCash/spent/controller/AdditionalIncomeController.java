package fioshi.com.github.SmartCash.spent.controller;

import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoList;
import fioshi.com.github.SmartCash.spent.service.AdditionalIncomeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/additional-income")
public class AdditionalIncomeController {

    private final AdditionalIncomeService additionalIncomeService;

    public AdditionalIncomeController(AdditionalIncomeService additionalIncomeService) {
        this.additionalIncomeService = additionalIncomeService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AdditionalIncomeDtoList> insert(
            @PathVariable Long userId,
            @RequestBody @Valid AdditionalIncomeDtoInsert dto,
            UriComponentsBuilder uriBuilder
    ) {
        var income = additionalIncomeService.insertIncome(userId, dto);
        var uri = uriBuilder.path("/api/additional-income/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).body(new AdditionalIncomeDtoList(income));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AdditionalIncomeDtoList>> list(@PathVariable Long userId) {
        var incomes = additionalIncomeService.listByUser(userId);
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        additionalIncomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
