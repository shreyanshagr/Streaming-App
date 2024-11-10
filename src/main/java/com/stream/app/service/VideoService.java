package com.stream.app.service;

import com.stream.app.ContentType;
import com.stream.app.dto.VideoDTO;
import com.stream.app.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    Video save(VideoDTO.VideoRequest video, MultipartFile videoFile) throws IOException;

    Video getByTitle(String title);

    Video getByContentType(ContentType contentType);

    List<Video> getAll();
}
