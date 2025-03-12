package es.ua.biblioteca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Asumiendo que las imágenes se guardan en /opt/myapp/images/authors (ajusta según tu entorno)
        registry.addResourceHandler("/images/authors/**")
                .addResourceLocations("file:./images/authors/");

    }
}