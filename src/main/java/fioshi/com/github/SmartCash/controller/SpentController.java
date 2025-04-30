package fioshi.com.github.SmartCash.controller;

import fioshi.com.github.SmartCash.domain.dto.SpentDtoInsert;
import fioshi.com.github.SmartCash.service.SpentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/spent")
public class SpentController {

    private final SpentService spentService;

    public SpentController(SpentService spentService) {
        this.spentService = spentService;
    }

    @PostMapping
    private ResponseEntity<String> insertSpent(@RequestBody SpentDtoInsert dtoInsert){
        spentService.insertSpent(dtoInsert);
        return ResponseEntity.ok("Cadastrado com sucesso");

    }

}
