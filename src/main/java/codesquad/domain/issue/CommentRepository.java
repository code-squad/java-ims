package codesquad.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findByDeleted(boolean deleted);
}
