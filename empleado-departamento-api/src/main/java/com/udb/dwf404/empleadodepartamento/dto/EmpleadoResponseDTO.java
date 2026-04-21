package com.udb.dwf404.empleadodepartamento.dto;

import lombok.Data;

@Data
public class EmpleadoResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String correo;
    private String cargo;
    private Double salario;
    private DepartamentoInfoDTO departamento;

    @Data
    public static class DepartamentoInfoDTO {
        private Long id;
        private String nombre;
        private String descripcion;
    }
}