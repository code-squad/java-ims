package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storeFilename;

    @Column(nullable = false)
    private String filepath;

    public Attachment() {

    }

    public Attachment(String originalFilename, String storeFilename, String filepath) {
       this.originalFilename = originalFilename;
       this.storeFilename = storeFilename;
       this.filepath = filepath;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Attachment setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public String getStoreFilename() {
        return storeFilename;
    }

    public Attachment setStoreFilename(String storeFilename) {
        this.storeFilename = storeFilename;
        return this;
    }

    public String getFilepath() {
        return filepath;
    }

    public Attachment setFilepath(String filepath) {
        this.filepath = filepath;
        return this;
    }
}
