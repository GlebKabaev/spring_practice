package com.glb.practice.my_practice.exception;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(String message) {
        super(message);
    }
}
