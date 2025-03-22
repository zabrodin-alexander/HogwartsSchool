
package com.hogwarts.school.controller;

import com.hogwarts.school.model.Faculty;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getInfoStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping("/age-between")
    public Collection<Student> findByAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("deleteAll")
    public void deletAllStudent() {
        studentService.deletAllStudent();
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getFacultyByStudentId(@PathVariable Long studentId){
        return studentService.getFacultyByStudentId(studentId);
    }
    @GetMapping("/faculty/{facultyId}")
    public List<Student> getStudentsByFacultyId(@PathVariable Long facultyId) {
        return studentService.getStudentsByFacultyId(facultyId);
    }
}