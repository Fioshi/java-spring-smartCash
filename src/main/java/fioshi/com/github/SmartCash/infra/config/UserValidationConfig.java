package fioshi.com.github.SmartCash.infra.config;

import fioshi.com.github.SmartCash.spent.service.validacao.SpentValidation;
import fioshi.com.github.SmartCash.spent.service.validacao.ValueValidation;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import fioshi.com.github.SmartCash.user.service.validation.UserCpfValidation;
import fioshi.com.github.SmartCash.user.service.validation.UserEmailValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserValidationConfig {

    @Bean
    public UserCpfValidation userValidationCpf(UserRepository userRepository){
        return new UserCpfValidation(userRepository);
    }

    @Bean
    public UserEmailValidation userEmailValidation(UserRepository userRepository){
        return new UserEmailValidation(userRepository);
    }
}
