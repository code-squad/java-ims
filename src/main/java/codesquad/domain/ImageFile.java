package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class ImageFile extends AbstractEntity {

    private String originName;
    private String name;

    public ImageFile() {
    }

    public ImageFile(String originName, String name) {
        this.originName = originName;
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
