package pl.sg.meetingorganiser.meetingorganiserservice.service.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sg.meetingorganiser.meetingorganiserservice.api.ApiResponse;
import pl.sg.meetingorganiser.meetingorganiserservice.api.AuthResponse;
import pl.sg.meetingorganiser.meetingorganiserservice.api.LoginRequest;
import pl.sg.meetingorganiser.meetingorganiserservice.api.SignUpRequest;
import pl.sg.meetingorganiser.meetingorganiserservice.config.security.JwtTokenProvider;
import pl.sg.meetingorganiser.meetingorganiserservice.infrastructure.exception.BadRequestException;
import pl.sg.meetingorganiser.meetingorganiserservice.service.user.AuthProvider;
import pl.sg.meetingorganiser.meetingorganiserservice.service.user.UserEntity;
import pl.sg.meetingorganiser.meetingorganiserservice.service.user.UserRepository;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(token);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(authResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@Valid @RequestBody SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProvider(AuthProvider.LOCAL);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        AuthResponse authResponse = new AuthResponse("1");
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(authResponse);
        return ResponseEntity.created(location).body(apiResponse);
    }

}