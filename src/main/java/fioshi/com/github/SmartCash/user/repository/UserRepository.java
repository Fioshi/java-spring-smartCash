package fioshi.com.github.SmartCash.user.repository;

import fioshi.com.github.SmartCash.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
