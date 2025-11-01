package com.example.actsum.analisis.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.actsum.analisis.model.Analisis;
import com.example.actsum.analisis.service.AnalisisService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Slf4j
@Validated
@RestController
@RequestMapping("/api/analisis")
public class AnalisisController {
    private final AnalisisService analisisService;
    
    public AnalisisController(AnalisisService analisisService) {
        this.analisisService = analisisService;
    }
    
    @GetMapping
    public ResponseEntity<List<Analisis>> listar(){
        log.info("Listando todos los análisis");
        return ResponseEntity.ok(analisisService.listar());
    }   
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Analisis> obtener(@PathVariable Long id) {
        log.info("[GET] Obteniendo análisis con ID: {}", id);        
        return ResponseEntity.ok(analisisService.buscarPorId(id));
    }

    @GetMapping("/rut/{rutUsuario}")
    public ResponseEntity<List<Analisis>> obtenerPorRutUsuario(@PathVariable String rutUsuario) {
        log.info("[GET] Obteniendo análisis con RUT de usuario: {}", rutUsuario);        
        List<Analisis> analisisPorRut = analisisService.buscarPorRutUsuario(rutUsuario);
        if(analisisPorRut.isEmpty()) {
            log.warn("⚠️ No se encontraron análisis para el RUT de usuario: {}", rutUsuario);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(analisisPorRut);
    }

    @GetMapping("/laboratorio/{laboratorio}")
    public ResponseEntity<List<Analisis>> obtenerPorLaboratorio(@PathVariable String laboratorio) {
        log.info("[GET] Obteniendo análisis en laboratorio: {}", laboratorio);        
        List<Analisis> analisisPorLaboratorio = analisisService.buscarPorLaboratorio(laboratorio);
        if(analisisPorLaboratorio.isEmpty()) {
            log.warn("⚠️ No se encontraron análisis en el laboratorio: {}", laboratorio);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(analisisPorLaboratorio);
    }

    @PostMapping
    public ResponseEntity<Analisis> crear(@Valid @RequestBody Analisis analisis) {
        log.info("[POST] Creando análisis para el RUT de usuario: {}", analisis.getRutUsuario());        
        Analisis creado = analisisService.crear(analisis);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Analisis> actualizar(@PathVariable Long id, @Valid @RequestBody Analisis analisis) {
        log.info("[PUT] Actualizando análisis con ID: {}", id);  
        return ResponseEntity.ok(analisisService.actualizar(id, analisis));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("[DELETE] Eliminando análisis con ID: {}", id);  
        analisisService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
