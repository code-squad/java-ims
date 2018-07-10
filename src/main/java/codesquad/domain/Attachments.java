package codesquad.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Attachments {
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Attachment> attachments;

    public Attachments() {
        attachments = new ArrayList<>();
    }

    public List<Attachment> getFiles() {
        return attachments;
    }

    public void add(Attachment attachment) {
        attachments.add(attachment);
    }

    public boolean isEmpty() {
        return attachments.isEmpty();
    }

    @Override
    public String toString() {
        return "Attachments{" +
                "attachments=" + attachments +
                '}';
    }
}