package com.hogwarts.school;

import com.hogwarts.school.controller.StudentController;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Тестируем только веб-слой (контроллеры) без поднятия всего контекста Spring
@WebMvcTest(controllers = StudentController.class)// Указываем, какой контроллер тестируем
public class StudentControllerWebTest {

    // Внедряем MockMvc для отправки "фейковых" HTTP-запросов
    @Autowired
    private MockMvc mockMvc;

    // Создаем мок-версию сервиса (реальный StudentService не будет использоваться)
    @MockBean
    private StudentService studentService;

    @Test
    public void saveStudentTest() throws Exception {
        // Создаем JSON, который будет отправлен в теле запроса
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "name");// Поле "name"
        studentObject.put("age", 75); // Поле "age"

        // Создаем объект-заглушку, который должен вернуть мок-сервис
        Student student = new Student();
        student.setId(1L);// ID будет проверяться в ответе
        student.setName("name");// Имя должно совпадать с запросом
        student.setAge(75);// Возраст должен совпадать с запросом

        // Настройка поведения мок-сервиса
        // "Когда вызовут studentService.addStudent() с ЛЮБЫМ объектом Student,
        // тогда верни наш подготовленный объект student"
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        // Отправка HTTP-запроса и проверки
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") // Отправляем POST-запрос
                        .content(studentObject.toString())//Тело запроса (наш JSON)
                        .contentType(MediaType.APPLICATION_JSON) // Говорим, что отправляем JSON
                        .accept(MediaType.APPLICATION_JSON))// Ожидаем JSON в ответе.
                // Проверки результата
                .andExpect(status().isOk())// Ожидаем HTTP 200 (если 404 - проблема с маппингом
                //Проверяем тело JSON-ответа:
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))  // Должен вернуться id = 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name")) // Имя должно совпадать
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(75)); // Возраст должен совпадать
    }

    @Test
    public void findStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "name");
        studentObject.put("age", 75);

        Student student = new Student();
        student.setId(1L);
        student.setName("name");
        student.setAge(75);

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(75));
    }

}
