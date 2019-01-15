package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity implements MenuEntity {
    @Embedded
    private ContentsBody contentsBody;

    public Label() {
    }

    public Label(ContentsBody contentsBody) {
        this.contentsBody = contentsBody;
    }

    public static Label ofBody(ContentsBody contentsBody) {
        return new Label(contentsBody);
    }

    public String getSubject() {
        return contentsBody.getSubject();
    }

    public String getComment() {
        return this.contentsBody.getComment();
    }
}
