package com.udb.dwf404.empleadodepartamento.controller;

import com.udb.dwf404.empleadodepartamento.dto.EmpleadoResponseDTO;
import com.udb.dwf404.empleadodepartamento.entity.Empleado;
import com.udb.dwf404.empleadodepartamento.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado,
                                                   @RequestParam Long departamentoId) {
        try {
            Empleado nuevoEmpleado = empleadoService.createEmpleado(empleado, departamentoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        List<EmpleadoResponseDTO> response = empleados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> getEmpleadoById(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.getEmpleadoById(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(convertToDTO(empleado.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id,
                                                   @RequestBody Empleado empleadoDetails) {
        try {
            Empleado empleadoActualizado = empleadoService.updateEmpleado(id, empleadoDetails);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable Long id) {
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }

    private EmpleadoResponseDTO convertToDTO(Empleado empleado) {
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.setId(empleado.getId());
        dto.setNombreCompleto(empleado.getNombreCompleto());
        dto.setCorreo(empleado.getCorreo());
        dto.setCargo(empleado.getCargo());
        dto.setSalario(empleado.getSalario());

        if (empleado.getDepartamento() != null) {
            EmpleadoResponseDTO.DepartamentoInfoDTO deptDto = new EmpleadoResponseDTO.DepartamentoInfoDTO();
            deptDto.setId(empleado.getDepartamento().getId());
            deptDto.setNombre(empleado.getDepartamento().getNombre());
            deptDto.setDescripcion(empleado.getDepartamento().getDescripcion());
            dto.setDepartamento(deptDto);
        }
        return dto;
    }
}