package codesquad;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "error")
@PropertySource("classpath:errorprops.properties")
public class ApplicationErrorProp {

    private Deny deny = new Deny();
    private String login;
    private String duplication;

    public class Deny {
        private String oneself;
        private String id;
        private String password;
        private String format;

        public String getOneself() {
            return oneself;
        }

        public void setOneself(String oneself) {
            this.oneself = oneself;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }
    }

    public Deny getDeny() {
        return deny;
    }

    public void setDeny(Deny deny) {
        this.deny = deny;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDuplication() {
        return duplication;
    }

    public void setDuplication(String duplication) {
        this.duplication = duplication;
    }
}
