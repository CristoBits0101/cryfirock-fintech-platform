package com.cryfirock.auth.service.configurations;

/**
 * Dependencies
 */
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

// Spring configuration classes that additionally scan packages
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.msvc.users.msvc_users",
        "com.cryfirock.msvc.users.msvc_users.validations"
})
public class AppConfig implements WebMvcConfigurer {

    /**
     * Attributes
     */
    @Autowired
    @Qualifier("userOperationsInterceptor")
    private HandlerInterceptor userOperationsInterceptor;

    /**
     * Register the interceptor and the routes on which it runs
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(userOperationsInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users");
    }

    /**
     * Configure the MessageSource bean to use properties files as message sources
     * 
     * @return messageSource
     */
    @Bean
    MessageSource messageSource() {
        // Create a new instance of ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Set the locations of the message properties files
        messageSource.setBasename("classpath:config/messages");

        // Set the default encoding to UTF-8
        messageSource.setDefaultEncoding("UTF-8");

        // Enable cache for message properties
        return messageSource;
    }

}