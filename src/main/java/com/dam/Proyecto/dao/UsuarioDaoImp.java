package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Repository
@Transactional

public class UsuarioDaoImp implements UsuarioDao {
    private static final Logger logger = LogManager.getLogger(UsuarioDaoImp.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List getUsuarios() {

        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }
    @Override
    public void registrar(Usuario usuario) {
        logger.info("Registrando usuario: " + usuario.getEmail());
        entityManager.merge(usuario);
    }
    @Override
    public Usuario Credenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email ";
        Usuario result = null;
        try {
            result = (Usuario) entityManager.createQuery(query)
                    .setParameter("email", usuario.getEmail())
                    .getSingleResult();
        } catch (NoResultException e) {
            // No se encontró ningún resultado, lo cual puede ser válido dependiendo de la lógica de tu aplicación
        }
        String passwordHashed = result.getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())) {
            return result;
        }
        return null;
    }
}