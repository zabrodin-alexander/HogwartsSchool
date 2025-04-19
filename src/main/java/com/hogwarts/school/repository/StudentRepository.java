package com.hogwarts.school.repository;

import com.hogwarts.school.model.Faculty;
import com.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    List<Student> findAllByFaculty(Faculty faculty);

    @Query("SELECT s FROM Student s JOIN FETCH s.faculty WHERE s.id =:studentId")
    Optional<Student> findByIdWithFaculty(@Param("studentId") Long studentId);
}
