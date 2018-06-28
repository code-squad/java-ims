package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AttachmentInfo extends AbstractEntity{

    @Column
    private String contentType;

    @Column
    private Long fileSize;

    @Column
    private String fileName;

    @Column
    private String fileUuid;

    @Column
    private String fileDirectory;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachmentInfo_issue"))
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachmentInfo_owner"))
    private User Owner;

    public AttachmentInfo() {

    }

    public AttachmentInfo(String contentType, Long fileSize, String fileName, String fileUuid, String fileDirectory, Issue issue, User owner) {
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileUuid = fileUuid;
        this.fileDirectory = fileDirectory;
        this.issue = issue;
        Owner = owner;
    }

    public AttachmentInfo(long id, String contentType, Long fileSize, String fileName, String fileUuid, String fileDirectory, Issue issue, User owner) {
        super(id);
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileUuid = fileUuid;
        this.fileDirectory = fileDirectory;
        this.issue = issue;
        Owner = owner;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public AttachmentInfo setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
        return this;
    }

    public User getOwner() {
        return Owner;
    }

    public AttachmentInfo setOwner(User owner) {
        Owner = owner;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public AttachmentInfo setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public AttachmentInfo setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AttachmentInfo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileUuid() {
        return fileUuid;
    }

    public AttachmentInfo setFileUuid(String fileUuid) {
        this.fileUuid = fileUuid;
        return this;
    }

    public Issue getIssue() {
        return issue;
    }

    public AttachmentInfo setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AttachmentInfo that = (AttachmentInfo) o;
        return Objects.equals(contentType, that.contentType) &&
                Objects.equals(fileSize, that.fileSize) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileUuid, that.fileUuid) &&
                Objects.equals(fileDirectory, that.fileDirectory) &&
                Objects.equals(issue, that.issue) &&
                Objects.equals(Owner, that.Owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), contentType, fileSize, fileName, fileUuid, fileDirectory, issue, Owner);
    }

    @Override
    public String toString() {
        return "AttachmentInfo{" +
                "contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", fileUuid='" + fileUuid + '\'' +
                ", fileDirectory='" + fileDirectory + '\'' +
                ", issue=" + issue +
                ", Owner=" + Owner +
                '}';
    }
}
