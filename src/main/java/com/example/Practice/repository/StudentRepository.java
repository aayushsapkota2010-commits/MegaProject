package com.example.Practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Practice.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByNameContaining(String name);
    List<Student> findByNameContainingAndCourseContaining(String name, String course);
    @Query("SELECT s FROM Student s")
List<Student> getAllStudentsUsingJPQL();
@Query("""
       SELECT s FROM Student s JOIN s.department d WHERE d.name = :departmentName""")
List<Student> getStudentsByDepartmentName(@Param("departmentName") String departmentName);

@Query(
       value = """
               SELECT *
               FROM student
               WHERE course = :course
               """,
       nativeQuery = true
)
List<Student> getStudentsByCourseNative(@Param("course") String course);
}
