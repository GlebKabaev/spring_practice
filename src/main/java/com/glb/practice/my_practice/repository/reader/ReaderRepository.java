package com.glb.practice.my_practice.repository.reader;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glb.practice.my_practice.models.Reader;

public interface ReaderRepository extends JpaRepository<Reader,Integer> {
    
}
