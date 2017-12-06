package codesquad.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	public List<Issue> findAllByOrderByIdDesc();
}
