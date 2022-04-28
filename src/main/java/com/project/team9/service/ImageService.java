package com.project.team9.service;

import com.project.team9.model.Image;
import com.project.team9.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    public Image getById(String id) {
        return imageRepository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Optional<Image> getImageByPath(String path) {
        return imageRepository.findByPath(path);
    }
}

