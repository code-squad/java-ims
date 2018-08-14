package codesquad.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Labels {

    @ManyToMany
    @JoinTable(name = "ISSUE_LABEL",
            joinColumns = @JoinColumn(name = "ISSUE_ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID"))
    private List<Label> labels = new ArrayList<>();

    public void addLabel(Label label) {
        labels.add(label);
    }
}
