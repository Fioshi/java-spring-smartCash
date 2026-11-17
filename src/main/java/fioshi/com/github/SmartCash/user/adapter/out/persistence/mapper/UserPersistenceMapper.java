package fioshi.com.github.SmartCash.user.adapter.out.persistence.mapper;

import fioshi.com.github.SmartCash.user.adapter.out.persistence.entity.UserJpaEntity;
import fioshi.com.github.SmartCash.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpaEntity toEntity(User domain) {
        return new UserJpaEntity(
                domain.getId(),
                domain.getNome(),
                domain.getSurname(),
                domain.getCpf(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getSalarioMensal()
        );
    }

    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getNome(),
                entity.getSurname(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getSalarioMensal()
        );
    }
}
