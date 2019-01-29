package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Attachment extends AbstractEntity {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_issue"))
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_writer"))
    private User writer;

    @Size(max = 260)
    String originalFileName;

    @Size(max = 32)
    String storedFileName;

    String path;

    public Attachment() {

    }

    public Attachment(Issue issue, String originalFileName, String storedFileName, String path) {
        this.issue = issue;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.path = path;
    }

    public void writerBy(User loginUser) {
        this.writer = loginUser;
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

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "issue=" + issue +
                ", writer=" + writer +
                ", originalFileName='" + originalFileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
