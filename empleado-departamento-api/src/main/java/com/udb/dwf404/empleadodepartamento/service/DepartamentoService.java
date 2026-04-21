package com.udb.dwf404.empleadodepartamento.service;

import com.udb.dwf404.empleadodepartamento.entity.Departamento;
import com.udb.dwf404.empleadodepartamento.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Departamento createDepartamento(Departamento departamento) {
        if (departamentoRepository.existsByNombre(departamento.getNombre())) {
            throw new RuntimeException("El departamento '" + departamento.getNombre() + "' ya existe");
        }
        return departamentoRepository.save(departamento);
    }

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento updateDepartamento(Long id, Departamento departamentoDetails) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + id));

        if (!departamento.getNombre().equals(departamentoDetails.getNombre()) &&
                departamentoRepository.existsByNombre(departamentoDetails.getNombre())) {
            throw new RuntimeException("El departamento '" + departamentoDetails.getNombre() + "' ya existe");
        }

        departamento.setNombre(departamentoDetails.getNombre());
        departamento.setDescripcion(departamentoDetails.getDescripcion());

        return departamentoRepository.save(departamento);
    }

    public void deleteDepartamento(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + id));
        departamentoRepository.delete(departamento);
    }
}