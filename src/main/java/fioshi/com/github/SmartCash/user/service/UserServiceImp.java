package fioshi.com.github.SmartCash.user.service;

import fioshi.com.github.SmartCash.user.domain.model.User;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import fioshi.com.github.SmartCash.user.service.validation.UserValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final List<UserValidation> userValidations;

    public UserServiceImp(UserRepository userRepository, List<UserValidation> userValidations) {
        this.userRepository = userRepository;
        this.userValidations = userValidations;
    }

    @Override
    public User signUp(UserDtoSignUp dtoSignUp) {

        userValidations.forEach(userValidation -> userValidation.validar(dtoSignUp));

        var user = new User(dtoSignUp);
        userRepository.save(user);
        return user;
    }

    @Override
    public void signIn(UserDtoSignIn dtoSignIn) {
    }
}
