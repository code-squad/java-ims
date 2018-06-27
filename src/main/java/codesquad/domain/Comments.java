package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Comments {
    private static final Logger log = LoggerFactory.getLogger(Comments.class);

    @OneToMany(mappedBy = "issue", cascade = CascadeType.PERSIST)
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
