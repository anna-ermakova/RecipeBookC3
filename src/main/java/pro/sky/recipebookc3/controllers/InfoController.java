package pro.sky.recipebookc3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping
    public String hello() {
        return "Приложение запущено";
    }

    @GetMapping("info")
    String showIknfo() {
        return "Автор: Ермакова Анна. <br>" +
                "Название проекта: Recipe book. <br>" +
                "Дата создания проекта: 03/01/2023. <br> " +
                "Описание проекта: Приложение для сайта рецептов.";
    }
}

