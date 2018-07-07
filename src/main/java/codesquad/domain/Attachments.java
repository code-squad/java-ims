package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Attachments {

    @OneToMany(mappedBy = "issue")
    private List<Attachment> attachments;

    public Attachments() {
        attachments = new ArrayList<>();
    }

    @JsonIgnore
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Attachments add(Attachment attachment) {
        attachments.add(attachment);
        return this;
    }
}
