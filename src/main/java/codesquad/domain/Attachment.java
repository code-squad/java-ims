package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Attachment extends AbstractEntity {
    private static final Logger log = LoggerFactory.getLogger(Attachment.class);

    @Column
    private String originalName;

    @Column
    private String convertedName;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_uploader"))
    private User uploader;

    public Attachment() {

    }

    public static Attachment convertedAttachment(MultipartFile file, String convertedName) {
        Attachment attachment = new Attachment();
        attachment.originalName = file.getOriginalFilename();
        attachment.convertedName = convertedName;
        return attachment;
    }

    public void loadedBy(User uploader) {
        this.uploader = uploader;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getConvertedName() {
        return convertedName;
    }

    public void setConvertedName(String convertedName) {
        this.convertedName = convertedName;
    }
}
