package com.hogwarts.school.service;

import com.hogwarts.school.model.Faculty;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public void deletAllStudent() {
        studentRepository.deleteAll();
    }


    public Faculty getFacultyByStudentId(Long studentId) {
        return studentRepository.getFacultyByStudentId(studentId);

    }

    public List<Student> getStudentsByFacultyId(Long facultyId) {
        return studentRepository.getStudentsByFacultyId(facultyId);
    }
}




