package com.hogwarts.school.service;

import com.hogwarts.school.model.Avatar;
import com.hogwarts.school.model.Student;
import com.hogwarts.school.repository.AvatarRepository;
import com.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    /**
     * В переменную avatarsDir будет автоматически подставлено значение avatar.
     */
    @Value("${avatars.dir.path}")//Надпись, что в переменной avatarsDir будет значение "avatar".
    private String avatarsDir; // Сама переменная куда это значение попадет.

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    /**
     * Метод сохраняет аватар (фотографию) студента в файловую систему и в базу данных.
     *
     * @param studentId
     * @param avatarFile
     * @throws IOException
     */
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        //Получение студента, берёт из базы данных студента с указанным studentId.
        Student student = studentRepository.getById(studentId);
        //Получение студента, берёт из базы данных студента с указанным studentId.
        Path filePath = Path.of(avatarsDir, student + "." + getExtension(avatarFile.getOriginalFilename()));
        //Path. Это интерфейс. В нем будем хранить путь до директории с загружаемыми файлами.
        //avatarsDir — папка, куда сохраняются аватары. student + "." + расширение
        //getExtensions() — вытаскивает расширение файла (.jpg, .png).
        //«Сохрани файл в папку /uploads/avatars под именем Иван Иванов.jpg»

        // Подготовка папки и удаление старого файла
        Files.createDirectories(filePath.getParent()); // Создаёт папки, если их нет
        Files.deleteIfExists(filePath); // Удаляет старый файл, если он был

        // Сохранение файла на Диск
        try (InputStream is = avatarFile.getInputStream(); // Поток для чтения файла, читает файл, который прислал пользователь.
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW); // Записывает его на диск.
             BufferedInputStream bis = new BufferedInputStream(is, 1024); //Буфер для ускорения чтения
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024); //Буфер для ускорения записи
        ) {
            bis.transferTo(bos); // Копирует файл из входящего потока в новый файл
        } //try-with-resources - способ работы который автоматически закрывает их после использования (даже если произошла ошибка).

        //Сохранение информации о файле в базу данных
        //Avatar — это сущность в базе данных, которая хранит Ссылку на студента.
        //Путь к файлу. Размер, тип и (опционально) содержимое файла.
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new); // Находит или создаёт новый аватар
        avatar.setStudent(student); // Привязывает к студенту
        avatar.setFilePath(filePath.toString()); // Путь к файлу (например, "/uploads/avatars/Иван Иванов.jpg")
        avatar.setFileSize(avatarFile.getSize()); // Размер файла (в байтах)
        avatar.setMediaType(avatarFile.getContentType()); // Тип файла (например, "image/jpeg")
        avatar.setData(avatarFile.getBytes()); // Сами данные файла (необязательно, если храним путь)
        avatarRepository.save(avatar); // Сохраняет в базу
    }

    //Этот метод извлекает расширение файла из его имени.
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
        //substring() вырезает часть строки от указанной позиции до конца
        //lastIndexOf(".") ищет последнюю точку в имени файла. + 1 нужно, чтобы пропустить саму точк

    }
}


