package fioshi.com.github.SmartCash.service;

import fioshi.com.github.SmartCash.domain.User;
import fioshi.com.github.SmartCash.domain.dto.UserDtoSignIn;
import fioshi.com.github.SmartCash.domain.dto.UserDtoSignUp;
import fioshi.com.github.SmartCash.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
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
