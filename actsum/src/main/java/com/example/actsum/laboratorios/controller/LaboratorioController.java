package com.example.actsum.laboratorios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.actsum.laboratorios.model.Laboratorio;
import com.example.actsum.laboratorios.service.LaboratorioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @GetMapping
    public ResponseEntity<List<Laboratorio>> listar() {
        log.info("Listando todos los laboratorios");
        return ResponseEntity.ok(laboratorioService.listar());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Laboratorio> obtener(@PathVariable Long id) {
        log.info("[GET] Obteniendo laboratorio con ID: {}", id);
        return ResponseEntity.ok(laboratorioService.buscarPorId(id));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Laboratorio>> listarActivos() {
        log.info("[GET] Listando laboratorios activos");
        List<Laboratorio> activos = laboratorioService.listarActivos();

        if (activos.isEmpty()) {
            log.warn("⚠️ No hay laboratorios activos");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activos);
    }

    @PostMapping
    public ResponseEntity<Laboratorio> crear(@Valid @RequestBody Laboratorio laboratorio) {
        log.info("[POST] Creando laboratorio: {}", laboratorio.getNombre());
        Laboratorio creado = laboratorioService.crear(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @Valid @RequestBody Laboratorio laboratorio) {
        log.info("[PUT] Actualizando laboratorio con ID: {}", id);
        return ResponseEntity.ok(laboratorioService.actualizar(id, laboratorio));
    }  

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("[DELETE] Eliminando laboratorio con ID: {}", id);
        laboratorioService.eliminar(id);
        return ResponseEntity.ok("El laboratorio con ID " + id + " ha sido eliminado correctamente.");
    }
}