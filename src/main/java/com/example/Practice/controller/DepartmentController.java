package com.example.Practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Practice.entity.Department;
import com.example.Practice.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

@PostMapping
public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
    
    
    Department savedDepartment= departmentService.saveDepartment(department);
    return ResponseEntity.ok(savedDepartment);
}



    @GetMapping
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }
    
    
}
