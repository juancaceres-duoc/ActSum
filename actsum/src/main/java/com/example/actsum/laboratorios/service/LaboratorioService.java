package com.example.actsum.laboratorios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actsum.exception.ResourceNotFoundException;
import com.example.actsum.laboratorios.model.Laboratorio;
import com.example.actsum.laboratorios.repository.LaboratorioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    public List<Laboratorio> listar() {
        log.info("Listando todos los laboratorios");
        return laboratorioRepository.findAll();
    }

    public Laboratorio buscarPorId(Long id) {
        log.info("Buscando laboratorio con ID: {}", id);
        return laboratorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratorio no encontrado con ID: " + id));
    }

    public List<Laboratorio> listarActivos() {
        log.info("Listando laboratorios activos");
        return laboratorioRepository.findByActivoIgnoreCase("S");
    }

    public Laboratorio crear(Laboratorio laboratorio) {
        String nombre = laboratorio.getNombre() == null ? "" : laboratorio.getNombre().trim();

        log.info("Creando nuevo laboratorio: {}", nombre);

        laboratorio.setIdLaboratorio(null);

        // default activo = 'S'
        String activo = laboratorio.getActivo();
        if (activo == null || activo.isBlank()) {
            activo = "S";
        }
        activo = activo.trim().toUpperCase();

        // normaliza campos
        laboratorio.setNombre(nombre);
        laboratorio.setActivo(activo);

        // evita duplicados por nombre (también tienes UNIQUE en BD)
        if (laboratorioRepository.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe un laboratorio con el nombre: " + nombre);
        }

        Laboratorio guardado = laboratorioRepository.save(laboratorio);
        log.info("Laboratorio creado con ID: {}", guardado.getIdLaboratorio());
        return guardado;
    }

    public Laboratorio actualizar(Long id, Laboratorio laboratorioActualizado) {
        log.info("Actualizando laboratorio con ID: {}", id);

        Laboratorio existente = buscarPorId(id);

        String nombreNuevo = laboratorioActualizado.getNombre() == null ? "" : laboratorioActualizado.getNombre().trim();
        if (nombreNuevo.isBlank()) {
            throw new IllegalArgumentException("El nombre del laboratorio es obligatorio");
        }

        laboratorioRepository.findByNombreIgnoreCase(nombreNuevo).ifPresent(other -> {
            if (!other.getIdLaboratorio().equals(id)) {
                throw new IllegalArgumentException("Ya existe un laboratorio con el nombre: " + nombreNuevo);
            }
        });

        existente.setNombre(nombreNuevo);

        if (laboratorioActualizado.getActivo() != null && !laboratorioActualizado.getActivo().isBlank()) {
            existente.setActivo(laboratorioActualizado.getActivo().trim().toUpperCase());
        }

        Laboratorio actualizado = laboratorioRepository.save(existente);
        log.info("Laboratorio actualizado con ID: {}", actualizado.getIdLaboratorio());
        return actualizado;
    }

    public Laboratorio desactivar(Long id) {
        log.info("Desactivando laboratorio con ID: {}", id);

        Laboratorio existente = buscarPorId(id);
        existente.setActivo("N");

        Laboratorio actualizado = laboratorioRepository.save(existente);
        log.info("Laboratorio desactivado con ID: {}", actualizado.getIdLaboratorio());
        return actualizado;
    }

    public void eliminar(Long id) {
        log.info("Eliminando laboratorio con ID: {}", id);

        if (!laboratorioRepository.existsById(id)) {
            log.warn("❌ No se encontró un laboratorio con el ID: {}", id);
            throw new ResourceNotFoundException("Laboratorio no encontrado con ID: " + id);
        }

        laboratorioRepository.deleteById(id);
        log.info("Laboratorio eliminado con ID: {}", id);
    }
}