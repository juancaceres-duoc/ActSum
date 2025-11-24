package com.example.actsum.usuarios.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.actsum.exception.ResourceNotFoundException;
import com.example.actsum.usuarios.model.Usuario;
import com.example.actsum.usuarios.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        log.info("Listando todos los usuarios");
        return ResponseEntity.ok(usuarioService.listar());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        log.info("[GET] Obteniendo usuario con ID: {}", id);
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Usuario> obtenerPorRut(@PathVariable String rut) {
        log.info("[GET] Obteniendo usuario con RUT: {}", rut);
        return ResponseEntity.ok(usuarioService.buscarPorRut(rut));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerPorRol(@PathVariable String rol) {
        log.info("[GET] Obteniendo usuarios con rol: {}", rol);
        List<Usuario> usuariosPorRol = usuarioService.buscarPorRol(rol);
        if (usuariosPorRol.isEmpty()) {
            log.warn("⚠️ No se encontraron usuarios con el rol: {}", rol);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuariosPorRol);

    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario) {
        log.info("[POST] Creando usuario con RUT: {}", usuario.getRut());
        Usuario creado = usuarioService.crear(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/rut/{rut}")
    public ResponseEntity<Usuario> actualizar(@PathVariable String rut, @Valid @RequestBody Usuario usuario) {
        log.info("[PUT] Actualizando usuario con RUT: {}", rut);
        return ResponseEntity.ok(usuarioService.actualizar(rut, usuario));
    }

    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable String rut) {
        log.info("[DELETE] Eliminando usuario con RUT: {}", rut);
        usuarioService.eliminar(rut);
        return ResponseEntity.ok("El usuario con RUT " + rut + " ha sido eliminado correctamente.");
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<Map<String, Object>> recuperarPassword(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");

        Map<String, Object> resp = new HashMap<>();
        try {
            String passwordActual = usuarioService.recuperarPasswordPorCorreo(correo);

            resp.put("existe", true);
            resp.put("mensaje",
                    "El correo existe en el sistema, su contraseña actual es: " + passwordActual +
                            ". Será bloqueada y se enviarán instrucciones para generar una contraseña a su correo.");
            resp.put("passwordActual", passwordActual);

            return ResponseEntity.ok(resp);
        } catch (ResourceNotFoundException ex) {
            resp.put("existe", false);
            resp.put("mensaje", "El correo no existe en el sistema.");
            return ResponseEntity.ok(resp);
        }
    }
}
