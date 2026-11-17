package fioshi.com.github.SmartCash.user.adapter.in.web;

import fioshi.com.github.SmartCash.user.adapter.in.web.dto.UserDtoDetail;
import fioshi.com.github.SmartCash.user.adapter.in.web.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase.SignUpUserCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final SignUpUserUseCase signUpUserUseCase;

    public UserController(SignUpUserUseCase signUpUserUseCase) {
        this.signUpUserUseCase = signUpUserUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDtoDetail> signUp(
            @RequestBody @Valid UserDtoSignUp dtoSignUp,
            UriComponentsBuilder uriBuilder
    ) {
        var command = new SignUpUserCommand(
                dtoSignUp.name(),
                dtoSignUp.surname(),
                dtoSignUp.cpf(),
                dtoSignUp.email(),
                dtoSignUp.password(),
                dtoSignUp.salarioMensal()
        );

        var user = signUpUserUseCase.execute(command);

        var uri = uriBuilder
                .path("api/user/signup/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UserDtoDetail(user));
    }
}
