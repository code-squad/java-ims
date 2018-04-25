package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
	Attachment findByPath(String path);
}