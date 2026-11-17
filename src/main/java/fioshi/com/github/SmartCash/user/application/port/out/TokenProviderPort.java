package fioshi.com.github.SmartCash.user.application.port.out;

public interface TokenProviderPort {

    String generateToken(String username);

    String generateRefreshToken(String username);

    String extractUsername(String token);

    boolean validateToken(String token, String username);
}
