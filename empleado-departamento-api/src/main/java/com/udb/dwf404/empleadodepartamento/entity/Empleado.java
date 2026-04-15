package com.udb.dwf404.empleadodepartamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private Double salario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;
}