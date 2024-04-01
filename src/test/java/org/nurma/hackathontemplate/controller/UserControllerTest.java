package org.nurma.hackathontemplate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.dto.request.LoginRequest;
import org.nurma.hackathontemplate.dto.response.JwtResponse;
import org.nurma.hackathontemplate.util.ExceptionTitle;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest extends AbstractControllerTest {
    @AfterEach
    void tearDown() {
        getMongoTemplate().getDb().drop();
    }

    @Test
    void userCanCreateAccount() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");
        createUser(createUserRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(createUserRequest.getEmail()));
    }

    @Test
    void userCannotCreateAccountWithExistingEmail() throws Exception {
        String existingEmail = "211393@astanait.edu.kz";
        CreateUserRequest createUserRequest = new CreateUserRequest(existingEmail, "password");
        createUser(createUserRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(createUserRequest.getEmail()));

        createUser(createUserRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(ExceptionTitle.VALIDATION))
                .andExpect(jsonPath("$.detail").isNotEmpty());
    }

    private static Stream<Arguments> invalidCreateUserRequests() {
        return Stream.of(
                Arguments.of(new CreateUserRequest(null, "password")),
                Arguments.of(new CreateUserRequest("", "password")),
                Arguments.of(new CreateUserRequest("not_email", "password")),
                Arguments.of(new CreateUserRequest("email@gmail.com", "")),
                Arguments.of(new CreateUserRequest("email@gmail.com", null))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCreateUserRequests")
    void userCannotCreateAccountWithInvalidRequest(CreateUserRequest createUserRequest) throws Exception {
        createUser(createUserRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(ExceptionTitle.VALIDATION))
                .andExpect(jsonPath("$.detail").isNotEmpty());
    }

    @Test
    void userCanGetProfile() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");
        createUser(createUserRequest)
                .andExpect(status().isOk());

        JwtResponse jwtResponse = fromJson(
                login(new LoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword())).andReturn(),
                JwtResponse.class
        );

        getCurrentUser(jwtResponse.getAccessToken())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(createUserRequest.getEmail()));
    }
}
