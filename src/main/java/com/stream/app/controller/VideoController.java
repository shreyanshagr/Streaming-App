package com.stream.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stream.app.dto.VideoDTO;
import com.stream.app.entity.Video;
import com.stream.app.service.VideoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addVideo(
            @RequestParam("videoData") String videoData,
            @RequestParam("videoFile") MultipartFile videoFile) {

        try {
            // Convert and validate JSON data
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            VideoDTO.VideoRequest videoRequest = objectMapper.readValue(videoData, VideoDTO.VideoRequest.class);

            // Validate the converted object
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<VideoDTO.VideoRequest>> violations = validator.validate(videoRequest);

            if (!violations.isEmpty()) {
                List<String> errors = violations.stream()
                        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }

            // Validate file
            if (videoFile == null || videoFile.isEmpty()) {
                throw new BadRequestException("Video file cannot be empty");
            }

            Video savedVideo = videoService.save(videoRequest,videoFile);
            return ResponseEntity.ok(savedVideo);

        } catch (Exception e) {
            log.error("Failed to save video", e);
            return ResponseEntity.badRequest().body("Failed to save video: " + e.getMessage());
        }
    }
}
