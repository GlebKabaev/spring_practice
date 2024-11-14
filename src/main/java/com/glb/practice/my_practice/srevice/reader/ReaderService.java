package com.glb.practice.my_practice.srevice.reader;

import java.util.List;

import com.glb.practice.my_practice.models.Reader;

public interface ReaderService {
    public List<Reader> getReaders(String field);
    Reader saveReader(Reader reader);
    Reader findByIDReader(int id);
    Reader updateReader(Reader reader);
    void deleteReader(int id);  
} 
