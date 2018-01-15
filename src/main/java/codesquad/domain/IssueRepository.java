package codesquad.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	Optional<Issue> findBySubject(String subject);

}
