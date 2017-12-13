package codesquad.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyFilePathRepository extends JpaRepository<ReplyFilePath, Long>{
	public List<ReplyFilePath> findAllByReply(Reply reply);
}