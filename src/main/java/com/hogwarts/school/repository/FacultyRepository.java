package com.hogwarts.school.repository;

import com.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyRepository  extends JpaRepository<Faculty,Long> {
}
