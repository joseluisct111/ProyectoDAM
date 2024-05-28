package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PluviometroDaoImp implements PluviometroDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List getPluviometros() {
        String query = "FROM Pluviometro ";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Pluviometro pluviometro = entityManager.find(Pluviometro.class, id);
        if (pluviometro != null) {
            entityManager.remove(pluviometro);
        }
    }

    @Override
    public void registrar(Pluviometro pluviometro) {
        entityManager.persist(pluviometro);
    }

    @Override
    public Pluviometro getPluviometro(Long id) {
        return entityManager.find(Pluviometro.class, id);
    }
}

