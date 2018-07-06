package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.springframework.core.io.WritableResource;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Attachment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_user"))
    private User uploader;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_issue"))
    private Issue issue;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String manageName;

    public Attachment uploadBy(User loginUser) {
        if (isNull(uploader)) {
            uploader = loginUser;
        }
        return this;
    }

    public Attachment toIssue(Issue issue) {
        if (isNull(this.issue)) {
            this.issue = issue;
            issue.addAttachment(this);
        }
        return this;
    }

    public Attachment setOriginName(String originName) {
        if (isNull(this.originName)) {
            this.originName = originName;
        }
        return this;
    }

    public Attachment setManageName(String manageName) {
        if (isNull(this.manageName)) {
            this.manageName = manageName;
        }
        return this;
    }

    public User getUploader() {
        return uploader;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getOriginName() {
        return originName;
    }

    public String getManageName() {
        return manageName;
    }

    private boolean isNull(Object target) {
        return target == null;
    }

    public WritableResource download(User user, Issue target, FileManager fileManager) {
        if (!uploader.equals(user) || !issue.equals(target)) {
            throw new UnAuthorizedException();
        }
        return fileManager.download(manageName);
    }
}
