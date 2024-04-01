package org.nurma.hackathontemplate.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nurma.hackathontemplate.configuration.IntegrationEnvironment;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.dto.request.LoginRequest;
import org.nurma.hackathontemplate.dto.request.RefreshRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.sql.Ref;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractControllerTest extends IntegrationEnvironment {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private ResultActions performGet(String url) throws Exception {
        return performGetWithToken(url, null);
    }

    private ResultActions performGetWithToken(String url, String token) throws Exception {
        return mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print());
    }

    private ResultActions performPost(String url, Object request) throws Exception {
        return performPostWithToken(url, request, null);
    }

    private ResultActions performPostWithToken(String url, Object request, String token) throws Exception {
        String contentJson = objectMapper.writeValueAsString(request);
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(contentJson))
                .andDo(print());
    }

    protected <T> T fromJson(MvcResult result, Class<T> clazz) throws Exception {
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(contentAsString, clazz);
    }

    protected <T> List<T> fromJsonArray(ResultActions resultActions, Class<T> clazz) throws Exception {
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(contentAsString, javaType);
    }

    // AuthController
    protected ResultActions login(LoginRequest loginRequest) throws Exception {
        return performPost("/auth/login", loginRequest);
    }

    protected ResultActions getNewAccessToken(RefreshRequest refreshRequest) throws Exception {
        return performPost("/auth/refresh", refreshRequest);
    }

    // UserController
    protected ResultActions createUser(CreateUserRequest createUserRequest) throws Exception {
        return performPost("/users", createUserRequest);
    }

    protected ResultActions getCurrentUser(String token) throws Exception {
        return performGetWithToken("/users/me", token);
    }
}
