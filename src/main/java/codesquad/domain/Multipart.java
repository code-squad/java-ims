package codesquad.domain;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import support.domain.AbstractEntity;

import javax.persistence.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Multipart extends AbstractEntity {
    private static final Logger log = getLogger(Multipart.class);
    private static final String UPLOAD_PATH = "C:\\Users\\User\\Documents\\workspace\\codesquad\\java-ims\\src\\main\\resources\\static\\fileUpload\\";

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_multipart_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_multipart_issue"))
    private Issue issue;

    @Column
    private String originalFilename;

    @Column
    private String saveName;

    public Multipart() {
    }

    public Multipart(User user, Issue issue, String file) {
        this.writer = user;
        this.issue = issue;
        this.originalFilename = file;
        this.saveName = UUID.randomUUID() + "_" + file;
    }

    public User getWriter() {
        return writer;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getSaveName() {
        return saveName;
    }

    public static String getUploadPath() {
        return UPLOAD_PATH;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Path add(User user, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_PATH + saveName);
        log.debug("path :~~~~~ {}", path.toString());
        return Files.write(path, bytes);
    }
}
