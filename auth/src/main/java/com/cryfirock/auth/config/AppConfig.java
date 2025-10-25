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
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.auth.service",
        "com.cryfirock.auth.validation"
})
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("userOperationsInterceptor")
    private HandlerInterceptor userOperationsInterceptor;
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(userOperationsInterceptor)
                .addPathPatterns("/api/users/**")
                .excludePathPatterns("/api/users");
    }
    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
