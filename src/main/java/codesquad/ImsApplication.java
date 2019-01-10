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


/**
 * Todo : Step4 ~ing
 * 1. html 자원들 중복 제거하면서 스타일이 제대로 안나오는 부분이 있다, 추후 중복 제거 다시해서 레이아웃이 제대로 나오도록
 * 2. 회원가입/가입정보수정 시 유효하지 않은 값 입력하고 submit 시 어떤 경우에 alert, 어떤 경우에 오류페이지 나오는지 확인 후 개선
 */
