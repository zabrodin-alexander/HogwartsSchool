package com.hogwarts.school.repository;

import com.hogwarts.school.model.Faculty;
import com.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByStudents(List<Student>students);


}
