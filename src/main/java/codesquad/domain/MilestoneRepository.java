package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
	Iterable<Milestone> findByDeleted(boolean deleted);
	Milestone findBySubject(String subject);
}
