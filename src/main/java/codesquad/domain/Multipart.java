package codesquad.domain;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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

    private String uploadPath;

    public Multipart() {
    }

    public Multipart(User user, Issue issue, String file, String uploadPath) {
        this.writer = user;
        this.issue = issue;
        this.originalFilename = file;
        this.saveName = UUID.randomUUID() + "_" + file;
        this.uploadPath = uploadPath;
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

    public String getUploadPath() {
        return uploadPath;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Path add(User user, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + saveName);
        log.debug("path :~~~~~ {}", path.toString());
        return Files.write(path, bytes);
    }
}
