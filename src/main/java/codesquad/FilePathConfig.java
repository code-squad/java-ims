package codesquad;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:filepath.properties")
public class FilePathConfig {
}
