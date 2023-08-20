package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.model.Image;
import com.greenblat.socialmedia.model.Post;
import com.greenblat.socialmedia.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${app.image.bucket:/Users/alexgreenblat/IdeaProjects/test-task/social-media/images}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content, Post post) {
        var fullImagePath = Path.of(bucket, imagePath);

        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);

            var image = buildImage(imagePath, post);

            imageRepository.save(image);
        }
    }

    private static Image buildImage(String imagePath, Post post) {
        return Image.builder()
                .filename(imagePath)
                .post(post)
                .build();
    }
}
