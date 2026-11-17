package fioshi.com.github.SmartCash.user.application.port.out;

public interface AuthenticationPort {

    /**
     * Authenticates user credentials; throws BusinessException if invalid.
     */
    void authenticate(String email, String password);
}
