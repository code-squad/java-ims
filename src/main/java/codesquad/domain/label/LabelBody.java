package codesquad.domain.label;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@Embeddable
public class LabelBody {

    @Size(min = 3, max = 30)
    @Column(length = 30, nullable = false)
    private String name;

    @Size(min = 5)
    @Lob
    private String explanation;

    public LabelBody() {
    }

    public LabelBody(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
