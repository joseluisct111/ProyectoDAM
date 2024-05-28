package com.dam.Proyecto.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pluviometros") // Cambio el nombre de la tabla a "pluviometros"
@ToString
public class Pluviometro { // Cambio el nombre de la clase a "Pluviometro"
    @Getter @Setter @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter @Column(name = "nombre", nullable = false) // Agrego una columna "nombre" como VARCHAR(255) NOT NULL
    private String nombre;
    @Getter @Setter @Column(name = "latitud") // Agrego una columna "latitud" como DECIMAL(10, 8) NOT NULL
    private String latitud;
    @Getter @Setter @Column(name = "longitud") // Agrego una columna "longitud" como DECIMAL(11, 8) NOT NULL
    private String longitud;
}
