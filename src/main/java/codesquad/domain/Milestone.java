package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Milestone extends AbstractEntity implements UriGeneratable{


    @Override
    public String generateUrl() {
        return String.format("/milstones/", getId());
    }
}
