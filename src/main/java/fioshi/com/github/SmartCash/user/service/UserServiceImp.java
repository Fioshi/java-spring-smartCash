package fioshi.com.github.SmartCash.user.service;

import fioshi.com.github.SmartCash.user.domain.model.User;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserDtoSignUp dtoSignUp) {
        if (userRepository.existsByCpf(dtoSignUp.cpf()))
            throw new RuntimeException("O CPF deve ser unico no sistema");
        if (userRepository.existsByEmail(dtoSignUp.email()))
            throw new RuntimeException("Email ja registrado");
        var user = new User(dtoSignUp);
        userRepository.save(user);
    }

    @Override
    public void signIn(UserDtoSignIn dtoSignIn) {
    }
}
