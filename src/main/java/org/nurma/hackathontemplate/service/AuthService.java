package org.nurma.hackathontemplate.service;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nurma.hackathontemplate.collection.User;
import org.nurma.hackathontemplate.dto.request.LoginRequest;
import org.nurma.hackathontemplate.dto.response.JwtResponse;
import org.nurma.hackathontemplate.repository.UserRepository;
import org.nurma.hackathontemplate.security.JwtAuthentication;
import org.nurma.hackathontemplate.security.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.nurma.hackathontemplate.exception.AuthenticationException;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
    private static final String INVALID_PASSWORD = "Invalid password";
    private static final String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public JwtResponse login(final LoginRequest authRequest) {
        final User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND.formatted(authRequest.getEmail())));
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthenticationException(INVALID_PASSWORD);
        }
    }

    public JwtResponse getAccessToken(final String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.get("email", String.class);
            final User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND.formatted(email)));
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    public User getCurrentUserEntity() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthentication jwtAuthentication) {
            String message = USER_NOT_FOUND.formatted(jwtAuthentication.getName());
            return userRepository
                    .findByEmail(jwtAuthentication.getEmail())
                    .orElseThrow(() -> new AuthenticationException(message));
        }

        String authenticationType = authentication.getClass().getSimpleName();
        String message = "Authentication type is not supported for %s";
        throw new AuthenticationException(message.formatted(authenticationType));
    }
}
