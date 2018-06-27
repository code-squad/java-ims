package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Entity
public class Attachment {
    private static final Logger logger = LoggerFactory.getLogger(Attachment.class);
    private static final String ABSOLUTE_PATH = "/Users/JaeP/Desktop/CodeSquad-lvl3/java-ims/src/main/resources/attachments/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Size(min = 1, max = 20)
    private String originalFileName;

    private String hashedFileName;

    private String path;


    public Attachment() {
    }

    public Attachment(String originalFileName) {
        this.originalFileName = originalFileName;
        this.hashedFileName = createHashedName();
    }


    public Attachment(User writer, Issue issue, String originalFileName, String path) {
        this.writer = writer;
        this.issue = issue;
        this.originalFileName = originalFileName;
        this.hashedFileName = createHashedName();
        this.path = path;
    }

    private String createHashedName() {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    public long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getHashedFileName() {
        return hashedFileName;
    }

    public void setHashedFileName(String hashedFileName) {
        this.hashedFileName = hashedFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", issue=" + issue +
                ", writer=" + writer +
                ", originalFileName='" + originalFileName + '\'' +
                ", hashedFileName='" + hashedFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String saveFile(User loginUser, Issue issue) {
        this.originalFileName = file.getOriginalFilename();
        this.hashedFileName = createHashedName();
        this.path = ABSOLUTE_PATH + hashedFileName;
        this.writer = loginUser;
        this.issue = issue;
        try {
            File target = new File(path);
            if (target.exists()) {
                return saveFile(loginUser, issue);
            }
            target.createNewFile();
            file.transferTo(target);
            return path;
        } catch (IOException e) {
            logger.debug(e.getMessage());
            throw new IllegalStateException();
        }
    }
}
