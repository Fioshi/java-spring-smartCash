package fioshi.com.github.SmartCash.spent.adapter.out.persistence;

import fioshi.com.github.SmartCash.spent.application.port.out.SpentUserLookupPort;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;
import fioshi.com.github.SmartCash.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpentUserLookupAdapter implements SpentUserLookupPort {

    private final UserRepositoryPort userRepositoryPort;

    public SpentUserLookupAdapter(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepositoryPort.findById(userId);
    }
}
