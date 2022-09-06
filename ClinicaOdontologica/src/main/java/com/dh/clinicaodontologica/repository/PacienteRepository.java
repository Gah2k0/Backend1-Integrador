package com.dh.clinicaodontologica.repository;

import com.dh.clinicaodontologica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}