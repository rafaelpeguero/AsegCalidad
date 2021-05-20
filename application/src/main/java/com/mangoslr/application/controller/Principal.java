package com.mangoslr.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Principal {
    @RequestMapping
    public String index(Model model) {
        return "mangoMain/index";
    }
}
