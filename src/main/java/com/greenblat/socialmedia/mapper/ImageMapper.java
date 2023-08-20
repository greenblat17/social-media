package com.greenblat.socialmedia.mapper;

import com.greenblat.socialmedia.model.Image;
import com.greenblat.socialmedia.util.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Mapper
public class ImageMapper {

    public List<Image>  mapToListImage(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            var image = Image.builder()
                    .filename(file.getOriginalFilename())
                    .build();
            images.add(image);
        }
        return images;
    }
}
