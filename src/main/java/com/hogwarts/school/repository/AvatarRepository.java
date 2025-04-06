package com.hogwarts.school.repository;

import com.hogwarts.school.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
