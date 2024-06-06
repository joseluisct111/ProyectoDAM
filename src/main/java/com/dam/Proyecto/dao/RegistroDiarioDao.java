package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.models.RegistroDiario;
import java.util.List;

public interface RegistroDiarioDao {
    List<RegistroDiario> getRegistrosDiarios();

    List<RegistroDiario> getRegistrosDiariosPorPluviometro(Long idPluviometro);

    void eliminar(Long id);

    void registrar(RegistroDiario registroDiario);

    RegistroDiario getRegistroDiario(Long id);

    List<Integer> getYears();

    List<Double> getLluviaPorMes(Long pluviometroId, Integer year);

    Pluviometro obtenerPorId(int id);
}

