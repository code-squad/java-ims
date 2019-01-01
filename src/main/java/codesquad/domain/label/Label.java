package codesquad.domain.label;

import support.domain.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {

    @Embedded
    private LabelBody labelBody;

    public Label() {
    }

    public Label(LabelBody labelBody) {
        this.labelBody = labelBody;
    }

    public LabelBody getLabelBody() {
        return labelBody;
    }

    public boolean hasSameBody(LabelBody target) {
        return this.labelBody.equals(target);
    }

    public void setLabelBody(LabelBody labelBody) {
        this.labelBody = labelBody;
    }
}
