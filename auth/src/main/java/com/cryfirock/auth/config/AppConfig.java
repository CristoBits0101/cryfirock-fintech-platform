package com.cryfirock.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1. Componente de configuración de la aplicación.
 * 2. Escanea los paquetes especificados para detectar componentes de Spring.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Configuration
@ComponentScan(basePackages = { "com.cryfirock.auth.service", "com.cryfirock.auth.validation" })
public class AppConfig implements WebMvcConfigurer {
    /**
     * 1. Interceptor para operaciones de usuario.
     * 2. Se inyecta mediante Autowired y el selector Qualifier.
     * 3. Interceptar solicitudes a rutas específicas relacionadas con usuarios.
     * 4. No puede ser nulo en tiempo de ejecución.
     */
    @Autowired
    @Qualifier("userOperationsInterceptor")
    @NonNull
    private HandlerInterceptor userOperationsInterceptor;

    /**
     * 1. Configura el interceptor userOperationsInterceptor para la aplicación web.
     * 2. Intercepta todas las solicitudes a rutas que coinciden con /api/users/**
     * 3. Excluye la ruta /api/users de la interceptación.
     *
     * @param registry Registro de interceptores para la aplicación web.
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(userOperationsInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users");
    }

    /**
     * 1. Registra la fuente de mensajes configurada en la carpeta i18n.
     * 2. Carga mensajes desde messages.properties ubicado en classpath:i18n/.
     * 3. Establece la codificación predeterminada en UTF-8.
     * 4. Permite la recarga de mensajes sin reiniciar la aplicación.
     *
     * @return MessageSource configurado para i18n.
     */
    @Bean
    @SuppressWarnings("unused")
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
