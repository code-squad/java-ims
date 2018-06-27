package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Attachment add(Attachment attachment, User loginUser, Issue issue) {
        attachment.saveFile(loginUser, issue);

        return attachmentRepository.save(attachment);
    }

    public Attachment findById(long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
