package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAll();
    Optional<Attachment> findById(long id);

}
