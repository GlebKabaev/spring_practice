package com.glb.practice.my_practice.srevice.Image;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.glb.practice.my_practice.models.Image;
import com.glb.practice.my_practice.repository.image.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageService {
    ImageRepository imageRepository;
    public Image getImage(int id) {

        return imageRepository.findById(id).get();
    }
    public void saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(file.getBytes());

        imageRepository.save(image);
    }
}
