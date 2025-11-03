package com.example.actsum.analisis.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.actsum.analisis.model.Analisis;

@Repository
public interface AnalisisRepository  extends JpaRepository<Analisis, Long> {    
    List<Analisis> findByRutUsuario(String rutUsuario);
    List<Analisis> findByLaboratorioIgnoreCase(String laboratorio);
}
