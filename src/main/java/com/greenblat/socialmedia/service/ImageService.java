package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.exception.ResourceNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

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

    @SneakyThrows
    public byte[] get(String imagePath) {
        var fullImagePath = Path.of(bucket, imagePath);
        if (!Files.exists(fullImagePath))
            throw new ResourceNotFoundException("File with path [%s] not found".formatted(imagePath));
        return Files.readAllBytes(fullImagePath);
    }

    public List<byte[]> getPostsImages(Post post) {
        var images = post.getImages();
        List<byte[]> imagesBytes = new ArrayList<>(images.size());
        for (Image image : images) {
            var bytes = get(image.getFilename());
            imagesBytes.add(bytes);
        }
        return imagesBytes;
    }

    private static Image buildImage(String imagePath, Post post) {
        return Image.builder()
                .filename(imagePath)
                .post(post)
                .build();
    }
}
