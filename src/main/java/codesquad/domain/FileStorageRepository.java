package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileInfo, Long> {
        List<FileInfo> findByIssueId(Long issueId);
}
