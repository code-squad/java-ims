package codesquad.domain.milestone;

import codesquad.domain.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Iterable<Issue> findByDeleted(boolean deleted);
}
