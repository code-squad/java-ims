package codesquad.repository;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    Iterable<Issue> findByDeleted(boolean deleted);

}
