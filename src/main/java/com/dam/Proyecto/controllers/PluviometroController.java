package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.PluviometroDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.dao.RegistroDiarioDao;
import com.dam.Proyecto.models.RegistroDiario;
import com.dam.Proyecto.util.JWTUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class PluviometroController {

    @Autowired
    private PluviometroDao pluviometroDao;
    @Autowired
    private RegistroDiarioDao registroDiarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.GET)
    public Pluviometro getPluviometro(@PathVariable int id) {
        return pluviometroDao.getPluviometro(id);
    }

    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.GET)
    public List<Pluviometro> getPluviometros(@RequestHeader("Authorization") String token) {
        return pluviometroDao.getPluviometros();
    }
    @RequestMapping(value = "/api/pluviometros/count", method = RequestMethod.GET)
    public Long contarPluviometros(@RequestHeader("Authorization") String token) {
        return pluviometroDao.contarPluviometros();
    }
    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.POST)
    public void registrarPluviometro(@RequestBody Pluviometro pluviometro) {
        pluviometroDao.registrar(pluviometro);
    }

    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.DELETE)
    public void eliminarPluviometro(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        pluviometroDao.eliminar(id);
    }

    @GetMapping("/api/pluviometros/lluviaPorMes")
    public List<Double> getLluviaPorMes(@RequestParam Long pluviometroId, @RequestParam Integer year) {
        return registroDiarioDao.getLluviaPorMes(pluviometroId, year);
    }
    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.PUT)
public void modificarPluviometro(@PathVariable int id, @RequestBody Pluviometro nuevoPluviometro) {
    Pluviometro pluviometroExistente = pluviometroDao.getPluviometro(id);
    if (pluviometroExistente != null) {
        pluviometroExistente.setNombre(nuevoPluviometro.getNombre());
        pluviometroExistente.setLatitud(nuevoPluviometro.getLatitud());
        pluviometroExistente.setLongitud(nuevoPluviometro.getLongitud());
        pluviometroExistente.setActivo(nuevoPluviometro.isActivo());
        pluviometroDao.registrar(pluviometroExistente);
    }
}

    // Método para cargar archivo JSON y registrar registros diarios
    @PostMapping("/api/registros/cargar")
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

            // Convertir el archivo JSON en una lista de objetos RegistroDiario
            ObjectMapper objectMapper = new ObjectMapper();
            List<RegistroDiario> registros = objectMapper.readValue(file.getInputStream(), new TypeReference<List<RegistroDiario>>() {});

            // Registrar cada registro en la base de datos
            for (RegistroDiario registro : registros) {
                registro.setPluviometroId(pluviometroId);
                registroDiarioDao.registrar(registro);
            }

            return "Archivo cargado y datos registrados exitosamente.";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error al procesar el archivo.";
        }
    }
}
