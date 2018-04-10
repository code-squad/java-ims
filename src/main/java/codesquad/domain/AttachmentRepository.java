package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>{
    List<Attachment> findByFileName(String fileName);
}
