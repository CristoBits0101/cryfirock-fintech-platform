# 🤖 AGENTS.md - Guía de IA para Generación de Código

> Instrucciones para agentes de IA (GitHub Copilot, Claude, ChatGPT, etc.) sobre estándares de código y estructura del proyecto CryfiRock Fintech Platform.

---

## 📋 TABLA DE CONTENIDOS

- [Reglas de Formato de Código](#-reglas-de-formato-de-código)
- [Estilo de Comentarios](#-estilo-de-comentarios)
- [JavaDoc](#-javadoc)
- [Estructura de Clases](#-estructura-de-clases)
- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Patrones de Creación de Archivos](#-patrones-de-creación-de-archivos)

---

## 📐 REGLAS DE FORMATO DE CÓDIGO

### Indentación y Espaciado

| Regla | Valor |
|-------|-------|
| Indentación | **4 espacios** (NO tabs) |
| Máximo caracteres por línea | **100 caracteres** |
| Líneas en blanco entre métodos | **1 línea** |
| Espacio después de cierre de clase | **1 línea en blanco** |

### Estructura de Clases

```java
public class MiClase {
    /**
     * Comentario del primer atributo.
     */
    private final String atributo;

    // ... resto del código.

    /**
     * Último método de la clase.
     */
    public void ultimoMetodo() {
        // implementación.
    }
}
// ← Línea en blanco obligatoria después del cierre de la clase.
```

**Regla crítica:** La primera línea de código dentro de una clase debe ir **pegada** a la llave de apertura:

```java
// ✅ CORRECTO.
public class GlobalExceptionHandler {
    /**
     * Fuente de mensajes para la internacionalización.
     */
    private final MessageSource messageSource;

// ❌ INCORRECTO.
public class GlobalExceptionHandler {

    /**
     * Fuente de mensajes para la internacionalización.
     */
    private final MessageSource messageSource;
```

---

## 💬 ESTILO DE COMENTARIOS

### Reglas Generales

| # | Regla |
|:-:|-------|
| 1 | Cada comentario debe caber en **una sola línea** (sin wrap). |
| 2 | No superar el **máximo de 100 caracteres** por línea. |
| 3 | Todos los comentarios deben **terminar en punto (.)** |
| 4 | Los comentarios de línea simple deben terminar en punto final. |
| 5 | Usar comentarios numerados para explicaciones múltiples. |
| 6 | **Todos los comentarios deben estar en español.** |
| 7 | Si el comentario tiene **solo una línea**, usar formato simple `//`. |

### Tipos de Comentarios

```java
// Comentario de línea simple. ← Termina en punto.

/*
 * Comentario de bloque para explicaciones extensas.
 * Segunda línea del bloque.
 */

/**
 * Comentario JavaDoc multilínea para documentación pública.
 */
```

### Comentarios Simples de Una Línea

Cuando el comentario tiene una única línea de descripción, usar formato simple `//`:

```java
// ✅ CORRECTO - Comentario simple de una línea.
public class MiClase {
    // Mensaje descriptivo del error.
    private String message;

    // Código de estado HTTP asociado al error.
    private int status;
}

// ❌ INCORRECTO - No usar JavaDoc para una sola línea.
public class MiClase {
    /**
     * Mensaje descriptivo del error.
     */
    private String message;
}
```

### Comentarios Numerados (Estilo del Proyecto)

```java
/**
 * 1. Primera explicación del componente.
 * 2. Segunda explicación del comportamiento.
 * 3. Tercera explicación de dependencias.
 */
@Component
public class MiComponente {
    /**
     * 1. Descripción del atributo.
     * 2. Propósito del atributo.
     */
    private final MiDependencia dependencia;
}
```

---

## 📖 JAVADOC

### Estructura de JavaDoc para Clases

```java
package com.cryfirock.auth.service;

import java.util.List;

/**
 * 1. Descripción principal del servicio.
 * 2. Responsabilidades del componente.
 * 3. Dependencias principales.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class MiServicio {
    // implementación.
}
```

### JavaDoc para Métodos

```java
/**
 * 1. Descripción del propósito del método.
 * 2. Comportamiento esperado.
 * 3. Casos especiales o excepciones.
 *
 * {@code resultado = miMetodo(parametro);}
 *
 * @param parametro Descripción del parámetro.
 * @return Descripción del valor retornado.
 * @throws MiExcepcion Cuando ocurre un error específico.
 * @see #otroMetodo()
 */
public String miMetodo(String parametro) {
    return resultado;
}
```

### Regla de Fecha para @since

- El valor de `@since` debe usar la fecha del día actual (YYYY-MM-DD) al crear o modificar la clase. **IMPORTANTE:** No copiar la fecha de los ejemplos; generar siempre la fecha del día.

### JavaDoc para Atributos

```java
/**
 * 1. Repositorio JPA para usuarios.
 * 2. Proporciona operaciones CRUD.
 */
private final JpaUserRepository userRepository;
```

### Tags JavaDoc Comunes

| Tag | Uso |
|-----|-----|
| `@param` | Describe un parámetro del método. |
| `@return` | Describe el valor de retorno. |
| `@throws` | Documenta excepciones lanzadas. |
| `@see` | Referencia a otra clase o método. |
| `@since` | Versión desde la que existe. |
| `@version` | Versión actual del componente. |
| `@author` | Autor del código. |
| `@deprecated` | Marca código obsoleto. |
| `{@code x}` | Código inline en documentación. |
| `{@link #m}` | Link a otro método/clase. |
| `{@literal x}` | Texto literal sin formato. |

---

## 🏗️ ARQUITECTURA DEL PROYECTO

### Estructura de Paquetes (Módulo Auth)

```
com.cryfirock.auth/
├── advice/              # Manejo global de excepciones
├── aspect/              # Aspectos AOP (logging, auditoría)
├── config/              # Configuración de Spring MVC
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects (records)
├── entity/              # Entidades JPA
├── exception/           # Excepciones personalizadas
├── helper/              # Clases auxiliares de negocio
├── interceptor/         # Interceptores HTTP
├── mapper/              # MapStruct mappers
├── model/               # Modelos no persistentes (enums, POJOs)
├── repository/          # Repositorios JPA
├── security/
│   ├── config/          # Configuración Spring Security
│   ├── filter/          # Filtros JWT
│   ├── handler/         # Manejadores de errores 401/403
│   └── jackson/         # Configuración serialización
├── service/
│   ├── api/             # Interfaces de servicios
│   └── impl/            # Implementaciones de servicios
├── util/                # Utilidades estáticas
└── validation/
    ├── api/             # Anotaciones de validación
    └── impl/            # Implementaciones de validadores
```

### Convenciones de Nombrado

| Tipo | Patrón | Ejemplo |
|------|--------|---------|
| Interfaz de servicio | `I{Nombre}Service` | `IUserService` |
| Implementación | `{Nombre}Impl` | `UserServiceImpl` |
| Repositorio JPA | `Jpa{Entidad}Repository` | `JpaUserRepository` |
| DTO | `{Entidad}{Acción}Dto` | `UserUpdateDto` |
| Excepción | `{Entidad}NotFoundException` | `UserNotFoundException` |
| Validación interfaz | `IExistsBy{Campo}` | `IExistsByEmail` |
| Validación impl | `ExistsBy{Campo}ValidationImpl` | `ExistsByEmailValidationImpl` |
| Filtro JWT | `Jwt{Acción}Filter` | `JwtAuthenticationFilter` |
| Handler REST | `Rest{Tipo}Handler` | `RestAccessDeniedHandler` |

---

## 🛡️ MANEJO DE ERRORES

Los errores deben ser capturados y transformados en respuestas HTTP adecuadas utilizando `@RestControllerAdvice`.

### Flujo de Captura de Errores

| Escenario | Causa | Flujo de Excepción | Respuesta HTTP |
|-----------|-------|--------------------|:--------------:|
| **Tipo de dato incorrecto en URL**<br>Ej: `GET /users/abc` (id espera Long) | El cliente envía un tipo de dato que no coincide con el parámetro del controlador. | `Controller` → Spring lanza `TypeMismatchException` → `Advice` captura | **400 Bad Request** |
| **Recurso no encontrado**<br>Ej: `GET /users/10` (id no existe) | El recurso solicitado no existe en la base de datos. | `Service` lanza `NotFoundException` → `Advice` captura | **404 Not Found** |
| **Validación de DTO fallida**<br>Ej: `POST /users` (campos inválidos) | Los datos del cuerpo de la solicitud no cumplen con las anotaciones `@Valid` / `@NotNull`. | Spring (`@Valid`) detecta error → Lanza `MethodArgumentNotValidException` → `Advice` captura | **400 Bad Request** |

---

## 📝 PATRONES DE CREACIÓN DE ARCHIVOS

### 1. Crear un Nuevo Servicio

```java
package com.cryfirock.auth.service.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.auth.service.contract.INuevoService;

/**
 * 1. Implementación del servicio para operaciones de X.
 * 2. Maneja transacciones y lógica de negocio.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 */
@Service
public class NuevoServiceImpl implements INuevoService {
    /**
     * Repositorio para acceso a datos.
     */
    private final JpaEntidadRepository repository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param repository Repositorio de la entidad.
     */
    public NuevoServiceImpl(JpaEntidadRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Entidad guardar(Entidad entidad) {
        return repository.save(entidad);
    }
}
```

### 2. Crear un Nuevo Controlador

```java
package com.cryfirock.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.auth.service.contract.INuevoService;

/**
 * 1. Controlador REST para operaciones de X.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/nuevo.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 */
@RestController
@CrossOrigin
@RequestMapping("/api/nuevo")
public class NuevoController {
    /**
     * Servicio para operaciones de negocio.
     */
    private final INuevoService nuevoService;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param nuevoService Servicio de la entidad.
     */
    public NuevoController(INuevoService nuevoService) {
        this.nuevoService = nuevoService;
    }

    /**
     * 1. Obtiene todos los recursos.
     * 2. Mapea las solicitudes GET a la raíz.
     *
     * @return Lista de recursos.
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return ResponseEntity.ok(nuevoService.findAll());
    }
}
```

### 3. Crear una Nueva Entidad

```java
package com.cryfirock.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Entidad JPA que representa la tabla X.
 * 2. Mapea los campos a columnas de la base de datos.
 * 3. Usa Lombok para getters, setters y constructores.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 */
@Entity
@Table(name = "mi_tabla")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MiEntidad {
    /**
     * 1. Identificador único de la entidad.
     * 2. Generación automática por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Campo de ejemplo.
     */
    private String nombre;
}
```

### 4. Crear un Test Unitario

```java
package com.cryfirock.auth.service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 1. Pruebas unitarias para NuevoServiceImpl.
 * 2. Verifica el correcto funcionamiento del servicio.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unused")
class NuevoServiceImplTest {
    @Mock
    private JpaEntidadRepository repository;

    @InjectMocks
    private NuevoServiceImpl service;

    @Nested
    @DisplayName("Tests para método X")
    class MetodoXTests {

        @Test
        @DisplayName("Debe retornar resultado esperado")
        void shouldReturnExpectedResult() {
            // Arrange.
            when(repository.findAll()).thenReturn(List.of());

            // Act.
            var result = service.findAll();

            // Assert.
            assertNotNull(result);
        }
    }
}
```

---

## ⚡ COMANDOS ÚTILES

```bash
# Formatear código automáticamente.
./mvnw spotless:apply

# Verificar formato sin modificar.
./mvnw spotless:check

# Ejecutar tests.
./mvnw test

# Compilar proyecto.
./mvnw clean compile

# Ejecutar aplicación.
./mvnw spring-boot:run
```

---

## 🔍 CHECKLIST DE REVISIÓN DE CÓDIGO

- [ ] Indentación de 4 espacios.
- [ ] Comentarios terminan en punto.
- [ ] Comentarios no exceden 100 caracteres.
- [ ] Primera línea de clase pegada a la llave.
- [ ] Línea en blanco después del cierre de clase.
- [ ] JavaDoc en clases públicas con `@author`, `@version`, `@since`.
- [ ] Métodos públicos documentados con `@param` y `@return`.
- [ ] Nombres siguen las convenciones del proyecto.
- [ ] Tests con estructura `@Nested` y `@DisplayName`.

---

<p align="center">
  Documentación para agentes de IA - <strong>CryfiRock Fintech Platform</strong>
</p>
