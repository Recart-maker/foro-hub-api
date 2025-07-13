package com.alurachallenge.foro_hub_api.modelos;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // Importa esta interfaz
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Posiblemente necesites esta importación para evitar problemas de serialización en relaciones

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios") // Asegúrate de que el nombre de la tabla sea correcto
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Opcional, pero útil para evitar errores de serialización en relaciones LAZY
public class Usuario implements UserDetails { // ¡AHORA IMPLEMENTA UserDetails!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email; // Opcional, si usas email como login
    private String login; // Campo para el nombre de usuario de autenticación
    private String contrasena; // Campo para la contraseña encriptada

    // Relación con Tópicos
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topico> topicos;

    // Constructores (asegúrate de incluir login y contrasena si los añades)
    public Usuario() {}

    public Usuario(String nombre, String email, String login, String contrasena) {
        this.nombre = nombre;
        this.email = email;
        this.login = login;
        this.contrasena = contrasena; // ¡La contraseña se guardará encriptada!
    }

    // Getters y Setters (asegúrate de tenerlos para login y contrasena)
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }


    // --- MÉTODOS DE LA INTERFAZ UserDetails ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora, solo asignaremos el rol "ROLE_USER" para todos los usuarios.
        // Si necesitas roles más complejos, puedes gestionarlos aquí.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasena; // Devuelve la contraseña encriptada
    }

    @Override
    public String getUsername() {
        return email; // Devuelve el login/nombre de usuario (es el que se usa para autenticar)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indica si la cuenta del usuario no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indica si la cuenta del usuario no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indica si las credenciales (contraseña) no han expirado
    }

    @Override
    public boolean isEnabled() {
        return true; // Indica si el usuario está habilitado
    }

    // Puedes añadir hashCode, equals, toString si los necesitas
}