package com.alurachallenge.foro_hub_api.controladores; // Revisa si tu paquete es 'controladores' o 'controller'

import com.alurachallenge.foro_hub_api.modelos.StatusTopico;
import com.alurachallenge.foro_hub_api.modelos.Curso;
import com.alurachallenge.foro_hub_api.modelos.Topico;
import com.alurachallenge.foro_hub_api.modelos.Usuario;
import com.alurachallenge.foro_hub_api.records.DatosListadoTopico;
import com.alurachallenge.foro_hub_api.records.DatosRegistroTopico;
import com.alurachallenge.foro_hub_api.records.DatosActualizacionTopico;
import com.alurachallenge.foro_hub_api.records.DatosDetalleTopico; // <--- ¡ASEGÚRATE DE IMPORTAR ESTE RECORD!
import com.alurachallenge.foro_hub_api.repositorios.ICursoRepositorio;
import com.alurachallenge.foro_hub_api.repositorios.ITopicoRepositorio;
import com.alurachallenge.foro_hub_api.repositorios.IUsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private ITopicoRepositorio topicoRepository;
    @Autowired // Necesario para buscar el curso al registrar un tópico
    private ICursoRepositorio cursoRepository;
    @Autowired // Necesario para buscar el autor (usuario) al registrar un tópico
    private IUsuarioRepositorio usuarioRepository;


    // MÉTODO PARA REGISTRAR UN NUEVO TÓPICO (POST)
    @PostMapping
    public ResponseEntity<DatosDetalleTopico> registrarTopico( // <-- Cambiado el tipo de retorno a DatosDetalleTopico
                                                               @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                               UriComponentsBuilder uriComponentsBuilder
    ) {
        // Validación de existencia de curso y usuario
        Optional<Curso> cursoOptional = cursoRepository.findById(datosRegistroTopico.cursoId());
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Curso curso = cursoOptional.get();

        Optional<Usuario> autorOptional = usuarioRepository.findById(datosRegistroTopico.autorId());
        if (autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Usuario autor = autorOptional.get();

        // Crear el tópico
        Topico topico = new Topico(
                null, // ID se genera automáticamente por la base de datos
                datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje(),
                LocalDateTime.now(), // Fecha de creación actual
                StatusTopico.NO_RESPONDIDO, // Estado inicial por defecto
                autor,
                curso
        );

        // Guardar el tópico en la base de datos
        topicoRepository.save(topico);

        // Construir la URI para el nuevo tópico creado (según buenas prácticas REST)
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosDetalleTopico(topico)); // <-- Cambiado a DatosDetalleTopico
    }


    // MÉTODO PARA LISTAR TODOS LOS TÓPICOS (GET)
    // Se añade paginación para mejorar el rendimiento y manejo de grandes volúmenes de datos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            // @PageableDefault permite configurar el tamaño y ordenación por defecto
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion
    ) {
        Page<DatosListadoTopico> pagina = topicoRepository.findAll(paginacion)
                .map(DatosListadoTopico::new);
        return ResponseEntity.ok(pagina);
    }


    // MÉTODO PARA DETALLAR UN TÓPICO POR ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detallarTopico(@PathVariable Long id) { // <-- Cambiado el tipo de retorno
        // Busca el tópico por ID
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            // Si el tópico no se encuentra, devuelve un 404 Not Found
            return ResponseEntity.notFound().build();
        }
        // Si el tópico existe, devuelve un 200 OK con los datos
        return ResponseEntity.ok(new DatosDetalleTopico(topicoOptional.get())); // <-- Cambiado a DatosDetalleTopico
    }


    // --- ¡MÉTODO ACTUALIZAR TÓPICO (PUT) CORREGIDO Y CENTRALIZADO AQUÍ! ---
    // ESTE ES EL MÉTODO QUE DEBES TENER PARA LA ACTUALIZACIÓN
    @PutMapping("/{id}") // <-- Ahora usa @PathVariable para el ID
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable Long id,
                                                               @RequestBody @Valid DatosActualizacionTopico datosActualizacionTopico) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 si el tópico no existe
        }

        Topico topico = topicoOptional.get();

        // Actualiza los campos solo si no son nulos o vacíos en el DTO de actualización
        if (datosActualizacionTopico.titulo() != null && !datosActualizacionTopico.titulo().isBlank()) {
            topico.setTitulo(datosActualizacionTopico.titulo());
        }
        if (datosActualizacionTopico.mensaje() != null && !datosActualizacionTopico.mensaje().isBlank()) {
            topico.setMensaje(datosActualizacionTopico.mensaje());
        }
        if (datosActualizacionTopico.status() != null) {
            topico.setStatus(datosActualizacionTopico.status());
        }

        topicoRepository.save(topico); // Guarda los cambios en la base de datos

        // Devuelve una respuesta 200 OK con los datos detallados del tópico actualizado
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }


    // ENDPOINT PARA ELIMINAR UN TÓPICO (DELETE /{id}) - Este método se mantiene igual
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 si el tópico no existe
        }
    }
}