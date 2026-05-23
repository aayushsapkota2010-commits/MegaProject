package com.example.Practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Practice.entity.Department;
import com.example.Practice.repository.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department)
    {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments()
    {
        return departmentRepository.findAll();
    }
    
}
