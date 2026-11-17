package fioshi.com.github.SmartCash.user.adapter.in.web;

import fioshi.com.github.SmartCash.user.adapter.in.web.dto.AuthenticationRequest;
import fioshi.com.github.SmartCash.user.adapter.in.web.dto.AuthenticationResponse;
import fioshi.com.github.SmartCash.user.adapter.in.web.dto.TokenRefreshRequest;
import fioshi.com.github.SmartCash.user.application.port.in.AuthenticateUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.in.AuthenticateUserUseCase.AuthenticateUserCommand;
import fioshi.com.github.SmartCash.user.application.port.in.RefreshTokenUseCase;
import fioshi.com.github.SmartCash.user.application.port.in.RefreshTokenUseCase.RefreshTokenCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and authorization operations")
public class AuthenticationController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthenticationController(
            AuthenticateUserUseCase authenticateUserUseCase,
            RefreshTokenUseCase refreshTokenUseCase
    ) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Authenticate user", description = "Authenticate user with email and password, returns JWT tokens")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        var result = authenticateUserUseCase.execute(new AuthenticateUserCommand(request.email(), request.password()));
        return ResponseEntity.ok(AuthenticationResponse.from(result));
    }

    @PostMapping(value = "/refresh")
    @Operation(summary = "Refresh access token", description = "Generate new access token using refresh token")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
    @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest request) {
        var result = refreshTokenUseCase.execute(new RefreshTokenCommand(request.refreshToken()));
        return ResponseEntity.ok(AuthenticationResponse.from(result));
    }
}
