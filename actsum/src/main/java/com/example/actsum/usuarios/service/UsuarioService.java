package com.example.actsum.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actsum.exception.ResourceNotFoundException;
import com.example.actsum.usuarios.model.Usuario;
import com.example.actsum.usuarios.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public Usuario buscarPorRut(String rut) {
        log.info("Buscando usuario con RUT: {}", rut);
        return usuarioRepository.findByRut(rut).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con RUT: " + rut));
    }

    public List<Usuario> buscarPorRol(String rol) {
        log.info("Buscando usuarios con rol: {}", rol);
        return usuarioRepository.findByRol(rol);
    }

    public Usuario crear(Usuario usuario) {
        log.info("Creando nuevo usuario con RUT: {}", usuario.getRut());

        usuarioRepository.findByRut(usuario.getRut()).ifPresent(existingUser -> {
            log.warn("⚠️ Ya existe un usuario con el rut: {}", usuario.getRut());
            throw new IllegalArgumentException("El rut ya está registrado");
        });

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado con RUT: {}", guardado.getRut());
        return guardado;
        
    }

    public Usuario actualizar(String rut, Usuario usuarioActualizado) {
        log.info("Actualizando usuario con RUT: {}", rut);
        Usuario usuarioExistente = buscarPorRut(rut);

        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setRol(usuarioActualizado.getRol());
        usuarioExistente.setRut(usuarioActualizado.getRut());

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        log.info("Usuario actualizado con RUT: {}", actualizado.getRut());
        return actualizado;
    }
    
    public void eliminar(String rut) {
        log.info("Eliminando usuario con RUT: {}", rut);
        if (!usuarioRepository.findByRut(rut).isPresent()) {
            log.error("❌ No se puede eliminar. No se encontró usuario con RUT: {}", rut);
            throw new ResourceNotFoundException("Usuario no encontrado con RUT: " + rut);
        }
        usuarioRepository.deleteById(buscarPorRut(rut).getIdUsuario());
        log.info("Usuario eliminado con RUT: {}", rut);
    }
}
