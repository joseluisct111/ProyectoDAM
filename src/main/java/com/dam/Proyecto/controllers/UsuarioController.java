package com.dam.Proyecto.controllers;


import com.dam.Proyecto.dao.UsuarioDao;
import com.dam.Proyecto.models.Usuario;
import com.dam.Proyecto.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan.perez@pepe.es");
        usuario.setTelefono("123456789");
        usuario.setPassword("1234");
        return usuario;


    }
    @RequestMapping(value = "api/usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuario(@RequestHeader("Authorization") String token) {
        if (!validarToken(token)) {
            return null;
        }
        return usuarioDao.getUsuarios();
    }
    @RequestMapping(value = "api/usuarios",method = RequestMethod.POST )
    public void registrarUsuario(@RequestBody Usuario usuario) {

       // Cogemos la contraseña que recibimos en el body y la encriptamos(hashear)
        // Creamos una variable de la libreria de encriptacion Argon2
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);


       usuarioDao.registrar(usuario);
    }

    private boolean validarToken(String token) {
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }
    @RequestMapping(value = "api/login",method = RequestMethod.POST )
public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario usuario) {
    Usuario loginUser = usuarioDao.Credenciales(usuario);
    if (loginUser != null){
        String tokenJwt = jwtUtil.create(loginUser.getId().toString(), loginUser.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("token", tokenJwt);
        response.put("administrador", loginUser.getAdministrador());
        return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
    public Usuario editar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan.perez@pepe.es");
        usuario.setTelefono("123456789");
        usuario.setPassword("1234");
        return usuario;


    }
    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (!validarToken(token)) {
            return ;
        }
        usuarioDao.eliminar(id);

    }


}
