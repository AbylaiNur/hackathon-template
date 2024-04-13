package org.nurma.hackathontemplate.controller;

import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.service.AudioTranscriptionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/audio")
public class AudioController {
    private final AudioTranscriptionService audioTranscriptionService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileAndJson(
            @RequestParam("file") final MultipartFile file) throws IOException {
        String transcription = audioTranscriptionService.sendFileForTranscription(file);
        return ResponseEntity.ok(transcription);
    }

}
