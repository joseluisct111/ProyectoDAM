package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.RegistroDiarioDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.models.RegistroDiario;
import com.dam.Proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/registroDiarios")
public class RegistroDiarioController {

    @Autowired
    private RegistroDiarioDao registroDiarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/{id}")
    public RegistroDiario getRegistroDiario(@PathVariable Long id) {
        return registroDiarioDao.getRegistroDiario(id);
    }

    @GetMapping("/mediciones/{idPluviometro}")
    public List<RegistroDiario> getRegistrosDiariosPorPluviometro(@PathVariable Long idPluviometro) {
        return registroDiarioDao.getRegistrosDiariosPorPluviometro(idPluviometro);
    }

    @GetMapping("/mediciones") // Corregido el mapeo de la ruta
    public List<RegistroDiario> getRegistrosDiarios() {
        return registroDiarioDao.getRegistrosDiarios();
    }

    @PostMapping
    public void registrarRegistroDiario(@RequestBody RegistroDiario registroDiario) {
        registroDiarioDao.registrar(registroDiario);
    }

    @DeleteMapping("/{id}")
    public void eliminarRegistroDiario(@PathVariable Long id) {
        registroDiarioDao.eliminar(id);
    }
}
