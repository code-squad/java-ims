package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	Iterable<Issue> findByDeleted(boolean deleted);
	Issue findBySubject(String subject);
}
