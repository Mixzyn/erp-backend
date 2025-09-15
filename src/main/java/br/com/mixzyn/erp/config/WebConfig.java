package br.com.mixzyn.erp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.imgDir}")
    private String imgDir;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Garante que a URL /imgs/** aponte para a pasta configurada
        registry.addResourceHandler("/imgs/**")
                .addResourceLocations("file:" + imgDir + "/");
    }
}
