package codesquad.domain;

import org.slf4j.Logger;
import support.converter.FileNameConverter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Embeddable
public class Attachment {

    private static final String SPLIT_STANDARD = ".";

    private static final String PATTERN = "^\\S+.(?i)(png|jpg|jpeg|gif)$";

    private static final Logger logger = getLogger(Attachment.class);

    @Column
    @Pattern(regexp = PATTERN)
    private String originFileName;

    @Column(unique = true)
    private String targetFileName;

    @Column
    private String path;

    public Attachment() {

    }

    public Attachment(String originFileName, String targetFileName, String path) {
        this.originFileName = originFileName;
        this.targetFileName = targetFileName;
        this.path = path;
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

    public Attachment createAttachment(InputStream is) throws IOException {
        Files.copy(is, createPath(path, targetFileName), StandardCopyOption.REPLACE_EXISTING);

        return this;
    }

    public Path createPath(String path, String targetFileName) {
        return Paths.get(String.format("%s/%s", path, targetFileName));
    }

    public static Attachment of(String originFileName, String path) {
        return new Attachment(originFileName, UUID.randomUUID().toString(), path);
    }

    public static boolean extensionCheck(String originFileName, List<String> suffix) {
        if(!originFileName.contains(SPLIT_STANDARD)) {
            return false;
        }

        if(!suffix.contains(obtainSuffix(originFileName))) {
            return false;
        }

        return true;
    }

    public static String obtainSuffix(String fileName) {
        String[] splited = fileName.split("\\.");
        return splited[splited.length - 1];
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "originFileName='" + originFileName + '\'' +
                ", targetFileName='" + targetFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
