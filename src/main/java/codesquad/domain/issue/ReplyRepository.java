package codesquad.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Iterable<Reply> findByDeleted(boolean deleted);
}
