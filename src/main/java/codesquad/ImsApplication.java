package codesquad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImsApplication.class, args);
    }
}

// TODO : html 자원들 중복 제거하면서 스타일이 제대로 안나오는 부분이 있다, 추후 중복 제거 다시해서 레이아웃이 제대로 나오도록...!