package com.stream.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stream.app.ContentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class VideoDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VideoRequest{

        private String videoId;

        @NotNull(message = "Title cannot be null or empty.")
        private String title;

        @NotNull(message = "Description cannot be null or empty.")
        private String description;

        @NotNull(message = "Content Type cannot be null or empty.")
        private ContentType contentType;

        private String filePath;

        @Builder.Default
        @JsonIgnoreProperties(allowGetters = true)
        private LocalDateTime creationTime = LocalDateTime.now();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVideoRequest{

        @Valid
        @NotNull(message = "Video data cannot be null")
       private VideoRequest videoRequest;

        @NotNull(message = "Video file cannot be empty")
       private MultipartFile videoFile;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VideoResponse{

        private String videoId;

        private String title;

        private String description;

        private ContentType contentType;

        private String filePath;

        private LocalDateTime creationTime;
    }


}
