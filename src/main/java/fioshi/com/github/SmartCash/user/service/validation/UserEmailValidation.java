package fioshi.com.github.SmartCash.user.service.validation;

import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.repository.UserRepository;

public class UserEmailValidation implements UserValidation{

    private final UserRepository userRepository;

    public UserEmailValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validar(UserDtoSignUp dtoSignUp) {
        if (userRepository.existsByEmail(dtoSignUp.email()))
            throw new RuntimeException("Email ja registrado");
    }
}
