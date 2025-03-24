package com.hogwarts.school.controller;

import com.hogwarts.school.model.Faculty;

import com.hogwarts.school.model.Student;
import com.hogwarts.school.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getInfoFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @GetMapping("/color")
    public Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase(@RequestParam(required = false) String name,
                                                                            @RequestParam(required = false) String color) {
        return facultyService.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
    @GetMapping("/{studentId}/faculty")
    public Faculty findFacultyByStudent(@RequestParam List<Student>students) {
        return facultyService.findFacultyByStudents(students);
    }
}


