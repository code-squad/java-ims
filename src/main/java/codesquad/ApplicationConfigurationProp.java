package codesquad;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "file.avatar")
@PropertySource("classpath:configprops.properties")
public class ApplicationConfigurationProp {

    private String path;
    private Dummy dummy = new Dummy();
    private List<String> suffix = new ArrayList<>();

    public class Dummy {

        private String path;
        private String name;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Dummy getDummy() {
        return dummy;
    }

    public void setDummy(Dummy dummy) {
        this.dummy = dummy;
    }

    public List<String> getSuffix() {
        return suffix;
    }

    public void setSuffix(List<String> suffixes) {
        this.suffix = suffixes;
    }
}
