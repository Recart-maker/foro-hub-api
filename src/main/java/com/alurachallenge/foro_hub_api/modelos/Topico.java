package com.alurachallenge.foro_hub_api.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Se importa pero no se usa directamente en esta clase para @NotBlank

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos") // Nombre de la tabla en la base de datos
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion; // Eliminamos la inicialización aquí, la manejaremos en los constructores

    @Enumerated(EnumType.STRING)
    private StatusTopico status; // Eliminamos la inicialización aquí, la manejaremos en los constructores

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // Constructores

    // Constructor vacío (necesario para JPA y frameworks como Spring)
    public Topico() {}

    // Constructor para la creación de un nuevo tópico (el que se usará desde el controlador)
    // Este constructor coincide con la llamada de `new Topico(...)` en TopicoController
    public Topico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, Usuario autor, Curso curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion; // Asigna la fecha pasada
        this.status = status; // Asigna el estado pasado
        this.autor = autor;
        this.curso = curso;
    }

    // Opcional: Constructor más simple si lo necesitas para otras lógicas (inicializa valores por defecto)
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now(); // Por defecto al crear
        this.status = StatusTopico.NO_RESPONDIDO; // Por defecto al crear
        this.autor = autor;
        this.curso = curso;
    }


    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public StatusTopico getStatus() { return status; }
    public Usuario getAutor() { return autor; }
    public Curso getCurso() { return curso; }

    // Setters (solo los que probablemente necesites para actualizaciones o asignaciones)
    // No se necesita setId() si es generado por la DB
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setStatus(StatusTopico status) { this.status = status; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public void setCurso(Curso curso) { this.curso = curso; }

    // Puedes añadir hashCode, equals, toString si los necesitas
}