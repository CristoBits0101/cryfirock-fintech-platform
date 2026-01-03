<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
</p>

<h1 align="center">🏦 CryfiRock Fintech Platform</h1>

<p align="center">
  <strong>Plataforma fintech con Spring Boot que ofrece autenticación y autorización mediante JWT</strong>
</p>

##

### <sub>📋 Tabla de Contenidos</sub>

- <sub>[Módulo Auth](#-módulo-auth) ⭍</sub>
- <sub>[Módulo OAuth2](#-módulo-oauth2) ⭍</sub>
- <sub>[Ejecución](#-ejecución) ⭍</sub>
- <sub>[API Endpoints](#-api-endpoints) ⭍</sub>

##

### <sub>🔐 Módulo Auth</sub>

> <sub>Microservicio de Autenticación y Gestión de Usuarios</sub>

### <sub>⚙️ Configuración Principal</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>1</sub> | <sub>🚀</sub> | <sub>• Punto de entrada Spring Boot<br>• Habilita AspectJ (AOP)</sub> | <sub>`AuthApplication.java`</sub> |
| <sub>2</sub> | <sub>📝</sub> | <sub>• Propiedades de aplicación<br>• Config BD y servidor</sub> | <sub>`application.properties`</sub> |
| <sub>3</sub> | <sub>🌐</sub> | <sub>• Mensajes i18n<br>• Validaciones y errores</sub> | <sub>`messages.properties`</sub> |

##

### <sub>📦 Paquete > `Advice`</sub>

> <sub>*Manejo Centralizado de Excepciones*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>4</sub> | <sub>⚠️</sub> | <sub>• Controlador de excepciones global<br>• Maneja:<br>&nbsp;&nbsp;◦ Validación<br>&nbsp;&nbsp;◦ 404<br>&nbsp;&nbsp;◦ Usuarios<br>&nbsp;&nbsp;◦ JSON<br>• Usa `MessageSource` (i18n)</sub> | <sub>`GlobalExceptionHandler.java`</sub> |

<sub>💡 **Advice:** Componente de Spring AOP que captura y maneja excepciones de forma centralizada.</sub>

##

### <sub>📦 Paquete > `Aspect`</sub>

> <sub>*Programación Orientada a Aspectos (AOP)*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>5</sub> | <sub>📊</sub> | <sub>• Intercepta métodos de `UserServiceImpl`<br>• Advices:<br>&nbsp;&nbsp;◦ `@Before`<br>&nbsp;&nbsp;◦ `@After`<br>&nbsp;&nbsp;◦ `@Around`<br>• Logging de operaciones</sub> | <sub>`UserAspect.java`</sub> |

<sub>💡 **Aspect:** Clase AOP que intercepta métodos para lógica transversal de logging, seguridad y transacciones.</sub>

##

### <sub>📦 Paquete > `Config`</sub>

> <sub>*Configuración de Spring MVC*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>6</sub> | <sub>🔧</sub> | <sub>• Config Spring MVC<br>• Interceptor `/api/users/**`<br>• `MessageSource` (i18n)</sub> | <sub>`AppConfig.java`</sub> |

<sub>💡 **Config:** Clases de configuración de Spring que definen beans, interceptores y ajustes del framework.</sub>

##

### <sub>📦 Paquete > `Controller`</sub>

> <sub>*Controladores REST de la API*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>7</sub> | <sub>🎮</sub> | <sub>• REST para gestión de usuarios<br>• CRUD endpoints<br>• `@PreAuthorize` (roles)</sub> | <sub>`UserController.java`</sub> |

<sub>💡 **Controller:** Componente que recibe peticiones HTTP, las procesa y devuelve respuestas al cliente.</sub>

##

### <sub>📦 Paquete > `DTO`</sub>

> <sub>*Objetos de Transferencia de Datos*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>8</sub> | <sub>🔑</sub> | <sub>• Record credenciales login<br>• Campos validados:<br>&nbsp;&nbsp;◦ `username`<br>&nbsp;&nbsp;◦ `password`</sub> | <sub>`UserLoginDto.java`</sub> |
| <sub>9</sub> | <sub>✏️</sub> | <sub>• Record actualización parcial<br>• Campos:<br>&nbsp;&nbsp;◦ Nombre<br>&nbsp;&nbsp;◦ Email<br>&nbsp;&nbsp;◦ Teléfono<br>&nbsp;&nbsp;◦ Etc...</sub> | <sub>`UserUpdateDto.java`</sub> |

<sub>💡 **DTO:** Objeto que transporta datos entre capas sin lógica de negocio usado para entrada/salida de la API.</sub>

##

### <sub>📦 Paquete > `Entity`</sub>

> <sub>*Entidades JPA Persistentes*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>10</sub> | <sub>👤</sub> | <sub>• Entidad principal usuario<br>• Contiene:<br>&nbsp;&nbsp;◦ Datos<br>&nbsp;&nbsp;◦ Credenciales<br>&nbsp;&nbsp;◦ Estado<br>• Relación con roles</sub> | <sub>`User.java`</sub> |
| <sub>11</sub> | <sub>🎭</sub> | <sub>• Entidad rol<br>• Relación bidireccional<br>• Roles:<br>&nbsp;&nbsp;◦ `ROLE_USER`<br>&nbsp;&nbsp;◦ `ROLE_ADMIN`</sub> | <sub>`Role.java`</sub> |
| <sub>12</sub> | <sub>📅</sub> | <sub>• Clase embebible auditoría<br>• Fechas:<br>&nbsp;&nbsp;◦ Creación<br>&nbsp;&nbsp;◦ Update<br>&nbsp;&nbsp;◦ Login</sub> | <sub>`Audit.java`</sub> |

<sub>💡 **Entity:** Clase que representa una tabla de la base de datos y es gestionada por JPA/Hibernate.</sub>

<hr>

### <sub>📦 Paquete > `Exception`</sub>

> <sub>*Excepciones Personalizadas*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>13</sub> | <sub>❌</sub> | <sub>• Usuario no encontrado en BD</sub> | <sub>`UserNotFoundException.java`</sub> |

<sub>💡 **Exception:** Clases que representan errores específicos del dominio para un manejo de errores más preciso.</sub>

<hr>

### <sub>📦 Paquete > `Helper`</sub>

> <sub>*Clases Auxiliares de Lógica de Negocio*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>14</sub> | <sub>🎯</sub> | <sub>• Asigna roles a usuarios<br>• `ROLE_USER` (todos)<br>• `ROLE_ADMIN` (flag admin)</sub> | <sub>`RolesHelper.java`</sub> |

<sub>💡 **Helper:** Clases auxiliares con métodos utilitarios que encapsulan lógica de negocio reutilizable.</sub>

---

### <sub>📦 Paquete > `Interceptor`</sub>

> <sub>*Interceptores HTTP*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>15</sub> | <sub>🛡️</sub> | <sub>• Valida tokens JWT<br>• Verifica:<br>&nbsp;&nbsp;◦ Header<br>&nbsp;&nbsp;◦ Firma<br>&nbsp;&nbsp;◦ Expiración</sub> | <sub>`UserOperationsInterceptor.java`</sub> |

<sub>💡 **Interceptor:** Componente que intercepta peticiones HTTP antes/después de llegar al controlador.</sub>

---

### <sub>📦 Paquete > `Mapper`</sub>

> <sub>*Mapeadores de Objetos (MapStruct)*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>16</sub> | <sub>🔄</sub> | <sub>• Convierte params → `Error`<br>• Fecha automática</sub> | <sub>`ErrorMapper.java`</sub> |
| <sub>17</sub> | <sub>🔄</sub> | <sub>• Actualización parcial usuarios<br>• Ignora campos sensibles</sub> | <sub>`UserMapper.java`</sub> |

<sub>💡 **Mapper:** Interfaces que convierten automáticamente entre DTOs y Entities usando MapStruct.</sub>

---

### <sub>📦 Paquete > `Model`</sub>

> <sub>*Modelos de Dominio no Persistentes*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>18</sub> | <sub>📊</sub> | <sub>• Enum estados cuenta:<br>&nbsp;&nbsp;◦ `PENDING`<br>&nbsp;&nbsp;◦ `ACTIVE`<br>&nbsp;&nbsp;◦ `SUSPENDED`<br>&nbsp;&nbsp;◦ Etc.</sub> | <sub>`AccountStatus.java`</sub> |
| <sub>19</sub> | <sub>💬</sub> | <sub>• POJO respuestas error<br>• Campos:<br>&nbsp;&nbsp;◦ `message`<br>&nbsp;&nbsp;◦ `error`<br>&nbsp;&nbsp;◦ `status`<br>&nbsp;&nbsp;◦ `date`</sub> | <sub>`Error.java`</sub> |

<sub>💡 **Model:** Clases de dominio que representan conceptos del negocio sin persistencia en base de datos.</sub>

---

### <sub>📦 Paquete > `Repository`</sub>

> <sub>*Repositorios de Acceso a Datos*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>20</sub> | <sub>🗃️</sub> | <sub>• Repositorio JPA usuarios<br>• Existencia y búsquedas</sub> | <sub>`JpaUserRepository.java`</sub> |
| <sub>21</sub> | <sub>🗃️</sub> | <sub>• Repositorio roles<br>• Búsqueda por nombre</sub> | <sub>`JpaRoleRepository.java`</sub> |

<sub>💡 **Repository:** Interfaces que abstraen el acceso a datos y proporcionan operaciones CRUD sobre las entidades.</sub>

---

### <sub>📦 Paquete > `Service`</sub>

> <sub>*Servicios de Lógica de Negocio*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>22</sub> | <sub>📋</sub> | <sub>• Interfaz CRUD usuarios</sub> | <sub>`IUserService.java`</sub> |
| <sub>23</sub> | <sub>📋</sub> | <sub>• Interfaz consultas existencia<br>• Por:<br>&nbsp;&nbsp;◦ Email<br>&nbsp;&nbsp;◦ Teléfono<br>&nbsp;&nbsp;◦ Username</sub> | <sub>`IUserQueryService.java`</sub> |
| <sub>24</sub> | <sub>⚡</sub> | <sub>• Impl `IUserService`<br>• Incluye:<br>&nbsp;&nbsp;◦ Transacciones<br>&nbsp;&nbsp;◦ Roles<br>&nbsp;&nbsp;◦ BCrypt</sub> | <sub>`UserServiceImpl.java`</sub> |
| <sub>25</sub> | <sub>⚡</sub> | <sub>• Impl `IUserQueryService`<br>• Delega al repositorio</sub> | <sub>`UserQueryServiceImpl.java`</sub> |
| <sub>26</sub> | <sub>🔐</sub> | <sub>• Impl `UserDetailsService`<br>• Funciones:<br>&nbsp;&nbsp;◦ Carga usuarios<br>&nbsp;&nbsp;◦ Convierte roles</sub> | <sub>`JpaUserDetailsServiceImpl.java`</sub> |

<sub>💡 **Service:** Contiene la lógica de negocio, coordina transacciones y orquesta operaciones entre repositorios.</sub>

---

### <sub>📦 Paquete > `Util`</sub>

> <sub>*Utilidades Generales*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>27</sub> | <sub>🔒</sub> | <sub>• Codificación contraseñas<br>• BCrypt (detecta si codificada)</sub> | <sub>`PasswordUtil.java`</sub> |
| <sub>28</sub> | <sub>✅</sub> | <sub>• Validación de strings<br>• Genera HTTP 400 formateados</sub> | <sub>`ValidationUtil.java`</sub> |

<sub>💡 **Util:** Clases con métodos estáticos de propósito general reutilizables en toda la aplicación.</sub>

---

### <sub>📦 Paquete > `Validation`</sub>

> <sub>*Validadores Personalizados de Bean Validation*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>29</sub> | <sub>📧</sub> | <sub>• Anotación email único en BD</sub> | <sub>`IExistsByEmail.java`</sub> |
| <sub>30</sub> | <sub>📧</sub> | <sub>• Impl validador email único</sub> | <sub>`ExistsByEmailValidationImpl.java`</sub> |
| <sub>31</sub> | <sub>📱</sub> | <sub>• Anotación teléfono único</sub> | <sub>`IExistsByPhoneNumber.java`</sub> |
| <sub>32</sub> | <sub>📱</sub> | <sub>• Impl validador teléfono único</sub> | <sub>`ExistsByPhoneNumberValidationImpl.java`</sub> |
| <sub>33</sub> | <sub>👤</sub> | <sub>• Anotación username único</sub> | <sub>`IExistsByUsername.java`</sub> |
| <sub>34</sub> | <sub>👤</sub> | <sub>• Impl validador username único</sub> | <sub>`ExistsByUsernameValidationImpl.java`</sub> |

<sub>💡 **Validation:** Anotaciones y validadores personalizados que extienden Bean Validation para reglas específicas.</sub>

---

### <sub>📦 Paquete > `Security.Config`</sub>

> <sub>*Configuración de Spring Security*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>35</sub> | <sub>🔐</sub> | <sub>• Config Spring Security<br>• Incluye:<br>&nbsp;&nbsp;◦ Filtros<br>&nbsp;&nbsp;◦ Autorización<br>&nbsp;&nbsp;◦ Stateless<br>• CORS y JWT</sub> | <sub>`SpringSecurityConfig.java`</sub> |
| <sub>36</sub> | <sub>🎟️</sub> | <sub>• Config tokens JWT<br>• Incluye:<br>&nbsp;&nbsp;◦ Constantes<br>&nbsp;&nbsp;◦ Clave HMAC-SHA256</sub> | <sub>`TokenJwtConfig.java`</sub> |

<sub>💡 **Security.Config:** Configuración que define reglas de autenticación, autorización y filtros de seguridad.</sub>

---

### <sub>📦 Paquete > `Security.Filter`</sub>

> <sub>*Filtros de Seguridad JWT*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>37</sub> | <sub>🔑</sub> | <sub>• Filtro autenticación<br>• Procesa:<br>&nbsp;&nbsp;◦ Login<br>&nbsp;&nbsp;◦ JSON<br>&nbsp;&nbsp;◦ Token 1h</sub> | <sub>`JwtAuthenticationFilter.java`</sub> |
| <sub>38</sub> | <sub>✔️</sub> | <sub>• Filtro validación<br>• Valida JWT, `SecurityContext`</sub> | <sub>`JwtValidationFilter.java`</sub> |

<sub>💡 **Security.Filter:** Filtros de la cadena de seguridad que procesan autenticación y validación de tokens JWT.</sub>

---

### <sub>📦 Paquete > `Security.Handler`</sub>

> <sub>*Manejadores de Errores de Seguridad*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>39</sub> | <sub>🚫</sub> | <sub>• Acceso denegado (403)<br>• Respuesta JSON</sub> | <sub>`RestAccessDeniedHandler.java`</sub> |
| <sub>40</sub> | <sub>🚷</sub> | <sub>• Error autenticación (401)<br>• Respuesta JSON</sub> | <sub>`RestAuthenticationEntryPoint.java`</sub> |

<sub>💡 **Security.Handler:** Manejadores que personalizan respuestas de error de seguridad (401, 403) en formato JSON.</sub>

---

### <sub>📦 Paquete > `Security.Jackson`</sub>

> <sub>*Configuración de Serialización Jackson*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>41</sub> | <sub>🔧</sub> | <sub>• Mixin Jackson<br>• Deserializa `SimpleGrantedAuthority`</sub> | <sub>`SimpleGrantedAuthorityJsonCreator.java`</sub> |

<sub>💡 **Security.Jackson:** Configuración de Jackson para serializar/deserializar objetos de Spring Security en JSON.</sub>

---

## <sub>🌐 Módulo OAuth2</sub>

> <sub>⚠️ *En Desarrollo*</sub>

| <sub>#</sub> | <sub>#</sub> | <sub>Descripción</sub> | <sub>Archivo</sub> |
|:-:|:-:|:------------|---------|
| <sub>42</sub> | <sub>🔗</sub> | <sub>• Proveedores OAuth2 externos</sub> | <sub>`provider/`</sub> |
| <sub>43</sub> | <sub>📝</sub> | <sub>• Config propiedades OAuth2</sub> | <sub>`application.properties`</sub> |

---

## <sub>🚀 Ejecución de Módulos</sub>

```bash
# 📦 Módulo Auth:
⌨️ cd auth
⌨️ ./mvnw spring-boot:run

# 📦 Módulo OAuth2:
⌨️ cd oauth2
⌨️ ./mvnw spring-boot:run
```

---

## <sub>📡 API Endpoints</sub>

| <sub>Método</sub> | <sub>Endpoint</sub> | <sub>Descripción</sub> | <sub>🔐 Auth</sub> |
|:------:|----------|-------------|:-------:|
| <sub>`POST`</sub> | <sub>`/login`</sub> | <sub>Autenticación de usuario</sub> | <sub>🌍 Público</sub> |
| <sub>`POST`</sub> | <sub>`/api/users`</sub> | <sub>Crear usuario</sub> | <sub>🌍 Público</sub> |
| <sub>`POST`</sub> | <sub>`/api/users/superuser`</sub> | <sub>Crear administrador</sub> | <sub>👑 ADMIN</sub> |
| <sub>`GET`</sub> | <sub>`/api/users`</sub> | <sub>Listar usuarios</sub> | <sub>👤 USER/ADMIN</sub> |
| <sub>`GET`</sub> | <sub>`/api/users/{id}`</sub> | <sub>Obtener usuario</sub> | <sub>👤 USER/ADMIN</sub> |
| <sub>`PUT`</sub> | <sub>`/api/users/{id}`</sub> | <sub>Actualizar usuario</sub> | <sub>👤 USER/ADMIN</sub> |
| <sub>`DELETE`</sub> | <sub>`/api/users/{id}`</sub> | <sub>Eliminar usuario</sub> | <sub>👤 USER/ADMIN</sub> |

---

## <sub>📚 Dependencias del Proyecto</sub>

### <sub>🔐 Módulo Auth (`auth/pom.xml`)</sub>

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
| <sub>**lombok**</sub> | <sub>-</sub> | <sub>optional</sub> | <sub>Generación de código (getters, setters, builders)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**reactor-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de flujos reactivos</sub> |
| <sub>**spring-security-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de seguridad</sub> |

---

### <sub>🌐 Módulo OAuth2 (`oauth2/pom.xml`)</sub>

| <sub>Dependencia</sub> | <sub>Versión</sub> | <sub>Scope</sub> | <sub>Descripción</sub> |
|:------------|:-------:|:-----:|:------------|
| <sub>**spring-boot-starter-parent**</sub> | <sub>`3.5.7`</sub> | <sub>parent</sub> | <sub>BOM padre de Spring Boot con gestión de versiones</sub> |
| <sub>**spring-boot-starter-actuator**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Endpoints de monitoreo y métricas de la aplicación</sub> |
| <sub>**spring-boot-starter-data-jpa**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Persistencia JPA con Hibernate y Spring Data</sub> |
| <sub>**spring-boot-starter-security**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Autenticación y autorización con Spring Security</sub> |
| <sub>**spring-boot-starter-validation**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Validación de beans con Jakarta Bean Validation</sub> |
| <sub>**spring-boot-starter-webflux**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Reactividad asíncrona con Netty y WebClient</sub> |
| <sub>**spring-cloud-dependencies**</sub> | <sub>`2024.0.1`</sub> | <sub>BOM</sub> | <sub>Gestión de versiones de Spring Cloud</sub> |
| <sub>**spring-cloud-starter-openfeign**</sub> | <sub>-</sub> | <sub>compile</sub> | <sub>Cliente HTTP declarativo para microservicios</sub> |
| <sub>**spring-boot-devtools**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Herramientas de desarrollo (hot reload)</sub> |
| <sub>**mysql-connector-j**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Driver JDBC para MySQL</sub> |
| <sub>**postgresql**</sub> | <sub>-</sub> | <sub>runtime</sub> | <sub>Driver JDBC para PostgreSQL</sub> |
| <sub>**lombok**</sub> | <sub>-</sub> | <sub>optional</sub> | <sub>Generación de código (getters, setters, builders)</sub> |
| <sub>**spring-boot-starter-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing con JUnit, Mockito, AssertJ</sub> |
| <sub>**reactor-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de flujos reactivos</sub> |
| <sub>**spring-security-test**</sub> | <sub>-</sub> | <sub>test</sub> | <sub>Testing de seguridad</sub> |

<sub>💡 Las versiones marcadas con `-` son gestionadas automáticamente por `spring-boot-starter-parent`.</sub>

---

<p align="center">
  <sub>Desarrollado con ❤️ por <strong>CryfiRock Team</strong></sub>
</p>