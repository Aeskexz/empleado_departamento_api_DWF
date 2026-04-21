package com.udb.dwf404.empleadodepartamento.service;

import com.udb.dwf404.empleadodepartamento.entity.Departamento;
import com.udb.dwf404.empleadodepartamento.entity.Empleado;
import com.udb.dwf404.empleadodepartamento.repository.DepartamentoRepository;
import com.udb.dwf404.empleadodepartamento.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Empleado createEmpleado(Empleado empleado, Long departamentoId) {
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + departamentoId));

        empleado.setDepartamento(departamento);
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado updateEmpleado(Long id, Empleado empleadoDetails) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        empleado.setNombreCompleto(empleadoDetails.getNombreCompleto());
        empleado.setCorreo(empleadoDetails.getCorreo());
        empleado.setCargo(empleadoDetails.getCargo());
        empleado.setSalario(empleadoDetails.getSalario());

        if (empleadoDetails.getDepartamento() != null && empleadoDetails.getDepartamento().getId() != null) {
            Departamento newDept = departamentoRepository.findById(empleadoDetails.getDepartamento().getId())
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            empleado.setDepartamento(newDept);
        }

        return empleadoRepository.save(empleado);
    }

    @Transactional
    public void deleteEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        Departamento dept = empleado.getDepartamento();
        empleadoRepository.delete(empleado);

        if (dept != null && dept.getEmpleados().isEmpty()) {
            departamentoRepository.delete(dept);
        }
    }
}