package codesquad.domain.issue.answer;


import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {
    @Column
    private String originalFileName;

    @Column
    private String savedFileName;

    public Attachment() {
    }

    public Attachment(String originalFileName, String savedFileName) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public Attachment(long id, String originalFileName, String savedFileName) {
        super(id);
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }
}
