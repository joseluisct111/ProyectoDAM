package com.dam.Proyecto.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.dam.Proyecto.models.Pluviometro;

@Entity
@Table(name = "registro_diario") // Cambio el nombre de la tabla a "registro_diario"
@ToString
public class RegistroDiario { // Cambio el nombre de la clase a "RegistroDiario"
    @Getter @Setter @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter @Column(name = "fecha", nullable = false) // Agrego una columna "fecha" como DATE NOT NULL
    private LocalDate fecha;

    @Getter @Setter @Column(name = "pluviometro_id", nullable = false) // Agrego una columna "pluviometro_id" como BIGINT NOT NULL
    private Integer pluviometroId;

    @Getter @Setter @Column(name = "volumen_lluvia", nullable = false) // Agrego una columna "volumen_lluvia" como DECIMAL NOT NULL
    private BigDecimal volumenLluvia;

    // Agrego la relación con la tabla "pluviometros"
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pluviometro_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Setter @Getter
    private Pluviometro pluviometro;


}
