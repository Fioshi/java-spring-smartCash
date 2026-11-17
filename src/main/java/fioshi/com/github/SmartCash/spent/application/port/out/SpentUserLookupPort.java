package fioshi.com.github.SmartCash.spent.application.port.out;

import fioshi.com.github.SmartCash.user.domain.model.User;

import java.util.Optional;

public interface SpentUserLookupPort {

    Optional<User> findById(Long userId);
}
