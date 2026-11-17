package fioshi.com.github.SmartCash.spent.adapter.in.web;

import fioshi.com.github.SmartCash.spent.adapter.in.web.dto.AdditionalIncomeDtoInsert;
import fioshi.com.github.SmartCash.spent.adapter.in.web.dto.AdditionalIncomeDtoList;
import fioshi.com.github.SmartCash.spent.application.port.in.DeleteAdditionalIncomeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertAdditionalIncomeUseCase;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertAdditionalIncomeUseCase.InsertAdditionalIncomeCommand;
import fioshi.com.github.SmartCash.spent.application.port.in.ListAdditionalIncomeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/additional-income")
public class AdditionalIncomeController {

    private final InsertAdditionalIncomeUseCase insertAdditionalIncomeUseCase;
    private final ListAdditionalIncomeUseCase listAdditionalIncomeUseCase;
    private final DeleteAdditionalIncomeUseCase deleteAdditionalIncomeUseCase;

    public AdditionalIncomeController(
            InsertAdditionalIncomeUseCase insertAdditionalIncomeUseCase,
            ListAdditionalIncomeUseCase listAdditionalIncomeUseCase,
            DeleteAdditionalIncomeUseCase deleteAdditionalIncomeUseCase
    ) {
        this.insertAdditionalIncomeUseCase = insertAdditionalIncomeUseCase;
        this.listAdditionalIncomeUseCase = listAdditionalIncomeUseCase;
        this.deleteAdditionalIncomeUseCase = deleteAdditionalIncomeUseCase;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AdditionalIncomeDtoList> insert(
            @PathVariable Long userId,
            @RequestBody @Valid AdditionalIncomeDtoInsert dto,
            UriComponentsBuilder uriBuilder
    ) {
        var command = new InsertAdditionalIncomeCommand(
                userId,
                dto.valor(),
                dto.descricao(),
                dto.ano(),
                dto.mes()
        );
        var income = insertAdditionalIncomeUseCase.execute(command);
        var uri = uriBuilder.path("/api/additional-income/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).body(new AdditionalIncomeDtoList(income));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AdditionalIncomeDtoList>> list(@PathVariable Long userId) {
        var incomes = listAdditionalIncomeUseCase.execute(userId).stream()
                .map(AdditionalIncomeDtoList::new)
                .toList();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteAdditionalIncomeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
