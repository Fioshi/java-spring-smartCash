package fioshi.com.github.SmartCash.user.service;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.domain.model.User;

public interface UserService {

    User signUp(UserDtoSignUp dtoSignUp);
    void signIn(UserDtoSignIn dtoSignIn);

}
