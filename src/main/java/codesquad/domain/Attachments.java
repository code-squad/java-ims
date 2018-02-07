package codesquad.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;

@Embeddable
public class Attachments {

    @OneToMany
    @JoinColumn(name="id")
    @OrderBy("id ASC")
    List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    @Override
    public String toString() {
        return "Attachments{" +
                "attachments=" + attachments +
                '}';
    }
}
