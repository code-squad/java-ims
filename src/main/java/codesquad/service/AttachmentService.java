package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class AttachmentService {

    @Resource(name = "attachmentRepository")
    private AttachmentRepository attachmentRepository;

    @Resource(name = "issueService")
    private IssueService issueService;

    @Value("${file.path}")
    private String path;

    @Transactional(rollbackFor = IOException.class)
    public Attachment upload(MultipartFile file, User loginUser, long issueId) throws IOException {
        Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), loginUser, path);
        attachment.setIssue(issueService.findById(issueId));
        file.transferTo(attachment.save());
        return attachmentRepository.save(attachment);
    }

    public Attachment download(long attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new);
    }
}
