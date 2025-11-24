package com.example.actsum.usuarios.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula y un número"
    )
    @Column(nullable = false, length = 100)
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(?i)(ADMIN|TECNICO|CLIENTE)$", message = "El rol debe ser ADMIN, TECNICO o CLIENTE")
    @Column(nullable = false, length = 20)
    private String rol;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(
        regexp = "^[0-9]{7,8}-[0-9Kk]{1}$",
        message = "El RUT debe ingresarse sin puntos y con guion (ej: 12345678-9 o 1234567-K)"
    )
    @Column(name="RUT_DNI", nullable = false, unique = true, length = 20)
    private String rut;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    @Column(name = "CORREO", nullable = false, unique = true, length = 100)
    private String correo;

    @PrePersist
    @PreUpdate
    private void normalizarCampos() {
        if (rol != null) {
            rol = rol.trim().toUpperCase();
        }

        if (rut != null) {
            rut = rut.trim().toUpperCase();
        }

        if (username != null) {
            username = username.trim();
        }

        if (nombre != null) {
            nombre = nombre.trim();
        }

        if (correo != null) {
            correo = correo.trim().toLowerCase();
        }
    }

}