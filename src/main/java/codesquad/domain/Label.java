package codesquad.domain;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

public enum Label {
    JAVA(1L), PYTHON(2L), CODESQUAD(3L);

    private long id;

    Label(long id) {
        this.id = id;
    }

    public static Label getLabel(long id) {
        return Arrays.stream(Label.values())
                .filter(label -> label.id == id)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }
}
