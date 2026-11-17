package fioshi.com.github.SmartCash.user.application.port.out;

import fioshi.com.github.SmartCash.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Long id);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
