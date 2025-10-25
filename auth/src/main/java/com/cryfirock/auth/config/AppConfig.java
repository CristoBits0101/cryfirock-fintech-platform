package com.cryfirock.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * =============================================================================================
 * Paso 8.1: Configuración general de Spring y registro de interceptores
 * =============================================================================================
 */
// Clase de configuración de Spring que además escanea paquetes
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.auth.service",
        "com.cryfirock.auth.validation"
})
public class AppConfig implements WebMvcConfigurer {

    /**
     * =========================================================================================
     * Paso 8.2: Atributos
     * =========================================================================================
     */
    @Autowired
    @Qualifier("userOperationsInterceptor")
    private HandlerInterceptor userOperationsInterceptor;

    /**
     * =========================================================================================
     * Paso 8.3: Registro de interceptores
     * =========================================================================================
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(userOperationsInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users");
    }

    /**
     * =========================================================================================
     * Paso 8.4: Configuración del MessageSource
     * =========================================================================================
     */
    @Bean
    MessageSource messageSource() {
        // Crea una nueva instancia de ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Establece la ubicación de los archivos de propiedades de mensajes
        messageSource.setBasename("classpath:i18n/messages");

        // Configura la codificación predeterminada a UTF-8
        messageSource.setDefaultEncoding("UTF-8");

        // Devuelve el bean configurado
        return messageSource;
    }

}
