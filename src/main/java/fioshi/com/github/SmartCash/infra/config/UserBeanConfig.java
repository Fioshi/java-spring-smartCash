package fioshi.com.github.SmartCash.infra.config;

import fioshi.com.github.SmartCash.user.application.port.in.AuthenticateUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.in.RefreshTokenUseCase;
import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.out.AuthenticationPort;
import fioshi.com.github.SmartCash.user.application.port.out.PasswordEncoderPort;
import fioshi.com.github.SmartCash.user.application.port.out.TokenProviderPort;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;
import fioshi.com.github.SmartCash.user.application.usecase.AuthenticateUserUseCaseImpl;
import fioshi.com.github.SmartCash.user.application.usecase.RefreshTokenUseCaseImpl;
import fioshi.com.github.SmartCash.user.application.usecase.SignUpUserUseCaseImpl;
import fioshi.com.github.SmartCash.user.application.usecase.validation.UserCpfValidation;
import fioshi.com.github.SmartCash.user.application.usecase.validation.UserEmailValidation;
import fioshi.com.github.SmartCash.user.application.usecase.validation.UserSignUpValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserSignUpValidation userCpfValidation(UserRepositoryPort userRepositoryPort) {
        return new UserCpfValidation(userRepositoryPort);
    }

    @Bean
    public UserSignUpValidation userEmailValidation(UserRepositoryPort userRepositoryPort) {
        return new UserEmailValidation(userRepositoryPort);
    }

    @Bean
    public SignUpUserUseCase signUpUserUseCase(
            UserRepositoryPort userRepositoryPort,
            List<UserSignUpValidation> validations,
            PasswordEncoderPort passwordEncoderPort
    ) {
        return new SignUpUserUseCaseImpl(userRepositoryPort, validations, passwordEncoderPort);
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            AuthenticationPort authenticationPort,
            TokenProviderPort tokenProviderPort,
            UserRepositoryPort userRepositoryPort
    ) {
        return new AuthenticateUserUseCaseImpl(authenticationPort, tokenProviderPort, userRepositoryPort);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            TokenProviderPort tokenProviderPort,
            UserRepositoryPort userRepositoryPort
    ) {
        return new RefreshTokenUseCaseImpl(tokenProviderPort, userRepositoryPort);
    }
}
