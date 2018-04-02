package codesquad.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
	Optional<Milestone> findBySubject(String subject);
}
