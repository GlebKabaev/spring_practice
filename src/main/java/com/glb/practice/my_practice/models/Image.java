package com.glb.practice.my_practice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "images")
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    public Image(){
        
    }
}
