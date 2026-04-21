package com.udb.dwf404.empleadodepartamento.controller;

import com.udb.dwf404.empleadodepartamento.entity.Departamento;
import com.udb.dwf404.empleadodepartamento.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departamentos")
@CrossOrigin(origins = "*")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) {
        try {
            Departamento nuevoDepartamento = departamentoService.createDepartamento(departamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDepartamento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Departamento>> getAllDepartamentos() {
        List<Departamento> departamentos = departamentoService.getAllDepartamentos();
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Long id) {
        Optional<Departamento> departamento = departamentoService.getDepartamentoById(id);
        if (departamento.isPresent()) {
            return ResponseEntity.ok(departamento.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable Long id,
                                                           @RequestBody Departamento departamentoDetails) {
        try {
            Departamento departamentoActualizado = departamentoService.updateDepartamento(id, departamentoDetails);
            return ResponseEntity.ok(departamentoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartamento(@PathVariable Long id) {
        try {
            departamentoService.deleteDepartamento(id);
            return ResponseEntity.ok("Departamento eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departamento no encontrado");
        }
    }
}