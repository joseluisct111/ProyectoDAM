package com.dam.Proyecto.controllers;


import com.dam.Proyecto.dao.UsuarioDao;
import com.dam.Proyecto.models.Usuario;
import com.dam.Proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login1",method = RequestMethod.POST )
    public String login(@RequestBody Usuario usuario) {
        Usuario loginUser = usuarioDao.Credenciales(usuario);
        if (loginUser != null){

            String tokenJwt;
            tokenJwt = jwtUtil.create(loginUser.getId().toString(), loginUser.getEmail()   );

            return tokenJwt ;
        } else {
           return "fail";
        }
    }
}
