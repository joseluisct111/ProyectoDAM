package com.dam.Proyecto.repository;

import com.dam.Proyecto.models.Pluviometro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluviRepository extends JpaRepository<Pluviometro, Long> {
}
