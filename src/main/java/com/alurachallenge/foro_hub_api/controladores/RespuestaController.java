package com.alurachallenge.foro_hub_api.controladores;

import com.alurachallenge.foro_hub_api.modelos.Respuesta;
import com.alurachallenge.foro_hub_api.modelos.Topico;
import com.alurachallenge.foro_hub_api.modelos.Usuario;
import com.alurachallenge.foro_hub_api.records.DatosActualizacionRespuesta;
import com.alurachallenge.foro_hub_api.records.DatosDetalleRespuesta;
import com.alurachallenge.foro_hub_api.records.DatosListadoRespuesta;
import com.alurachallenge.foro_hub_api.records.DatosRegistroRespuesta;
import com.alurachallenge.foro_hub_api.repositorios.IRespuestaRepositorio;
import com.alurachallenge.foro_hub_api.repositorios.ITopicoRepositorio;
import com.alurachallenge.foro_hub_api.repositorios.IUsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private IRespuestaRepositorio respuestaRepository;
    @Autowired
    private ITopicoRepositorio topicoRepository; // Necesario para buscar el tópico
    @Autowired
    private IUsuarioRepositorio usuarioRepository; // Necesario para buscar el autor

    // MÉTODO PARA REGISTRAR UNA NUEVA RESPUESTA (POST)
    @PostMapping
    public ResponseEntity<DatosDetalleRespuesta> registrarRespuesta(
            @RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        // Validación de existencia de tópico
        Optional<Topico> topicoOptional = topicoRepository.findById(datosRegistroRespuesta.topicoId());
        if (topicoOptional.isEmpty()) {
            // Puedes lanzar una excepción personalizada para un manejo de errores más específico
            return ResponseEntity.badRequest().body(null); // O un mensaje de error más específico
        }
        Topico topico = topicoOptional.get();

        // Validación de existencia de autor
        Optional<Usuario> autorOptional = usuarioRepository.findById(datosRegistroRespuesta.autorId());
        if (autorOptional.isEmpty()) {
            // Puedes lanzar una excepción personalizada para un manejo de errores más específico
            return ResponseEntity.badRequest().body(null); // O un mensaje de error más específico
        }
        Usuario autor = autorOptional.get();

        Respuesta respuesta = new Respuesta(
                datosRegistroRespuesta.mensaje(),
                topico,
                autor
        );

        respuestaRepository.save(respuesta);

        // Construir la URL de la nueva respuesta creada para la cabecera Location
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosDetalleRespuesta(respuesta));
    }

    // MÉTODO PARA LISTAR TODAS LAS RESPUESTAS (GET)
    // Permite paginación: /respuestas?page=0&size=10&sort=fechaCreacion,desc
    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listarRespuestas(
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion
    ) {
        // Convierte la página de entidades Respuesta a una página de DTOs DatosListadoRespuesta
        Page<DatosListadoRespuesta> pagina = respuestaRepository.findAll(paginacion)
                .map(DatosListadoRespuesta::new);
        return ResponseEntity.ok(pagina);
    }

    // MÉTODO PARA DETALLAR UNA RESPUESTA POR ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> detallarRespuesta(@PathVariable Long id) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(id);

        if (respuestaOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si la respuesta no existe
        }
        // Si la respuesta existe, la devuelve mapeada al DTO de detalle
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuestaOptional.get()));
    }

    // MÉTODO PARA ACTUALIZAR UNA RESPUESTA (PUT /{id})
    @PutMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> actualizarRespuesta(@PathVariable Long id,
                                                                     @RequestBody @Valid DatosActualizacionRespuesta datosActualizacionRespuesta) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(id);

        if (respuestaOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si la respuesta no existe
        }

        Respuesta respuesta = respuestaOptional.get();

        // Actualiza solo los campos que vienen en la solicitud (no nulos o en blanco)
        if (datosActualizacionRespuesta.mensaje() != null && !datosActualizacionRespuesta.mensaje().isBlank()) {
            respuesta.setMensaje(datosActualizacionRespuesta.mensaje());
        }
        if (datosActualizacionRespuesta.solucion() != null) {
            respuesta.setSolucion(datosActualizacionRespuesta.solucion());
        }

        // Guarda los cambios en la base de datos
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    // MÉTODO PARA ELIMINAR UNA RESPUESTA (DELETE /{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(id);

        if (respuestaOptional.isPresent()) {
            respuestaRepository.deleteById(id); // Elimina la respuesta por su ID
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content para una eliminación exitosa
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si la respuesta no existe
        }
    }
}