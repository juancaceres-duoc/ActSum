package com.example.actsum.analisis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.actsum.exception.ResourceNotFoundException;
import com.example.actsum.analisis.model.Analisis;
import com.example.actsum.analisis.repository.AnalisisRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalisisService {
    private final AnalisisRepository analisisRepository;

    public AnalisisService(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }
    
    public List<Analisis> listar() {
        log.info("Listando todos los análisis");
        return analisisRepository.findAll();
    }

    public Analisis buscarPorId(Long id) {
        log.info("Buscando análisis con ID: {}", id);
        return analisisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Análisis no encontrado con ID: " + id));
    }

    public List<Analisis> buscarPorRutUsuario(String rutUsuario) {
        log.info("Buscando análisis con RUT de usuario: {}", rutUsuario);
        return analisisRepository.findByRutUsuario(rutUsuario);
    }

    public List<Analisis> buscarPorLaboratorio(String laboratorio) {
        log.info("Buscando análisis en laboratorio: {}", laboratorio);
        return analisisRepository.findByLaboratorio(laboratorio);
    }

    public Analisis crear(Analisis analisis) {
        log.info("Creando nuevo análisis para el RUT de usuario: {}", analisis.getRutUsuario());
        
        analisisRepository.findById(analisis.getIdAnalisis()).ifPresent(existingAnalisis -> {
            log.warn("⚠️ Ya existe un análisis con el ID: {}", analisis.getIdAnalisis());
            throw new IllegalArgumentException("El ID del análisis ya está registrado");
        });

        Analisis guardado = analisisRepository.save(analisis);
        log.info("Análisis creado con ID: {}", guardado.getIdAnalisis());
        return guardado;
    }

    public Analisis actualizar(Long id, Analisis analisisActualizado) {
        log.info("Actualizando análisis con ID: {}", id);
        Analisis analisisExistente = buscarPorId(id);

        analisisExistente.setRutUsuario(analisisActualizado.getRutUsuario());
        analisisExistente.setNombreAnalisis(analisisActualizado.getNombreAnalisis());
        analisisExistente.setDetalleAnalisis(analisisActualizado.getDetalleAnalisis());
        analisisExistente.setUnidad(analisisActualizado.getUnidad());      
        analisisExistente.setLaboratorio(analisisActualizado.getLaboratorio());

        Analisis actualizado = analisisRepository.save(analisisExistente);
        log.info("Análisis actualizado con ID: {}", actualizado.getIdAnalisis());
        return actualizado;
    }

    public void eliminar(Long id) {
        log.info("Eliminando análisis con ID: {}", id);
        if (!analisisRepository.existsById(id)) {
            log.warn("❌ No se encontró un análisis con el ID: {}", id);
            throw new ResourceNotFoundException("Análisis no encontrado con ID: " + id);
        }
        analisisRepository.deleteById(id);
        log.info("Análisis eliminado con ID: {}", id);
       
    }    
}
