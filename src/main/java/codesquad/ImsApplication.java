package codesquad;

import codesquad.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableJpaAuditing
public class ImsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImsApplication.class, args);
    }
}
