package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.PluviometroDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.dao.RegistroDiarioDao;
import com.dam.Proyecto.models.RegistroDiario;
import com.dam.Proyecto.service.PluviService;
import com.dam.Proyecto.util.JWTUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class PluviometroController {

    @Autowired
    private PluviometroDao pluviometroDao;
    @Autowired
    private PluviService pluviservice;
    @Autowired
    private RegistroDiarioDao registroDiarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.GET)
    public Pluviometro getPluviometro(@PathVariable Long id, @RequestHeader("Authorization") String token) {
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

    @GetMapping("/api/pluviometros/years")
    public List<Integer> getYears() {
        return registroDiarioDao.getYears();
    }

    @GetMapping("/api/pluviometros/lluviaPorMes")
    public List<Double> getLluviaPorMes(@RequestParam Long pluviometroId, @RequestParam Integer year) {
        return registroDiarioDao.getLluviaPorMes(pluviometroId, year);
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf() {
        try {
            byte[] pdfBytes = pluviservice.exportPdf();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "pluviometros.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace(); // Manejo de excepciones, puedes personalizarlo según tus necesidades
            return ResponseEntity.status(500).body("Error al generar el PDF".getBytes());
        }
    }

    @GetMapping("/export-xls")
    public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
        var contentDisposition = ContentDisposition.builder("attachment")
                .filename("petsReport" + ".xls").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(pluviservice.exportXls());
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
