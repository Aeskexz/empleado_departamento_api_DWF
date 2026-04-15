package com.udb.dwf404.empleadodepartamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "departamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados;
}