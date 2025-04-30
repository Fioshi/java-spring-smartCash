package fioshi.com.github.SmartCash.controller;

import fioshi.com.github.SmartCash.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    private ResponseEntity<String> signUp (@RequestBody UserDtoSignUp dtoSignUp){
        service.signUp(dtoSignUp);
        return ResponseEntity.ok("Cadastrado com sucesso");
    }

    @PostMapping("/login")
    private ResponseEntity<String> singIn (){
        return ResponseEntity.ok("Login efetuado com sucesso");
    }

}
