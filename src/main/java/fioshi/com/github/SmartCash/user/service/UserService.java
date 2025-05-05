package fioshi.com.github.SmartCash.user.service;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;

public interface UserService {

    void signUp(UserDtoSignUp dtoSignUp);
    void signIn(UserDtoSignIn dtoSignIn);

}
