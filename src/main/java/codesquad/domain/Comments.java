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

    @OneToMany(mappedBy = "issue")
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public Comments() {
    }

    public Comments(List<Comment> comments){
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @JsonIgnore
    public List<DeleteHistory> delete(User loginUser){
//        List<DeleteHistory> deleteHistories = new ArrayList<>();
//        for (Comment comment : comments) {
//            DeleteHistory deleteHistory = comment.delete(loginUser);
//            deleteHistories.add(deleteHistory);
//        }
//        return deleteHistories;
        return comments.stream().map(comment -> comment.delete(loginUser)).collect(Collectors.toList());
    }
}
