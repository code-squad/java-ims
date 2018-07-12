package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Attchment extends AbstractEntity {

    @Column
    private String fileName;

    @Column
    private String contentType;

    @Column
    private String path;

    public Attchment() {}

    public Attchment(String fileName, String contentType) {
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public Attchment(String fileName, String contentType, String path) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.path = path;
    }

    public Attchment(long id, String fileName, String contentType, String path) {
        super(id);
        this.fileName = fileName;
        this.contentType = contentType;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Attchment{" +
                "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
