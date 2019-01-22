package codesquad.repository;

import codesquad.domain.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone,Long> {
}
