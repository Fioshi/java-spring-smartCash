package fioshi.com.github.SmartCash.user.service;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.infra.security.jwt.JwtUtil;
import fioshi.com.github.SmartCash.user.domain.dto.AuthenticationRequest;
import fioshi.com.github.SmartCash.user.domain.dto.AuthenticationResponse;
import fioshi.com.github.SmartCash.user.domain.dto.TokenRefreshRequest;
import fioshi.com.github.SmartCash.user.domain.dto.UserDtoDetail;
import fioshi.com.github.SmartCash.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            var user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new BusinessException("User not found"));

            String token = jwtUtil.generateToken(user.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            return new AuthenticationResponse(token, refreshToken, new UserDtoDetail(user));

        } catch (BadCredentialsException e) {
            throw new BusinessException("Invalid email or password");
        }
    }

    public AuthenticationResponse refreshToken(TokenRefreshRequest request) {
        try {
            String username = jwtUtil.extractUsername(request.refreshToken());
            
            var user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new BusinessException("User not found"));

            if (jwtUtil.validateToken(request.refreshToken(), username)) {
                String newToken = jwtUtil.generateToken(username);
                String newRefreshToken = jwtUtil.generateRefreshToken(username);

                return new AuthenticationResponse(newToken, newRefreshToken, new UserDtoDetail(user));
            } else {
                throw new BusinessException("Invalid refresh token");
            }
        } catch (Exception e) {
            throw new BusinessException("Invalid refresh token");
        }
    }
}
