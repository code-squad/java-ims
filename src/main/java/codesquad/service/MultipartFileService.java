package codesquad.service;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MultipartFileService {
    private static final Logger log = getLogger(MultipartFileService.class);
    @Resource(name = "multipartRepository")
    private MultipartRepository multipartRepository;

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Transactional
    public Multipart add(User user, long issueId, MultipartFile file) throws IOException {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnknownError::new);
        Multipart multipart = new Multipart(user, issue, file.getOriginalFilename());
        Path path = multipart.add(user, file);
        file.transferTo(path);
        return multipartRepository.save(multipart);
    }

    public List<Multipart> findAll() {
        return multipartRepository.findAll();
    }

    public String findPath(long id) {
        Multipart multipart = multipartRepository.findById(id).orElseThrow(UnknownError::new);
        return multipart.getUploadPath() + multipart.getSaveName();
    }
}
