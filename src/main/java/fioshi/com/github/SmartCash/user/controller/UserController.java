package fioshi.com.github.SmartCash.user.controller;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoDetail;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    private ResponseEntity<UserDtoDetail> signUp (
            @RequestBody UserDtoSignUp dtoSignUp,
            UriComponentsBuilder uriBuilder
    ){
        var user = service.signUp(dtoSignUp);

        var uri = uriBuilder
                .path("api/user/signup/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UserDtoDetail(user));
    }

    @PostMapping("/singin")
    private ResponseEntity<String> singIn (){
        return ResponseEntity.ok("Login efetuado com sucesso");
    }

}
