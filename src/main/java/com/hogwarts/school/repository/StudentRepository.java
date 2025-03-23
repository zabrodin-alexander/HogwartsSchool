package com.hogwarts.school.repository;

import com.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    List<Student> getStudentsByFacultyId(Long id);


}
