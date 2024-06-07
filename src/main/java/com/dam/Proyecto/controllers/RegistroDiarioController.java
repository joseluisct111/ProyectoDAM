package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.PluviometroDao;
import com.dam.Proyecto.dao.RegistroDiarioDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.models.RegistroDiario;
import com.dam.Proyecto.util.JWTUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/registroDiarios")
public class RegistroDiarioController {

    @Autowired
    private RegistroDiarioDao registroDiarioDao;

    @Autowired
    private PluviometroDao pluviometroDao;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public RegistroDiario getRegistroDiario(@PathVariable Long id) {
        return registroDiarioDao.getRegistroDiario(id);
    }

    @GetMapping("/mediciones/{idPluviometro}")
    public List<RegistroDiario> getRegistrosDiariosPorPluviometro(@PathVariable Long idPluviometro) {
        return registroDiarioDao.getRegistrosDiariosPorPluviometro(idPluviometro);
    }

    @GetMapping("/mediciones")
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

    // Nuevo método para cargar el archivo

    @PostMapping("/cargar")
    public String cargarArchivo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Por favor, seleccione un archivo.";
        }

        try {
            // Obtener los primeros dos caracteres del nombre del archivo como pluviometroId
            String filename = file.getOriginalFilename();
            if (filename == null || filename.length() < 2) {
                return "Nombre del archivo no válido.";
            }
            int pluviometroId = Integer.parseInt(filename.substring(0, 2));

            // Obtener el objeto Pluviometro correspondiente al pluviometroId
            Pluviometro pluviometro = pluviometroDao.obtenerPorId(pluviometroId);
            if (pluviometro == null) {
                return "No se encontró un pluviómetro con el ID proporcionado.";
            }

            // Convertir el archivo JSON en una lista de objetos RegistroDiario
            List<RegistroDiario> registros = objectMapper.readValue(file.getInputStream(), new TypeReference<List<RegistroDiario>>() {});

            // Registrar cada registro en la base de datos
            for (RegistroDiario registro : registros) {
                registro.setPluviometroId(pluviometroId);
                registro.setPluviometro(pluviometro); // Establecer el objeto Pluviometro
                registroDiarioDao.registrar(registro);
            }

            return "Archivo cargado y datos registrados exitosamente.";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error al procesar el archivo.";
        }
    }

    @GetMapping("/volumen-lluvia")
    public ResponseEntity<?> getVolumenLluviaPorMes(@RequestParam int year) {
        try {
            // Obtener los datos del volumen de lluvia por mes de tu base de datos
            List<Double> volumenesLluviaPorMes = registroDiarioDao.getVolumenesLluviaPorMes(year);

            // Devolver los datos como respuesta
            return ResponseEntity.ok(volumenesLluviaPorMes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los datos del volumen de lluvia por mes.");
        }
    }





}

