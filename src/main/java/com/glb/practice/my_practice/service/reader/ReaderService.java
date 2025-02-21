package com.glb.practice.my_practice.service.reader;

import java.util.List;

import com.glb.practice.my_practice.models.Reader;

public interface ReaderService {
    public List<Reader> findAll(String field);
    Reader save(Reader reader);
    Reader findByIDReader(int id);
    Reader updateReader(Reader reader);
    void deleteReader(int id); 
    Reader thisReader();
} 
