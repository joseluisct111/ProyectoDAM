package com.dam.Proyecto.service.impl;

import com.dam.Proyecto.models.Pluviometro;
import com.dam.Proyecto.repository.PluviRepository;
import com.dam.Proyecto.service.PluviService;
import com.dam.Proyecto.util.PluviReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class PluviServiceImpl implements PluviService {

    @Autowired
    private PluviRepository pluviRepository;

    @Autowired
    private PluviReportGenerator pluviReportGenerator;

    @Override
    public List<Pluviometro> findAll() {
        return pluviRepository.findAll();
    }

    @Override
    public Pluviometro findById(Long id) {
        return pluviRepository.findById(id).get();
    }

    @Override
    public Pluviometro save(Pluviometro pet) {
        return pluviRepository.save(pet);
    }

    @Override
    public void deleteById(Long id) {
        pluviRepository.deleteById(id);
    }

    @Override
    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return pluviReportGenerator.exportToPdf(pluviRepository.findAll());
    }

    @Override
    public byte[] exportXls() throws JRException, FileNotFoundException {
        return pluviReportGenerator.exportToXls(pluviRepository.findAll());
    }
}
