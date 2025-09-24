package com.cryfirock.auth.service.configurations;

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

// Clase de configuración de Spring que además escanea paquetes
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.auth.service",
        "com.cryfirock.auth.service.validations"
})
public class AppConfig implements WebMvcConfigurer {

    /**
     * Atributos
     */
    @Autowired
    @Qualifier("userOperationsInterceptor")
    private HandlerInterceptor userOperationsInterceptor;

    /**
     * Registra el interceptor y las rutas en las que se ejecuta
     *
     * @param registry registro de interceptores
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(userOperationsInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users");
    }

    /**
     * Configura el bean MessageSource para usar archivos properties como fuente de mensajes
     *
     * @return instancia configurada de MessageSource
     */
    @Bean
    MessageSource messageSource() {
        // Crea una nueva instancia de ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Establece la ubicación de los archivos de propiedades de mensajes
        messageSource.setBasename("classpath:config/messages");

        // Configura la codificación predeterminada a UTF-8
        messageSource.setDefaultEncoding("UTF-8");

        // Habilita la caché para las propiedades de los mensajes
        return messageSource;
    }

}