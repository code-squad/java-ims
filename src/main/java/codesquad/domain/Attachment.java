package codesquad.domain;

import codesquad.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {
    private static final Logger log = LoggerFactory.getLogger(Attachment.class);

    @Column(nullable = false)
    String fileName;

    @Column(nullable = false)
    String filePath;

    boolean deleted = false;

    public Attachment() {

    }

    public Attachment(String fileName, String filePath) {
        this(0L, fileName, filePath);
    }

    public Attachment(long id, String fileName, String filePath) {
        super(id);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSameName(String fileName) {
        return this.fileName.equals(fileName);
    }

}
