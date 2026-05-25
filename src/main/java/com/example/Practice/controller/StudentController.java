package com.example.Practice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Practice.dto.StudentDTO;
import com.example.Practice.entity.Student;
import com.example.Practice.response.ApiResponse;
import com.example.Practice.service.StudentService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;






@RestController
@RequestMapping("/students")

public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }
@PostMapping
public ResponseEntity<ApiResponse<Student>> saveStudent(
        @Valid @RequestBody StudentDTO studentDTO) {

    Student student = new Student();

    student.setName(studentDTO.getName());
    student.setEmail(studentDTO.getEmail());
    student.setCourse(studentDTO.getCourse());

    // student.setDepartment(studentDTO.getDepartment());

    Student savedStudent = studentService.saveStudent(student);

    ApiResponse<Student> response =
            new ApiResponse<>(
                    true,
                    "Student created successfully",
                    savedStudent
            );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
    
    @GetMapping("/{id}")
public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {

    Student student = studentService.getStudentById(id);

    ApiResponse<Student> response=new ApiResponse<>(true,"Student fetched Successfully",student);

    return ResponseEntity.ok(response);
}

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,@Valid @RequestBody StudentDTO studentDTO) {
        Student updatedStudent=studentService.updateStudent(id, studentDTO);

        
        return ResponseEntity.ok(updatedStudent);
    }

    
@GetMapping("/pagination")
public ResponseEntity<Page<Student>> getStudentsWithPagination(Pageable pageable) {
    Page<Student> students=studentService.getStudentsWithPagination(pageable);
    return ResponseEntity.ok(students);

}
@GetMapping("/search")
public ResponseEntity<List<Student>> searchStudentByName(@RequestParam String name ) {
    List<Student> answer=studentService.searchByName(name);
    return ResponseEntity.ok(answer);
}

@GetMapping("/search/filter")
public ResponseEntity<List<Student>> searchByNameAndCourse(@RequestParam String name,@RequestParam String course) {

    return ResponseEntity.ok(
            studentService.searchByNameAndCourse(name, course)
    );
}

@GetMapping("/jpql")
public ResponseEntity<List<Student>> getStudentsUsingJPQL() {

    return ResponseEntity.ok(
            studentService.getStudentsUsingJPQL()
    );
}

@GetMapping("/department")
public ResponseEntity<List<Student>> getStudentsByDepartment(@RequestParam String departmentName) {

    return ResponseEntity.ok(studentService.getStudentsByDepartmentName(departmentName) );
}

@GetMapping("/native/course")
public ResponseEntity<List<Student>>
getStudentsByCourseNative(@RequestParam String course) {

    return ResponseEntity.ok(studentService.getStudentsByCourseNative(course));
}

}
