package codesquad.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findByDeleted(boolean deleted);

    List<Comment> findByIssue(long issueId);
}
