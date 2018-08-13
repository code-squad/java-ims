package codesquad.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Labels {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Label> labels = new ArrayList<>();

    public void addLabel(Label label) {
        labels.add(label);
    }
}
