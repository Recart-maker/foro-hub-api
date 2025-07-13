package com.alurachallenge.foro_hub_api.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;

    // Constructor vacío requerido por JPA
    public Curso() {}

    // Constructor con nombre y categoría
    public Curso(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    // ¡ESTE ES EL CONSTRUCTOR CORREGIDO!
    // Ahora asigna el 'curso' que recibe al campo 'nombre'
    public Curso(@NotBlank String nombre) { // Cambié el parámetro a 'nombre' para mayor claridad
        this.nombre = nombre;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}