package com.dam.Proyecto.dao;

import com.dam.Proyecto.models.Pluviometro;
import java.util.List;

public interface PluviometroDao {


    List<Pluviometro> getPluviometros();

    void eliminar(Long id);

    Pluviometro obtenerPorId(int id);

    void registrar(Pluviometro pluviometro);

    Pluviometro getPluviometro(Long id);

    Long contarPluviometros();
}

