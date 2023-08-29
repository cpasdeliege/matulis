package be.cpasdeliege.logs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "be.cpasdeliege.logs.repository")
public class JpaConfig {
}
