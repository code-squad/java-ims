package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Comments {

    @OneToMany(mappedBy = "issue", cascade = CascadeType.PERSIST)
    @Where(clause = "deleted = false")
    private List<Comment> comments;

    public Comments() {
        comments = new ArrayList<>();
    }

    public Comments add(Comment comment) {
        comments.add(comment);
        return this;
    }

    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }
}
