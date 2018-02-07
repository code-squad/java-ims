package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity{
    private String originalFileName;
    private String savedFileName;

    public Attachment() {
    }

    public Attachment(String originalFileName, String savedFileName) {
        this(0L, originalFileName, savedFileName);
    }

    public Attachment(long id, String originalFileName, String savedFileName) {
        super(id);
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }
}
