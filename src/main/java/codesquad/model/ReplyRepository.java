package codesquad.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	public List<Reply> findAllByIssue(Issue issue);
}