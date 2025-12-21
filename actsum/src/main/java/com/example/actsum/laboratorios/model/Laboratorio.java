package com.example.actsum.laboratorios.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "LABORATORIO")
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LABORATORIO")
    private Long idLaboratorio;

    @NotBlank(message = "El nombre del laboratorio es obligatorio")
    @Size(max = 100, message = "El nombre del laboratorio no puede superar 100 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "El campo activo es obligatorio")
    @Pattern(regexp = "^[SNsn]{1}$", message = "El campo activo debe ser 'S' o 'N'")
    @Column(name = "ACTIVO", nullable = false, length = 1)
    private String activo;
}
