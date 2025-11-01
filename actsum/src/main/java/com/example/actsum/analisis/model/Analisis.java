package com.example.actsum.analisis.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "ANALISIS")
public class Analisis {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ANALISIS")
    private Long idAnalisis;

    @NotBlank(message = "El RUT del usuario es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]{1}$",
             message = "El RUT debe tener formato válido (ej: 12345678-9 o 1234567-K)")
    @Size(max = 20)
    @Column(name = "RUT_USUARIO", nullable = false, length = 20)
    private String rutUsuario;

    @NotBlank(message = "El nombre del análisis es obligatorio")
    @Size(max = 100)
    @Column(name = "NOMBRE_ANALISIS", nullable = false, length = 100)
    private String nombreAnalisis;

    @NotBlank(message = "El detalle del análisis es obligatorio")
    @Size(max = 100)
    @Column(name = "DETALLE_ANALISIS", nullable = false, length = 100)
    private String detalleAnalisis;

    @Size(max = 30)
    @Column(name = "UNIDAD", length = 30)
    private String unidad;

    @Size(max = 100)
    @Column(name = "LABORATORIO", length = 100)
    private String laboratorio;
}
