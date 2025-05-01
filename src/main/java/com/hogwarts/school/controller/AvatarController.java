package com.hogwarts.school.controller;

import com.hogwarts.school.model.Avatar;
import com.hogwarts.school.service.AvatarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page")
    public List<Avatar> findAll(@RequestParam int page, @RequestParam int size) {
        return avatarService.findAll(page, size);
    }

}
