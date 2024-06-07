package com.dam.Proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/inicio")
    public String gohome(Model model) {
        return "inicio";
    }
    @GetMapping("/usuario")
    public String usuario(Model model) {
        return "usuarios";
    }
    @GetMapping("/pluviometro")
    public String pluviometro(Model model) {
        return "pluviometros";
    }
    @GetMapping("/mapa")
    public String mapa(Model model) {
        return "mapapluviometros";
    }
    @GetMapping("/cargardatos")
    public String cargardatos(Model model) {
        return "cargadatospluvi";
    }
    @GetMapping("/datospluviometros")
    public String datospluviometro(Model model) {
        return "registroPluviometros";
    }
    @GetMapping("/")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/registeruser")
    public String registeruser(Model model) {
        return "registeruser";
    }
    @GetMapping("/registerpluvi")
    public String registerpluvi(Model model) {
        return "registerpluvi";
    }
    @GetMapping("/forgotpassword")
    public String forgotpassword(Model model) {
        return "forgot-password";
    }
}
