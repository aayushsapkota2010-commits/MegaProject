package com.example.Practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Practice.dto.StudentDTO;
import com.example.Practice.entity.Department;
import com.example.Practice.entity.Student;
import com.example.Practice.exception.StudentNotFoundException;
import com.example.Practice.repository.DepartmentRepository;
import com.example.Practice.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
private DepartmentRepository departmentRepository;

private static final Logger logger =
        LoggerFactory.getLogger(StudentService.class);

    public Student saveStudent(Student student)
    {
        Department department = departmentRepository
        .findByName(student.getCourse())
        .orElseThrow(() -> new RuntimeException("Department not found"));

student.setDepartment(department);

logger.info("Saving student: {}", student.getName());
        return studentRepo.save(student);

    }

    public List<Student> getAllStudents()
    {
        return studentRepo.findAll();
    }
  public Student getStudentById(Long id) {

    return studentRepo.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
}

public Student updateStudent(Long id, StudentDTO studentDTO)
{
    Student existingStudent=studentRepo.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
            existingStudent.setName(studentDTO.getName());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setCourse(studentDTO.getCourse());

            return studentRepo.save(existingStudent);

}

public void deleteStudent(Long id)
{
     Student existingStudent=studentRepo.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
            studentRepo.delete(existingStudent);
}

public Page<Student> getStudentsWithPagination(Pageable pageable)
{
    return studentRepo.findAll(pageable);

}

public List<Student> searchByName(String name){
    return studentRepo.findByNameContaining(name);
}

public List<Student> searchByNameAndCourse(String name,String course) {

    return studentRepo.findByNameContainingAndCourseContaining(name, course);
}

public List<Student> getStudentsUsingJPQL() {

    return studentRepo.getAllStudentsUsingJPQL();
}


public List<Student> getStudentsByDepartmentName(String departmentName) {

    return studentRepo.getStudentsByDepartmentName(departmentName);
}

public List<Student> getStudentsByCourseNative(
        String course
) {

    return studentRepo
            .getStudentsByCourseNative(course);
}


}
