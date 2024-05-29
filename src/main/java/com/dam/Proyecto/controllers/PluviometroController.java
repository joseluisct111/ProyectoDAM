package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.PluviometroDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.dao.RegistroDiarioDao;
import com.dam.Proyecto.models.RegistroDiario;
import org.springframework.web.bind.annotation.RequestParam;
import com.dam.Proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Pluviometro getPluviometro(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        return pluviometroDao.getPluviometro(id);
    }

    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.GET)
    public List<Pluviometro> getPluviometros(@RequestHeader("Authorization") String token) {

        // poner alerta


        return pluviometroDao.getPluviometros();
}

    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.POST)
    public void registrarPluviometro(@RequestBody Pluviometro pluviometro ) {
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
}

