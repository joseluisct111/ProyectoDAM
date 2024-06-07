package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.models.RegistroDiario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class RegistroDiarioDaoImp implements RegistroDiarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getRegistrosDiarios() {
        String query = "SELECT r.id, r.fecha, p.nombre, r.volumenLluvia FROM RegistroDiario r JOIN r.pluviometro p";
        return entityManager.createQuery(query, Object[].class).getResultList();
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
    public List<Double> getLluviaPorMes(Long pluviometroId, Integer year) {
        return null;
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
    public List<Double> getVolumenesLluviaPorMes(int year) {
        String query = "SELECT MONTH(r.fecha), SUM(r.volumenLluvia) FROM RegistroDiario r WHERE YEAR(r.fecha) = :year GROUP BY MONTH(r.fecha)";
        List<Object[]> results = entityManager.createQuery(query)
                .setParameter("year", year)
                .getResultList();

        // Inicializar un array con 12 ceros
        Double[] volumenes = new Double[12];
        Arrays.fill(volumenes, 0.0);

        // Llenar el array con los resultados de la consulta
        for (Object[] result : results) {
            int month = (int) result[0];
            BigDecimal sum = (BigDecimal) result[1];
            volumenes[month - 1] = sum.doubleValue();
        }

        System.out.println(Arrays.toString(volumenes));
        return Arrays.asList(volumenes);
    }


    // Otros métodos implementados
}

