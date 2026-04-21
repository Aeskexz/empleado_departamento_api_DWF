package com.udb.dwf404.empleadodepartamento.controller;

import com.udb.dwf404.empleadodepartamento.entity.Empleado;
import com.udb.dwf404.empleadodepartamento.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * POST: Crear un nuevo empleado
     * Formato del body: {"nombreCompleto": "...", "correo": "...", "cargo": "...", "salario": ..., "departamento": {"id": ...}}
     */
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

    /**
     * GET: Obtener todos los empleados
     */
    @GetMapping
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        return ResponseEntity.ok(empleados);
    }

    /**
     * GET: Obtener empleado por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.getEmpleadoById(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(empleado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * PUT: Actualizar empleado
     * Formato del body: {"nombreCompleto": "...", "correo": "...", "cargo": "...", "salario": ..., "departamento": {"id": ...}}
     */
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

    /**
     * DELETE: Borrar empleado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable Long id) {
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }
}
