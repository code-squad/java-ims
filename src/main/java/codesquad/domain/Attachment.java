package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Entity
public class Attachment extends AbstractEntity {

    private String path;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, unique = true)
    private String savedName;

    private long size;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_to_saver"))
    private User saver;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_to_issue"))
    private Issue issue;

    public Attachment() {
    }

    public Attachment(String originalName, long size, User saver, String path) {
        this.originalName = originalName;
        this.savedName = randomName(originalName);
        this.size = size;
        this.saver = saver;
        this.path = path;
    }

    public File save() {
        return new File(path + File.separator + savedName);
    }

    public Path getPath() {
        return Paths.get(path + File.separator + savedName);
    }

    public String randomName(String originalName) {
        return UUID.randomUUID().toString().replace("-", "") + parseExtension(originalName);
    }

    public String parseExtension(String file) {
        return file.substring(file.lastIndexOf("."));
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
