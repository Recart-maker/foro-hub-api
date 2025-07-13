# ForoHub API REST

Este proyecto implementa una API REST para un foro de discusión, permitiendo la gestión de tópicos. Está desarrollado con Spring Boot y utiliza Spring Security para la autenticación y JWT para la autorización.

## Tabla de Contenidos

1.  [Descripción General](#descripción-general)
2.  [Tecnologías Utilizadas](#tecnologías-utilizadas)
3.  [Configuración del Entorno](#configuración-del-entorno)
4.  [Base de Datos](#base-de-datos)
    * [Diagrama de Base de Datos](#diagrama-de-base-de-datos)
5.  [Funcionalidades de la API (Endpoints)](#funcionalidades-de-la-api-endpoints)
    * [Autenticación (JWT)](#autenticación-jwt)
    * [Gestión de Tópicos (CRUD)](#gestión-de-tópicos-crud)
    * [Gestión de Respuestas (Opcional)](#gestión-de-respuestas-opcional)
6.  [Documentación (Swagger/OpenAPI)](#documentación-swaggeropenapi)
7.  [Pruebas](#pruebas)
8.  [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
9.  [Contacto](#contacto)

---

## 1. Descripción General

ForoHub es una plataforma de discusión donde los usuarios pueden crear, leer, actualizar y eliminar tópicos (temas de conversación). Esta API RESTful es el backend que soporta las operaciones principales del foro, incluyendo la gestión de usuarios, cursos y, fundamentalmente, los tópicos. El proyecto incorpora buenas prácticas de desarrollo con Spring Boot, seguridad robusta mediante Spring Security y JWT, y una base de datos MySQL para la persistencia de datos.

## 2. Tecnologías Utilizadas

* **Java JDK 17+**
* **Spring Boot 3+**
* **Maven 4+**
* **MySQL 8+**
* **Spring Data JPA**: Para la interacción con la base de datos.
* **Spring Security**: Para la autenticación y autorización de usuarios.
* **JWT (JSON Web Token)**: Para la generación y validación de tokens de seguridad.
* **Flyway Migration**: Para el control de versiones de la base de datos (migraciones).
* **Lombok**: Para reducir el código boilerplate.
* **Validation**: Para la validación de los datos de entrada.
* **SpringDoc OpenAPI (o SpringFox Swagger)**: Para la documentación automática de la API (opcional).

## 3. Configuración del Entorno

Asegúrate de tener instalados los siguientes componentes:
* Java JDK 17 o superior.
* Maven 4 o superior.
* MySQL 8 o superior.
* Un IDE como IntelliJ IDEA (opcional).

Las dependencias del proyecto se gestionan con Maven y están especificadas en el archivo `pom.xml`.

## 4. Base de Datos

El proyecto utiliza MySQL para la persistencia de los datos. La estructura de la base de datos se maneja mediante migraciones de Flyway.

### Diagrama de Base de Datos

Aquí se presenta el diagrama de entidad-relación que sirve como base para la estructura de la base de datos de ForoHub. Este diagrama muestra las entidades principales como `Tópico`, `Usuario`, `Curso`, `Respuesta` y `Perfil`, y sus relaciones.

**Nota:** Aunque el diagrama es más completo, el enfoque principal del desafío está en la entidad `Tópico`.

![Diagrama de Base de Datos - ForoHub](docs/diagrama_base_de_datos_forohub.png)

*(Asegúrate de que la ruta `docs/diagrama_base_de_datos_forohub.png` sea correcta y que la imagen `diagrama_base_de_datos_forohub.png` esté en la carpeta `docs` dentro de tu repositorio)*

## 5. Funcionalidades de la API (Endpoints)

La API proporciona varios endpoints para interactuar con los recursos del foro. Todas las solicitudes que interactúan con los tópicos y otras rutas protegidas requieren autenticación con un token JWT.

### Autenticación (JWT)

* **`POST /login`**: Permite a los usuarios autenticarse con su nombre de usuario y contraseña para obtener un token JWT. Este token debe ser incluido en las cabeceras (`Authorization: Bearer <token>`) de las solicitudes posteriores para acceder a las rutas protegidas.

### Gestión de Tópicos (CRUD)

* **`POST /topicos`**: Registra un nuevo tópico en el foro. Requiere un `titulo`, `mensaje`, `cursoId` y `autorId` en el cuerpo de la solicitud JSON. Se valida que el título y mensaje no estén duplicados y que los IDs de curso y autor existan.
* **`GET /topicos`**: Muestra un listado de todos los tópicos registrados. Soporta paginación (por defecto 10 resultados, ordenados por `fechaCreacion`).
* **`GET /topicos/{id}`**: Muestra los detalles de un tópico específico identificado por su `id`. Devuelve un `404 Not Found` si el tópico no existe.
* **`PUT /topicos/{id}`**: Actualiza la información de un tópico existente. Se pueden modificar el `titulo`, `mensaje` y `status`. Requiere el `id` del tópico en la URL.
* **`DELETE /topicos/{id}`**: Elimina un tópico del foro. Requiere el `id` del tópico en la URL. Devuelve `204 No Content` si la eliminación es exitosa o `404 Not Found` si el tópico no existe.

### Gestión de Respuestas (Opcional - Tarea Pendiente)

* (Aquí iría la documentación para los endpoints de `/respuestas` una vez que los implementes, incluyendo POST, GET, PUT, DELETE, similar a los de tópicos).

## 6. Documentación (Swagger/OpenAPI)

La API está documentada utilizando SpringDoc OpenAPI (o SpringFox Swagger). Puedes acceder a la interfaz de documentación interactiva en tu navegador una vez que la aplicación esté en ejecución:

* **URL de Swagger UI:** `http://localhost:8080/swagger-ui/index.html` (o `http://localhost:8080/swagger-ui.html` para SpringFox).

## 7. Pruebas

Se recomienda utilizar herramientas como **Insomnia** o **Postman** para probar los endpoints de la API. Durante el desarrollo, se realizaron extensas pruebas manuales para asegurar la correcta funcionalidad del CRUD de tópicos y la autenticación.

## 8. Cómo Ejecutar el Proyecto

1.  **Clonar el Repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/nombre-del-repositorio.git](https://github.com/tu-usuario/nombre-del-repositorio.git)
    cd nombre-del-repositorio
    ```
2.  **Configurar la Base de Datos:**
    * Crea una base de datos MySQL (por ejemplo, `forohub_db`).
    * Configura las credenciales de la base de datos en `src/main/resources/application.properties` (o `application.yml`). Asegúrate de que los campos `spring.datasource.url`, `spring.datasource.username` y `spring.datasource.password` sean correctos.
    * Flyway se encargará de ejecutar las migraciones SQL (`src/main/resources/db/migration`) para crear las tablas necesarias al iniciar la aplicación.
3.  **Compilar y Ejecutar:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    La aplicación se iniciará en `http://localhost:8080`.

## 9. Contacto

Si tienes alguna pregunta o sugerencia, no dudes en contactarme.

---