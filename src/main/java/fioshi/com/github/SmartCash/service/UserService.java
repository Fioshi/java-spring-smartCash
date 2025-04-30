package fioshi.com.github.SmartCash.service;

import fioshi.com.github.SmartCash.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.domain.dto.UserDtoSignUp;

public interface UserService {

    void signUp(UserDtoSignUp dtoSignUp);
    void signIn(UserDtoSignIn dtoSignIn);

}
