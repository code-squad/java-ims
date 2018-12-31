package codesquad.domain;

import codesquad.domain.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    Iterable<Issue> findByDeleted(boolean deleted);
}
