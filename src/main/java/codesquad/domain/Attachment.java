package codesquad.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {

    public static final DummyAttachment DUMMY_ATTACHMENT = new DummyAttachment("dummy.png", "dummy.png");

    private String originFileName;
    private String targetFileName;
    private String path;

    public Attachment() {

    }

    public Attachment(String originFileName, String targetFileName, String path) {
        this.originFileName = originFileName;
        this.targetFileName = targetFileName;
        this.path = path;
    }

    public String getOrignFileName() {
        return originFileName;
    }

    public void setOrignFileName(String originFileName) {
        this.originFileName = originFileName;
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

    @Override
    public String toString() {
        return "Attachment{" +
                "orignFileName='" + originFileName + '\'' +
                ", targetFileName='" + targetFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Component
    private static class DummyAttachment extends Attachment {

        @Value("${file.avatar.path}")
        private static String path;

        public DummyAttachment(String originFileName, String targetFileName) {
            super(originFileName, targetFileName, path);
        }

        @Override
        public boolean isDummyAttachment() {
            return true;
        }
    }
}
