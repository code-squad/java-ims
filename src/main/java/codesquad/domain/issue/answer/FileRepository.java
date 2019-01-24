package codesquad.domain.issue.answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Attachment, Long> {
}
