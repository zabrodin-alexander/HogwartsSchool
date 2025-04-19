package com.hogwarts.school;

import com.hogwarts.school.controller.StudentController;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.repository.StudentRepository;
import com.hogwarts.school.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @Test
    public void saveStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "name");
        studentObject.put("age", 75);

        Student student = new Student();
        student.setId(1L);
        student.setName("name");
        student.setAge(75);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(1L))
                .andExpect((ResultMatcher) jsonPath("$.name").value("name"))
                .andExpect((ResultMatcher) jsonPath("$.age").value(75));


    }
}
