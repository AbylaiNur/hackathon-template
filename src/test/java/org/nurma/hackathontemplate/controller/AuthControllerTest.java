package org.nurma.hackathontemplate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.dto.request.LoginRequest;
import org.nurma.hackathontemplate.dto.request.RefreshRequest;
import org.nurma.hackathontemplate.dto.response.JwtResponse;
import org.nurma.hackathontemplate.util.ExceptionTitle;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerTest extends AbstractControllerTest {
    @AfterEach
    void tearDown() {
        getMongoTemplate().getDb().drop();
    }

    @Test
    void loginSuccess() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");
        createUser(createUserRequest)
                .andExpect(status().isOk());

        login(new LoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    void loginFailureWithInvalidCredentials() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");
        createUser(createUserRequest)
                .andExpect(status().isOk());

        login(new LoginRequest(createUserRequest.getEmail(), "wrong_password"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value(ExceptionTitle.AUTHENTICATION))
                .andExpect(jsonPath("$.detail").isNotEmpty());
    }

    @Test
    void loginFailureWithNonExistingUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");

        login(new LoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value(ExceptionTitle.AUTHENTICATION))
                .andExpect(jsonPath("$.detail").isNotEmpty());
    }

    @Test
    void refreshAccessTokenSuccess() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("211393@astanait.edu.kz", "password");

        createUser(createUserRequest)
                .andExpect(status().isOk());

        JwtResponse jwtResponse = fromJson(
                login(new LoginRequest(createUserRequest.getEmail(), createUserRequest.getPassword())).andReturn(),
                JwtResponse.class
        );

        RefreshRequest refreshRequest = new RefreshRequest(jwtResponse.getRefreshToken());

        getNewAccessToken(refreshRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isEmpty());
    }


    @Test
    void refreshAccessTokenFailure() throws Exception {
        RefreshRequest refreshRequest = new RefreshRequest("wrongtoken");

        getNewAccessToken(refreshRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value(ExceptionTitle.AUTHENTICATION))
                .andExpect(jsonPath("$.detail").isNotEmpty());
    }
}