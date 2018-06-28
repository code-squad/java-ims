package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

public enum Label {

    LABEL1("담당자 미할당", 1L),
    LABEL2("마일스톤 미지정", 2L),
    LABEL3("이슈 처리 중", 3L),
    LABEL4("승인 대기 중", 4L),
    LABEL5("이슈 처리 완료", 5L);

    private String status;
    private Long id;

    Label(String status, Long id) {
        this.status = status;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public static Label of(Long id) {
        return Arrays.stream(values()).filter(l -> l.id == id).findFirst().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public String toString() {
        return "Label{" +
                "status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}
