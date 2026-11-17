package fioshi.com.github.SmartCash.user.adapter.out.security;

import fioshi.com.github.SmartCash.infra.security.jwt.JwtUtil;
import fioshi.com.github.SmartCash.user.application.port.out.TokenProviderPort;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {

    private final JwtUtil jwtUtil;

    public JwtTokenProviderAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    @Override
    public String generateRefreshToken(String username) {
        return jwtUtil.generateRefreshToken(username);
    }

    @Override
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    @Override
    public boolean validateToken(String token, String username) {
        return Boolean.TRUE.equals(jwtUtil.validateToken(token, username));
    }
}
