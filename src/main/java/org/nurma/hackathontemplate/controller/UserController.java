package org.nurma.hackathontemplate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.dto.response.GetUserResponse;
import org.nurma.hackathontemplate.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public GetUserResponse getCurrentUser() {
        return userService.getCurrentUserDTO();
    }

    @PostMapping
    public GetUserResponse createUser(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
}
