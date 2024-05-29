package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.RegistroDiario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RegistroDiarioDaoImp implements RegistroDiarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RegistroDiario> getRegistrosDiarios() {
        String query = "FROM RegistroDiario";
        return entityManager.createQuery(query, RegistroDiario.class).getResultList();
    }

    @Override
    public List<RegistroDiario> getRegistrosDiariosPorPluviometro(Long idPluviometro) {
        String query = "FROM RegistroDiario r WHERE r.pluviometro.id = :idPluviometro";
        TypedQuery<RegistroDiario> typedQuery = entityManager.createQuery(query, RegistroDiario.class);
        typedQuery.setParameter("idPluviometro", idPluviometro);
        return typedQuery.getResultList();
    }

    @Override
    public void eliminar(Long id) {
        RegistroDiario registroDiario = entityManager.find(RegistroDiario.class, id);
        if (registroDiario != null) {
            entityManager.remove(registroDiario);
        }
    }

    @Override
    public void registrar(RegistroDiario registroDiario) {
        entityManager.persist(registroDiario);
    }

    @Override
    public RegistroDiario getRegistroDiario(Long id) {
        return entityManager.find(RegistroDiario.class, id);
    }

    @Override
    public List<Integer> getYears() {
        return null;
    }

    @Override
    public List<Double> getLluviaPorMes(Long pluviometroId, Integer year) {
        return null;
    }
}