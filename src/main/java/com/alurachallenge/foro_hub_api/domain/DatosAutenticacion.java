package com.alurachallenge.foro_hub_api.domain; // O el paquete donde decidas poner los DTOs

// Usaremos records para DTOs en Java 17+, si tu versión es anterior, usa una clase normal.
public record DatosAutenticacion(String email, String contrasena) {
}

/*
// Si usas una versión de Java anterior a la 17 o prefieres clases normales:
public class DatosAutenticacion {
    private String email;
    private String contrasena;

    // Constructores
    public DatosAutenticacion() {}

    public DatosAutenticacion(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
*/