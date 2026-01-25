<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.9-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
</p>

<h1 align="center">🏦 CryfiRock Fintech Platform</h1>

<p align="center">
  <strong>Plataforma fintech con Spring Boot que ofrece autenticación y autorización mediante JWT</strong>
</p>

---

### 📋 TABLA DE CONTENIDOS

- [Módulo Auth](#-módulo-auth) ⭍
- [Módulo Accounts](#-módulo-accounts) ⭍
- [Módulo OAuth2](#-módulo-oauth2) ⭍
- [Módulo Product](#-módulo-product) ⭍
- [Validaciones y mensajes](#-validaciones-y-mensajes) ⭍
- [Ejecución de Módulos](#-ejecución-de-módulos) ⭍
- [API Endpoints](#-api-endpoints) ⭍

---

### 🔐 MÓDULO AUTH

> Microservicio de Autenticación y Gestión de Usuarios

### ✅ VALIDACIONES Y MENSAJES

> Las utilidades de validación devuelven mensajes no nulos en respuestas de error.

### ⚙️ Configuración Principal

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 1 | 🚀 | • Punto de entrada Spring Boot<br>• Habilita AspectJ (AOP) | `AuthApplication.java` |
| 2 | 📝 | • Propiedades de aplicación<br>• Config BD y servidor | `application.properties` |
| 3 | 🌐 | • Mensajes i18n<br>• Validaciones y errores | `messages.properties` |

##

### 📦 Paquete > `Advice`

> Manejo Centralizado de Excepciones

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 4 | ⚠️ | • Controlador de excepciones global<br>• Maneja:<br>&nbsp;&nbsp;◦ Validación<br>&nbsp;&nbsp;◦ 404<br>&nbsp;&nbsp;◦ Usuarios<br>&nbsp;&nbsp;◦ JSON<br>• Usa `MessageSource` (i18n) | `GlobalExceptionHandler.java` |

💡 **Advice:** Componente de Spring AOP que captura y maneja excepciones de forma centralizada.

##

### 📦 Paquete > `Aspect`

> Programación Orientada a Aspectos (AOP)

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 5 | 📊 | • Intercepta métodos de `UserServiceImpl`<br>• Advices:<br>&nbsp;&nbsp;◦ `@Before`<br>&nbsp;&nbsp;◦ `@After`<br>&nbsp;&nbsp;◦ `@Around`<br>• Logging de operaciones | `UserAspect.java` |

💡 **Aspect:** Clase AOP que intercepta métodos para lógica transversal de logging, seguridad y transacciones.

##

### 📦 Paquete > `Config`

> Configuración de Spring MVC

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 6 | 🔧 | • Config Spring MVC<br>• Interceptor `/api/users/**`<br>• `MessageSource` (i18n) | `AppConfig.java` |

💡 **Config:** Clases de configuración de Spring que definen beans, interceptores y ajustes del framework.

##

### 📦 Paquete > `Controller`

> Controladores REST de la API

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 7 | 🎮 | • REST para gestión de usuarios<br>• CRUD endpoints<br>• `@PreAuthorize` (roles) | `UserController.java` |
| 8 | 🔍 | • REST para validaciones<br>• Verificar existencia<br>• Email, username, teléfono | `UserValidationController.java` |

💡 **Controller:** Componente que recibe peticiones HTTP, las procesa y devuelve respuestas al cliente.

##

### 📦 Paquete > `DTO`

> Objetos de Transferencia de Datos

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 9 | 🔑 | • Record credenciales login<br>• Campos validados:<br>&nbsp;&nbsp;◦ `username`<br>&nbsp;&nbsp;◦ `password` | `UserLoginDto.java` |
| 10 | ✏️ | • Record actualización parcial<br>• Campos:<br>&nbsp;&nbsp;◦ Nombre<br>&nbsp;&nbsp;◦ Email<br>&nbsp;&nbsp;◦ Teléfono<br>&nbsp;&nbsp;◦ Etc... | `UserUpdateDto.java` |

💡 **DTO:** Objeto que transporta datos entre capas sin lógica de negocio usado para entrada/salida de la API.

##

### 📦 Paquete > `Entity`

> Entidades JPA Persistentes

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 11 | 👤 | • Entidad principal usuario<br>• Contiene:<br>&nbsp;&nbsp;◦ Datos<br>&nbsp;&nbsp;◦ Credenciales<br>&nbsp;&nbsp;◦ Estado<br>• Relación con roles | `User.java` |
| 12 | 🎭 | • Entidad rol<br>• Relación bidireccional<br>• Roles:<br>&nbsp;&nbsp;◦ `ROLE_USER`<br>&nbsp;&nbsp;◦ `ROLE_ADMIN` | `Role.java` |
| 13 | 📅 | • Clase embebible auditoría<br>• Fechas:<br>&nbsp;&nbsp;◦ Creación<br>&nbsp;&nbsp;◦ Update<br>&nbsp;&nbsp;◦ Login | `Audit.java` |

💡 **Entity:** Clase que representa una tabla de la base de datos y es gestionada por JPA/Hibernate.

##

### 📦 Paquete > `Exception`

> Excepciones Personalizadas

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 14 | ❌ | • Usuario no encontrado en BD | `UserNotFoundException.java` |

💡 **Exception:** Clases que representan errores específicos del dominio para un manejo de errores más preciso.

##

### 📦 Paquete > `Helper`

> Clases Auxiliares de Lógica de Negocio

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 15 | 🎯 | • Asigna roles a usuarios<br>• `ROLE_USER` (todos)<br>• `ROLE_ADMIN` (flag admin) | `RolesHelper.java` |

💡 **Helper:** Clases auxiliares con métodos utilitarios que encapsulan lógica de negocio reutilizable.

##

### 📦 Paquete > `Interceptor`

> Interceptores HTTP

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 16 | 🛡️ | • Valida tokens JWT<br>• Verifica:<br>&nbsp;&nbsp;◦ Header<br>&nbsp;&nbsp;◦ Firma<br>&nbsp;&nbsp;◦ Expiración | `UserOperationsInterceptor.java` |

💡 **Interceptor:** Componente que intercepta peticiones HTTP antes/después de llegar al controlador.

##

### 📦 Paquete > `Mapper`

> Mapeadores de Objetos (MapStruct)

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 17 | 🔄 | • Convierte params → `ErrorResponse`<br>• Fecha automática | `ErrorMapper.java` |
| 18 | 🔄 | • Actualización parcial usuarios<br>• Ignora campos sensibles | `UserMapper.java` |

💡 **Mapper:** Interfaces que convierten automáticamente entre DTOs y Entities usando MapStruct.

##

### 📦 Paquete > `Model`

> Modelos de Dominio no Persistentes

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 19 | 📊 | • Enum estados cuenta:<br>&nbsp;&nbsp;◦ `PENDING`<br>&nbsp;&nbsp;◦ `ACTIVE`<br>&nbsp;&nbsp;◦ `SUSPENDED`<br>&nbsp;&nbsp;◦ Etc. | `AccountStatus.java` |
| 20 | 💬 | • POJO respuestas error<br>• Campos:<br>&nbsp;&nbsp;◦ `message`<br>&nbsp;&nbsp;◦ `error`<br>&nbsp;&nbsp;◦ `status`<br>&nbsp;&nbsp;◦ `date` | `ErrorResponse.java` |

💡 **Model:** Clases de dominio que representan conceptos del negocio sin persistencia en base de datos.

##

### 📦 Paquete > `Repository`

> Repositorios de Acceso a Datos

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 21 | 🗃️ | • Repositorio JPA usuarios<br>• Existencia y búsquedas | `JpaUserRepository.java` |
| 22 | 🗃️ | • Repositorio roles<br>• Búsqueda por nombre | `JpaRoleRepository.java` |

💡 **Repository:** Interfaces que abstraen el acceso a datos y proporcionan operaciones CRUD sobre las entidades.

##

### 📦 Paquete > `Service`

> Servicios de Lógica de Negocio

#### 📂 Subpaquete `service/api` - Interfaces

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 23 | 📋 | • Interfaz CRUD usuarios | `IUserService.java` |
| 24 | 📋 | • Interfaz consultas existencia<br>• Por:<br>&nbsp;&nbsp;◦ Email<br>&nbsp;&nbsp;◦ Teléfono<br>&nbsp;&nbsp;◦ Username | `IUserQueryService.java` |

#### 📂 Subpaquete `service/impl` - Implementaciones

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 25 | ⚡ | • Impl `IUserService`<br>• Incluye:<br>&nbsp;&nbsp;◦ Transacciones<br>&nbsp;&nbsp;◦ Roles<br>&nbsp;&nbsp;◦ BCrypt | `UserServiceImpl.java` |
| 26 | ⚡ | • Impl `IUserQueryService`<br>• Delega al repositorio | `UserQueryServiceImpl.java` |
| 27 | 🔐 | • Impl `UserDetailsService`<br>• Funciones:<br>&nbsp;&nbsp;◦ Carga usuarios<br>&nbsp;&nbsp;◦ Convierte roles | `JpaUserDetailsServiceImpl.java` |

💡 **Service:** Contiene la lógica de negocio, coordina transacciones y orquesta operaciones entre repositorios.

##

### 📦 Paquete > `Util`

> Utilidades Generales

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 28 | 🔒 | • Codificación contraseñas<br>• BCrypt (detecta si codificada) | `PasswordUtil.java` |
| 29 | ✅ | • Validación de strings<br>• Genera HTTP 400 formateados | `ValidationUtil.java` |

💡 **Util:** Clases con métodos estáticos de propósito general reutilizables en toda la aplicación.

##

### 📦 Paquete > `Validation`

> Validadores Personalizados de Bean Validation

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 30 | 📧 | • Anotación email único en BD | `IExistsByEmail.java` |
| 31 | 📧 | • Impl validador email único | `ExistsByEmailValidationImpl.java` |
| 32 | 📱 | • Anotación teléfono único | `IExistsByPhoneNumber.java` |
| 33 | 📱 | • Impl validador teléfono único | `ExistsByPhoneNumberValidationImpl.java` |
| 34 | 👤 | • Anotación username único | `IExistsByUsername.java` |
| 35 | 👤 | • Impl validador username único | `ExistsByUsernameValidationImpl.java` |

💡 **Validation:** Anotaciones y validadores personalizados que extienden Bean Validation para reglas específicas.

##

### 📦 Paquete > `Security.Config`

> Configuración de Spring Security

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 36 | 🔐 | • Config Spring Security<br>• Incluye:<br>&nbsp;&nbsp;◦ Filtros<br>&nbsp;&nbsp;◦ Autorización<br>&nbsp;&nbsp;◦ Stateless<br>• CORS y JWT | `SpringSecurityConfig.java` |
| 37 | 🎟️ | • Config tokens JWT<br>• Incluye:<br>&nbsp;&nbsp;◦ Constantes<br>&nbsp;&nbsp;◦ Clave HMAC-SHA256 | `TokenJwtConfig.java` |

💡 **Security.Config:** Configuración que define reglas de autenticación, autorización y filtros de seguridad.

##

### 📦 Paquete > `Security.Filter`

> Filtros de Seguridad JWT

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 38 | 🔑 | • Filtro autenticación<br>• Procesa:<br>&nbsp;&nbsp;◦ Login<br>&nbsp;&nbsp;◦ JSON<br>&nbsp;&nbsp;◦ Token 1h | `JwtAuthenticationFilter.java` |
| 39 | ✔️ | • Filtro validación<br>• Valida JWT, `SecurityContext` | `JwtValidationFilter.java` |

💡 **Security.Filter:** Filtros de la cadena de seguridad que procesan autenticación y validación de tokens JWT.

##

### 📦 Paquete > `Security.Handler`

> Manejadores de Errores de Seguridad

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 40 | 🚫 | • Acceso denegado (403)<br>• Respuesta JSON | `RestAccessDeniedHandler.java` |
| 41 | 🚷 | • Error autenticación (401)<br>• Respuesta JSON | `RestAuthenticationEntryPoint.java` |

💡 **Security.Handler:** Manejadores que personalizan respuestas de error de seguridad (401, 403) en formato JSON.

##

### 📦 Paquete > `Security.Jackson`

> Configuración de Serialización Jackson

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 42 | 🔧 | • Mixin Jackson<br>• Deserializa `SimpleGrantedAuthority` | `SimpleGrantedAuthorityJsonCreator.java` |

💡 **Security.Jackson:** Configuración de Jackson para serializar/deserializar objetos de Spring Security en JSON.

---

### 💰 MÓDULO ACCOUNTS

> ⚠️ *En Desarrollo* - Microservicio de Gestión de Cuentas Bancarias

### ⚙️ Configuración Principal

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 43 | 🚀 | • Punto de entrada Spring Boot<br>• Módulo reactivo con WebFlux | `AccountsApplication.java` |
| 44 | 📝 | • Propiedades de aplicación<br>• Config BD PostgreSQL | `application.properties` |

💡 **Accounts:** Microservicio dedicado a la gestión de cuentas bancarias y transacciones financieras.

---

### 🌐 MÓDULO OAUTH2

> ⚠️ *En Desarrollo* - Integración con Proveedores OAuth2

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 45 | 🔗 | • Proveedores OAuth2 externos | `provider/` |
| 46 | 📝 | • Config propiedades OAuth2 | `application.properties` |

#### 📋 Flujo de Autenticación

```
[ React ]
   │
(Login OAuth)
   │
   ▼
[ OAuth Provider (Google, Keycloak, Auth0...) ]
   │
 (JWT)
   │
   ▼
[ Spring Boot API ]
```

💡 **OAuth2:** Microservicio para autenticación federada con proveedores externos usando OpenFeign.

---

### 📦 MÓDULO PRODUCT

> ⚠️ *En Desarrollo* - Microservicio de Catálogo de Productos Financieros

### ⚙️ Configuración Principal

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 47 | 🚀 | • Punto de entrada Spring Boot<br>• Módulo reactivo con WebFlux | `ProductApplication.java` |
| 48 | 📝 | • Propiedades de aplicación<br>• Config servidor | `application.properties` |

##

### 📦 Paquete > `Model`

> Enums de Tipos de Productos Financieros

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 49 | 🏦 | • Tipos de cuentas bancarias<br>• 11 variantes:<br>&nbsp;&nbsp;◦ Corriente<br>&nbsp;&nbsp;◦ Ahorro<br>&nbsp;&nbsp;◦ Plazo fijo<br>&nbsp;&nbsp;◦ Etc. | `Account.java` |
| 50 | 💳 | • Tipos de tarjetas<br>• Variantes:<br>&nbsp;&nbsp;◦ Débito<br>&nbsp;&nbsp;◦ Crédito | `Card.java` |
| 51 | 💰 | • Tipos de préstamos<br>• 7 variantes:<br>&nbsp;&nbsp;◦ Personal<br>&nbsp;&nbsp;◦ Hipoteca<br>&nbsp;&nbsp;◦ Auto<br>&nbsp;&nbsp;◦ Etc. | `Credit.java` |
| 52 | 🪙 | • Productos de criptomonedas<br>• 8 variantes:<br>&nbsp;&nbsp;◦ Spot<br>&nbsp;&nbsp;◦ Staking<br>&nbsp;&nbsp;◦ Lending<br>&nbsp;&nbsp;◦ Etc. | `CryptoProduct.java` |
| 53 | 📈 | • Trading de derivados<br>• 4 variantes:<br>&nbsp;&nbsp;◦ Margen<br>&nbsp;&nbsp;◦ Futuros<br>&nbsp;&nbsp;◦ Perpetuos<br>&nbsp;&nbsp;◦ Opciones | `Derivatives.java` |
| 54 | 🏠 | • Productos de financiación<br>• 5 variantes:<br>&nbsp;&nbsp;◦ Personal<br>&nbsp;&nbsp;◦ Hipoteca<br>&nbsp;&nbsp;◦ Auto<br>&nbsp;&nbsp;◦ BNPL | `Financing.java` |
| 55 | 📊 | • Trading e inversión<br>• 4 variantes:<br>&nbsp;&nbsp;◦ Bróker<br>&nbsp;&nbsp;◦ Custodia<br>&nbsp;&nbsp;◦ Fondos<br>&nbsp;&nbsp;◦ Pensión | `Investment.java` |
| 56 | 🎁 | • Programas de fidelización<br>• Recompensas | `Loyalty.java` |

##

### 📦 Paquete > `Repository`

> Repositorios de Acceso a Datos

| # | # | Descripción | Archivo |
|:-:|:-:|:------------|---------|
| 57 | 🗃️ | • Repositorio JPA productos<br>• Extiende JpaRepository | `JpaProductRepository.java` |

💡 **Product:** Microservicio dedicado al catálogo de productos financieros disponibles en la plataforma.

---

### 🚀 EJECUCIÓN DE MÓDULOS

```bash
# 📦 Módulo Auth:
⌨️ cd auth
⌨️ ./mvnw spring-boot:run

# 📦 Módulo Accounts:
⌨️ cd accounts
⌨️ ./mvnw spring-boot:run

# 📦 Módulo OAuth2:
⌨️ cd oauth2
⌨️ ./mvnw spring-boot:run

# 📦 Módulo Product:
⌨️ cd product
⌨️ ./mvnw spring-boot:run
```

---

### 🧹 FORMATEO DE CÓDIGO

> VS Code Java Formatter con perfil CryfiRock (4 espacios, 100 chars)

📁 **Archivos de configuración:**
- `.editorconfig` → Reglas básicas de archivos
- `.vscode/java-formatter.xml` → Perfil de formato Java
- `.vscode/settings.json` → Configuración del editor

### 📊 EditorConfig vs Java Formatter

| Aspecto | EditorConfig | Java Formatter XML |
|---------|:------------:|:------------------:|
| Indentación Java | 🔶 Respaldo | ✅ **Principal** |
| Largo de línea Java | ❌ No aplica | ✅ **100 chars** |
| Finales de línea (LF) | ✅ **Aplica** | ❌ No controla |
| Nueva línea al final | ✅ **Aplica** | ❌ No controla |
| Trailing whitespace | ✅ **Aplica** | ❌ No controla |
| Formato XML | ✅ **Aplica** | ❌ No aplica |

💡 **EditorConfig** funciona para reglas de archivos y formato básico, pero para formatear código Java específicamente, el **Java Formatter XML** es el que manda.

---

### 📡 API ENDPOINTS

### 🔐 Módulo Auth (`auth/controller/UserController.java`):

| Método</sub> | Endpoint</sub> | Descripción</sub> | 🔐 Auth</sub> |
|:------:|----------|-------------|:-------:|
| `POST`</sub> | `/login`</sub> | Autenticación de usuario</sub> | 🌍 Público</sub> |
| `POST`</sub> | `/api/users`</sub> | Crear usuario</sub> | 🌍 Público</sub> |
| `POST`</sub> | `/api/users/superuser`</sub> | Crear administrador</sub> | 👑 ADMIN</sub> |
| `GET`</sub> | `/api/users`</sub> | Listar usuarios</sub> | 👤 USER/ADMIN</sub> |
| `GET`</sub> | `/api/users/{id}`</sub> | Obtener usuario</sub> | 👤 USER/ADMIN</sub> |
| `PUT`</sub> | `/api/users/{id}`</sub> | Actualizar usuario</sub> | 👤 USER/ADMIN</sub> |
| `DELETE`</sub> | `/api/users/{id}`</sub> | Eliminar usuario</sub> | 👤 USER/ADMIN</sub> |

### 🔍 Módulo Auth (`auth/controller/UserValidationController.java`):

| Método</sub> | Endpoint</sub> | Descripción</sub> | 🔐 Auth</sub> |
|:------:|----------|-------------|:-------:|
| `GET`</sub> | `/api/validations/exists?email={email}`</sub> | Verificar si email existe</sub> | 👤 USER/ADMIN</sub> |
| `GET`</sub> | `/api/validations/exists?username={username}`</sub> | Verificar si username existe</sub> | 👤 USER/ADMIN</sub> |
| `GET`</sub> | `/api/validations/exists?phoneNumber={phoneNumber}`</sub> | Verificar si teléfono existe</sub> | 👤 USER/ADMIN</sub> |

---

### 📚 DEPENDENCIAS DE PROYECTOS

### 🔐 Módulo Auth (`auth/pom.xml`):

| <sub>Dependencia</sub> | <sub>Versión</sub> | <sub>Scope</sub> | <sub>Descripción</sub> |
|:------------|:-------:|:-----:|:------------|
| <sub>**spring-boot-starter-parent**</sub> | <sub>`3.5.9`</sub> | <sub>parent</sub> | <sub>BOM padre de Spring Boot con gestión de versiones</sub> |
| <sub>**mapstruct**</sub> | <sub>`1.6.3`</sub> | <sub>compile</sub> | <sub>Framework de mapeo entre objetos (DTOs ↔ Entities)</sub> |
| <sub>**spring-boot-starter-actuator**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Endpoints de monitoreo y métricas de la aplicación</sub> |
| <sub>**spring-boot-starter-data-jpa**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Persistencia JPA con Hibernate y Spring Data</sub> |
| <sub>**spring-boot-starter-security**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Autenticación y autorización con Spring Security</sub> |
| <sub>**spring-boot-starter-validation**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Validación de beans con Jakarta Bean Validation</sub> |
| <sub>**spring-boot-starter-web**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Desarrollo web MVC con Tomcat embebido</sub> |
| <sub>**spring-boot-starter-webflux**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Programación reactiva con Project Reactor</sub> |
| <sub>**spring-boot-starter-aop**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Programación orientada a aspectos (AOP)</sub> |
| <sub>**jjwt-api**</sub> | <sub>`0.12.6`</sub> | <sub>compile</sub> | <sub>API de JSON Web Tokens (JJWT)</sub> |
| <sub>**jjwt-impl**</sub> | <sub>`0.12.6`</sub> | <sub>runtime</sub> | <sub>Implementación de JJWT</sub> |
| <sub>**jjwt-jackson**</sub> | <sub>`0.12.6`</sub> | <sub>runtime</sub> | <sub>Serialización Jackson para JJWT</sub> |
| <sub>**spring-boot-devtools**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Herramientas de desarrollo (hot reload)</sub> |
| <sub>**mysql-connector-j**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Driver JDBC para MySQL</sub> |
| <sub>**postgresql**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Driver JDBC para PostgreSQL</sub> |
| <sub>**h2**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Base de datos H2 en memoria para testing</sub> |
| <sub>**lombok**</sub> | <sub>-</sub> | <sub>optional</sub> | <sub>Generación de código (getters, setters, builders)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**reactor-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de flujos reactivos</sub> |
| <sub>**spring-security-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de seguridad</sub> |

##

### 💰 Módulo Accounts (`accounts/pom.xml`):

| <sub>Dependencia</sub> | <sub>Versión</sub> | <sub>Scope</sub> | <sub>Descripción</sub> |
|:------------|:-------:|:-----:|:------------|
| <sub>**spring-boot-starter-parent**</sub> | <sub>`3.5.9`</sub> | <sub>parent</sub> | <sub>BOM padre de Spring Boot con gestión de versiones</sub> |
| <sub>**spring-boot-starter-data-jpa**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Persistencia JPA con Hibernate y Spring Data</sub> |
| <sub>**spring-boot-starter-validation**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Validación de beans con Jakarta Bean Validation</sub> |
| <sub>**spring-boot-starter-webflux**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Programación reactiva con WebFlux y Project Reactor</sub> |
| <sub>**postgresql**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Driver JDBC para PostgreSQL</sub> |
| <sub>**lombok**</sub> | <sub>-</sub> | <sub>optional</sub> | <sub>Generación de código (getters, setters, builders)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**reactor-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de flujos reactivos</sub> |

##

### 🌐 Módulo OAuth2 (`oauth2/pom.xml`):

| <sub>Dependencia</sub> | <sub>Versión</sub> | <sub>Scope</sub> | <sub>Descripción</sub> |
|:------------|:-------:|:-----:|:------------|
| <sub>**spring-boot-starter-parent**</sub> | <sub>`3.5.9`</sub> | <sub>parent</sub> | <sub>BOM padre de Spring Boot con gestión de versiones</sub> |
| <sub>**spring-boot-starter-actuator**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Endpoints de monitoreo y métricas de la aplicación</sub> |
| <sub>**spring-boot-starter-security**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Autenticación y autorización con Spring Security</sub> |
| <sub>**spring-boot-starter-validation**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Validación de beans con Jakarta Bean Validation</sub> |
| <sub>**spring-boot-starter-web**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Desarrollo web MVC con Tomcat embebido</sub> |
| <sub>**spring-cloud-dependencies**</sub> | <sub>`2024.0.1`</sub> | <sub>BOM</sub> | <sub>Gestión de versiones de Spring Cloud</sub> |
| <sub>**spring-cloud-starter-openfeign**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Cliente HTTP declarativo para microservicios</sub> |
| <sub>**spring-cloud-starter-loadbalancer**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Balanceo de carga del lado del cliente</sub> |
| <sub>**spring-boot-devtools**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Herramientas de desarrollo (hot reload)</sub> |
| <sub>**lombok**</sub> | <sub>-</sub> | <sub>optional</sub> | <sub>Generación de código (getters, setters, builders)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**spring-security-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de seguridad</sub> |

##

### 📦 Módulo Product (`product/pom.xml`):

| <sub>Dependencia</sub> | <sub>Versión</sub> | <sub>Scope</sub> | <sub>Descripción</sub> |
|:------------|:-------:|:-----:|:------------|
| <sub>**spring-boot-starter-parent**</sub> | <sub>`3.5.9`</sub> | <sub>parent</sub> | <sub>BOM padre de Spring Boot con gestión de versiones</sub> |
| <sub>**spring-boot-starter-webflux**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Programación reactiva con WebFlux y Project Reactor</sub> |
| <sub>**spring-boot-starter-data-jpa**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Persistencia JPA con Hibernate y Spring Data</sub> |
| <sub>**spring-boot-devtools**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Herramientas de desarrollo (hot reload)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**reactor-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de flujos reactivos</sub> |

💡 Las versiones marcadas con `-` son gestionadas automáticamente por `spring-boot-starter-parent`.

---

<p align="center">
  Desarrollado con ❤️ por <strong>CryfiRock Team</strong>
</p>
