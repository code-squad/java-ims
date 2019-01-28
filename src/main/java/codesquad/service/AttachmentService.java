package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service
public class AttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    @Resource(name = "attachmentRepository")
    private AttachmentRepository attachmentRepository;

    public Attachment add(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Transactional
    public Attachment save(User loginUser, Attachment attachment) {
        attachment.loadedBy(loginUser);
        return add(attachment);
    }

    public Attachment findById(long id) {
        return attachmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
