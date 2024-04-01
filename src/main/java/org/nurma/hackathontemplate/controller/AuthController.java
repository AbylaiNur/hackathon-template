package org.nurma.hackathontemplate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.dto.request.LoginRequest;
import org.nurma.hackathontemplate.dto.request.RefreshRequest;
import org.nurma.hackathontemplate.dto.response.JwtResponse;
import org.nurma.hackathontemplate.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid final LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse getNewAccessToken(@RequestBody final RefreshRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }
}
