package codesquad.domain;

import codesquad.CannotDeleteException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Comments {

    @JsonIgnore
    @OneToMany(mappedBy = "issue")
    @Where(clause = "deleted = false")
    private List<Comment> comments = new ArrayList<>();

    public Comments() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<DeleteHistory> delete(User loginUser){
        return comments.stream().map(comment -> comment.delete(loginUser)).collect(Collectors.toList());
    }
}
