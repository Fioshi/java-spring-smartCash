package fioshi.com.github.SmartCash.user.adapter.out.persistence.repository;

import fioshi.com.github.SmartCash.user.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<UserJpaEntity> findByEmail(String email);
}
