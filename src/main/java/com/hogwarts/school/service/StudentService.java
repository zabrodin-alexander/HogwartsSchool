package com.hogwarts.school.service;

import com.hogwarts.school.model.Faculty;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyService facultyService;


    public StudentService(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
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

    public List<Student> findStudentsByFacultyId(Long facultyId) {
        Faculty faculty = facultyService.findFaculty(facultyId);
        return studentRepository.findAllByFaculty(faculty);
    }

}




