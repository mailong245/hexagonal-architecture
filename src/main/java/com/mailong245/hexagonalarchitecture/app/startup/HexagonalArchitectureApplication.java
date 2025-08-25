package com.mailong245.hexagonalarchitecture.app.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.mailong245"
})
@EnableJpaRepositories(basePackages = "com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity")
public class HexagonalArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureApplication.class, args);
    }

}
