package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filepath;

    public Attachment() {

    }

    public Attachment(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public Attachment setFilename(String filename) {
        this.filename = filename;
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
