package com.glb.practice.my_practice.srevice.image;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.glb.practice.my_practice.models.Image;
import com.glb.practice.my_practice.repository.image.ImageRepository;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;

    public Image getImage(int id) {
        logger.info("Fetching image with ID: {}", id);
        return imageRepository.findById(id).orElse(null);
    }

    public Image saveImage(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setImageData(file.getBytes());
            return imageRepository.save(image);
        }
        return null;
    }
    @Transactional(readOnly = true)
    public String getImageBase64(Image image) {
        if (image != null && image.getImageData() != null) {
            return Base64.getEncoder().encodeToString(image.getImageData());
        }
        return null;
    }
}