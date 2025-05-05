package fioshi.com.github.SmartCash.user.service.validation;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;

public interface UserValidation {
    void validar(UserDtoSignUp dtoSignUp);
}
