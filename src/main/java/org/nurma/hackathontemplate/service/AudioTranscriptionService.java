package org.nurma.hackathontemplate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Log4j2
public class AudioTranscriptionService {
    private final RestTemplate restTemplate;

    public AudioTranscriptionService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendFileForTranscription(final MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String baseUrl = "http://localhost:5000/asr";
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);

        return response.getBody();
    }

}
