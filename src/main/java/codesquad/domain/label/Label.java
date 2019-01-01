package codesquad.domain.label;

import support.domain.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Label extends AbstractEntity {

    @Embedded
    private LabelBody labelBody;


}
