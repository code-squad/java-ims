package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Attachments {

    @OneToMany(mappedBy = "issue")
    @JsonIgnore
    private List<Attachment> attachments = new ArrayList<>();

    public List<Attachment> getAttachments() {
        return attachments;
    }
}
