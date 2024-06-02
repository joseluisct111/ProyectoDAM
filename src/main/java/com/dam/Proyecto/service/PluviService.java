package com.dam.Proyecto.service;

import com.dam.Proyecto.models.Pluviometro;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface PluviService {
    List<Pluviometro> findAll();
    Pluviometro findById(Long id);
    Pluviometro save(Pluviometro pet);
    void deleteById(Long id);
    byte[] exportPdf() throws JRException, FileNotFoundException;
    byte[] exportXls() throws JRException, FileNotFoundException;
}
