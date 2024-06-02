package com.dam.Proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/inicio")
    public String gohome(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "inicio";
    }
    @GetMapping("/usuario")
    public String usuario(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "usuarios";
    }
    @GetMapping("/pluviometro")
    public String pluviometro(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "pluviometros";
    }
    @GetMapping("/cargardatos")
    public String cargardatos(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "cargadatospluvi";
    }
    @GetMapping("/datospluviometros")
    public String datospluviometro(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "registropluviometros";
    }
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "login";
    }
    @GetMapping("/registeruser")
    public String registeruser(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "registeruser";
    }
    @GetMapping("/registerpluvi")
    public String registerpluvi(Model model) {
        model.addAttribute("titulo", "Bienvenido a la aplicación de registro de lluvias");
        return "registerpluvi";
    }
}
