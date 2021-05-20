package com.mangoslr.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//todo terminar los campos y etc para registrar.
@Controller
public class Registrar {
    @RequestMapping("registrar")
    public String registrar(Model model) {

        return "login/registrar";
    }
}
