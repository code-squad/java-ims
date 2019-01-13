package codesquad.domain;

import org.springframework.web.multipart.MultipartFile;
import support.converter.FileNameConverter;
import support.domain.AbstractEntity;
import javax.persistence.Entity;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Entity
public class Attachment extends AbstractEntity {

    public static final Attachment DUMMY_AVATAR = new AttachmentDummy(0L);

    private String originFileName;
    private String targetFileName;
    private String path;
    private boolean deleted = false;

    public Attachment() {

    }

    public Attachment(Long id) {

    }

    public Attachment(String originFileName, String targetFileName, String path) {
        this.originFileName = originFileName;
        this.targetFileName = targetFileName;
        this.path = path;
    }

    public Attachment(String originFileName, String targetFileName, String path, boolean deleted) {
        this(originFileName, targetFileName, path);
        this.deleted = true;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDummyAttachment() {
        return false;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Attachment saveAttachment(MultipartFile multipartFile, String path, boolean deleted) throws IOException {
        this.originFileName = multipartFile.getOriginalFilename();
        this.targetFileName = FileNameConverter.convert(this.originFileName);
        this.path = path;
        this.deleted = deleted;
        InputStream is = multipartFile.getInputStream();
        Files.copy(is, Paths.get(path + this.targetFileName), StandardCopyOption.REPLACE_EXISTING);
        return this;
    }

    private static class AttachmentDummy extends Attachment {
        public AttachmentDummy(Long id) {
            super(id);
        }
        @Override
        public boolean isDummyAttachment() {
            return true;
        }
    }
}
