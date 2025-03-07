package com.glb.practice.my_practice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "content_type", nullable = false)
    private String contentType;
    
    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;
}