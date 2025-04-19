package com.hogwarts.school.controller;

import com.hogwarts.school.service.AvatarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //"/{studentId}/avatar" — URL, по которому можно отправить аватар.
    //Например: http://Сайт/students/1/avatar (где 1 — studentId).
    //consumes = MediaType.MULTIPART_FORM_DATA_VALUE — означает, что метод принимает файлы (например, изображения).
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        //throwsIOException - выбросит ошибку если фото не будет загружено успешно
        //ResponseEntity<String> - метод возвращает ответ сервера (например, 200 OK или ошибку).
        //uploadAvatar - метод загрузки аватарки
        //@PathVariable Long studentId - Id студента, который берется из URL
        //@RequestParam MultipartFile avatar - берёт файл из формы отправки. MultipartFile avatar — это само фото (или другой файл), которое пришло от пользователя.-
        avatarService.uploadAvatar(studentId, avatar);
        //avatarService — это другой класс, который сохраняет фото (например, на диск или в базу данных).
        //uploadAvatar(studentId, avatar) — передаёт ID студента и его фото в сервис для сохранения
        return ResponseEntity.ok().build();
        //ResponseEntity.ok() — возвращает статус 200 OK (всё прошло успешно).
        //.build() — создаёт ответ без дополнительных данных.
    }

}
