package be.cpasdeliege.matulis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirection des URL non trouvées ("/**") vers la page principale ("/")
        registry.addViewController("/home").setViewName("forward:/");
        registry.addViewController("/login").setViewName("forward:/");
    }
}
