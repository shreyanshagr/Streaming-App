package com.stream.app.entity;

import com.stream.app.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Video {

    @Id
    private String videoId;

    private String title;

    private String description;

    private ContentType contentType;

    private String filePath;

    @Builder.Default
    private LocalDateTime creationTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
