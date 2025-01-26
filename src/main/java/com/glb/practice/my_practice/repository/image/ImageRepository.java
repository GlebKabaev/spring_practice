package com.glb.practice.my_practice.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glb.practice.my_practice.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}

