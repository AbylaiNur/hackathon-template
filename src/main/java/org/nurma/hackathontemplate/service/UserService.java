package org.nurma.hackathontemplate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nurma.hackathontemplate.collection.User;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.dto.response.GetUserResponse;
import org.nurma.hackathontemplate.exception.ValidationException;
import org.nurma.hackathontemplate.repository.UserRepository;
import org.nurma.hackathontemplate.util.EntityToDTOMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private static final String USER_ALREADY_EXISTS = "User with email %s already exists";
    private final UserRepository userRepository;
    private final AuthService authService;

    public GetUserResponse createUser(final CreateUserRequest createUserRequest) {
        userRepository.findByEmail(createUserRequest.getEmail())
                .ifPresent(user -> {
                    throw new ValidationException(USER_ALREADY_EXISTS.formatted(createUserRequest.getEmail()));
                });

        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        User savedUser = userRepository.save(user);

        return EntityToDTOMapper.mapUserToGetUserResponse(savedUser);
    }

    public GetUserResponse getCurrentUserDTO() {
        final User user = authService.getCurrentUserEntity();
        return EntityToDTOMapper.mapUserToGetUserResponse(user);
    }
}
