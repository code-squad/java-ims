package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

public enum Label {
    @JsonProperty("Java")
    JAVA(1L),

    @JsonProperty("Python")
    PYTHON(2L),

    @JsonProperty("Codesquad")
    CODESQUAD(3L);

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
