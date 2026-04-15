package com.udb.dwf404.empleadodepartamento.repository;

import com.udb.dwf404.empleadodepartamento.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByNombre(String nombre);
}