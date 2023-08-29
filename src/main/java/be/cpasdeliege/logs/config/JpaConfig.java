package be.cpasdeliege.logs.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "be.cpasdeliege.logs.repository")
@EntityScan(basePackages = "be.cpasdeliege.logs.model")
public class JpaConfig {
}
