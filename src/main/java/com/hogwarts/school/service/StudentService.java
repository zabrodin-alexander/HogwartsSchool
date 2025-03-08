package com.hogwarts.school.service;

import com.hogwarts.school.model.Student;
import com.hogwarts.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStident(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStident(long id) {
        studentRepository.deleteById(id);
    }
}


