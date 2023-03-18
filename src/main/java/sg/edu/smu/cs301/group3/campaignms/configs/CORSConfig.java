package sg.edu.smu.cs301.group3.campaignms.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                .allowedMethods("*")
                .exposedHeaders("*")
                .allowedOrigins("*");
    }

}
