package com.udb.dwf404.empleadodepartamento.service;

import com.udb.dwf404.empleadodepartamento.entity.Departamento;
import com.udb.dwf404.empleadodepartamento.entity.Empleado;
import com.udb.dwf404.empleadodepartamento.repository.DepartamentoRepository;
import com.udb.dwf404.empleadodepartamento.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    // CREATE: Insertar empleado con departamento
    public Empleado createEmpleado(Empleado empleado, Long departamentoId) {
        Departamento departamento = departamentoRepository.findById(departamentoId)  // ✅ Mayúscula
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + departamentoId));

        empleado.setDepartamento(departamento);
        return empleadoRepository.save(empleado);
    }

    // READ ALL: Obtener todos los empleados
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    // READ ONE: Obtener empleado por ID
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    // UPDATE: Actualizar empleado
    public Empleado updateEmpleado(Long id, Empleado empleadoDetails) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        empleado.setNombreCompleto(empleadoDetails.getNombreCompleto());
        empleado.setCorreo(empleadoDetails.getCorreo());
        empleado.setCargo(empleadoDetails.getCargo());
        empleado.setSalario(empleadoDetails.getSalario());

        // Si se envía un nuevo departamento, actualizarlo
        if (empleadoDetails.getDepartamento() != null && empleadoDetails.getDepartamento().getId() != null) {
            Departamento newDept = departamentoRepository.findById(empleadoDetails.getDepartamento().getId())  // ✅ Mayúscula
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            empleado.setDepartamento(newDept);
        }

        return empleadoRepository.save(empleado);
    }

    // DELETE: Eliminar empleado
    public void deleteEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        empleadoRepository.delete(empleado);
    }
}