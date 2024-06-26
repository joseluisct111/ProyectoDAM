package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.models.RegistroDiario;
import java.util.List;

public interface RegistroDiarioDao {
    List<Object[]> getRegistrosDiarios();

    List<RegistroDiario> getRegistrosDiariosPorPluviometro(Long idPluviometro);

    void eliminar(Long id);

    void registrar(RegistroDiario registroDiario);

    RegistroDiario getRegistroDiario(Long id);



    List<Double> getLluviaPorMes(Long pluviometroId, Integer year);

    Pluviometro obtenerPorId(int id);

    List<Double> getVolumenesLluviaPorMes(int year);
}

