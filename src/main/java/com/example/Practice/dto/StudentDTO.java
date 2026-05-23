package com.example.Practice.dto;

import com.example.Practice.entity.Department;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must contain at least 2 characters")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Course is required")
    private String course;
    private Department department;

    // Default Constructor
    public StudentDTO() {
    }

    // Parameterized Constructor
public StudentDTO(String name, String email, String course, Department department) {
    this.name = name;
    this.email = email;
    this.course = course;
    this.department = department;
}
    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Department getDepartment() {
    return department;
}

public void setDepartment(Department department) {
    this.department = department;
}
}