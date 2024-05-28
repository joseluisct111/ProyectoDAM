package com.dam.Proyecto.controllers;

import com.dam.Proyecto.dao.PluviometroDao;
import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PluviometroController {

    @Autowired
    private PluviometroDao pluviometroDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.GET)
    public Pluviometro getPluviometro(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!validarToken(token)) {
            return null;
        }
        return pluviometroDao.getPluviometro(id);
    }

    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.GET)
    public List<Pluviometro> getPluviometros(@RequestHeader("Authorization") String token) {
        if (!validarToken(token)) {
            return null;
        }
        // poner alerta


        return pluviometroDao.getPluviometros();
}

    @RequestMapping(value = "/api/pluviometros", method = RequestMethod.POST)
    public void registrarPluviometro(@RequestBody Pluviometro pluviometro, @RequestHeader("Authorization") String token) {
        if (!validarToken(token)) {
            return;
        }
        pluviometroDao.registrar(pluviometro);
    }

    @RequestMapping(value = "/api/pluviometros/{id}", method = RequestMethod.DELETE)
    public void eliminarPluviometro(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!validarToken(token)) {
            return;
        }
        pluviometroDao.eliminar(id);
    }

    private boolean validarToken(String token) {
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }
}

