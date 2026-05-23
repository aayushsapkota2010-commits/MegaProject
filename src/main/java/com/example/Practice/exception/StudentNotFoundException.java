package com.example.Practice.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message)
    {
        super(message);
    }
    
}
