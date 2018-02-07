package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Attachment extends AbstractEntity{

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_writer"))
    private User uploader;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_issue"))
    private Issue issue;

    public Attachment() {

    }

    public Attachment(User uploader, String fileName, String path) {
        this.uploader = uploader;
        this.fileName = fileName;
        this.path = path;
    }

    public Attachment(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(path, that.path) &&
                Objects.equals(uploader, that.uploader) &&
                Objects.equals(issue, that.issue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), fileName, path, uploader, issue);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                ", uploader=" + uploader +
                ", issue=" + issue +
                '}';
    }
}
