package com.dam.Proyecto.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "usuarios1")
@ToString
public class Usuario {
    @Getter  @Setter @Column(name = "id")
    @Id
    //Genera un valor autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter  @Setter @Column(name = "nombre")
    private String nombre;
    @Getter @Setter @Column(name = "apellido")
    private String apellido;
    @Getter @Setter @Column(name = "email")
    private String email;
    @Getter @Setter @Column(name = "telefono")
    private String telefono;
    @Getter @Setter @Column(name = "password")
      private String password;

}
