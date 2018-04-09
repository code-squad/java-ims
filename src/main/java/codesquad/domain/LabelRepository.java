package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
	Iterable<Label> findByDeleted(boolean deleted);
	Label findBySubject(String subject);
}
