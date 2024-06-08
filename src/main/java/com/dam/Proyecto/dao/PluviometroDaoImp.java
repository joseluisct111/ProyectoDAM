package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PluviometroDaoImp implements PluviometroDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pluviometro> getPluviometros() {
        String query = "FROM Pluviometro WHERE ";
        return entityManager.createQuery(query, Pluviometro.class).getResultList();
    }


    @Override
    public void eliminar(Long id) {
        Pluviometro pluviometro = entityManager.find(Pluviometro.class, id);
        if (pluviometro != null) {
            entityManager.remove(pluviometro);
        }
    }
    @Override
    public Pluviometro obtenerPorId(int id) {
    Pluviometro pluviometro = entityManager.find(Pluviometro.class, id);
    if (pluviometro != null) {
        return pluviometro;
    } else {
        return null;
    }
}
    @Override
    public void registrar(Pluviometro pluviometro) {
        entityManager.merge(pluviometro);
    }

    @Override
    public Pluviometro getPluviometro(int id) {
        return entityManager.find(Pluviometro.class, id);
    }
    @Override
    public Long contarPluviometros() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Pluviometro p");
        return (Long) query.getSingleResult();
    }
}

