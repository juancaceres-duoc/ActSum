package com.example.actsum.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.actsum.usuarios.model.Usuario;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByRut(String rut);
    List<Usuario> findByRolIgnoreCase(String rol);    
    Optional<Usuario> findByCorreo(String correo);
}
