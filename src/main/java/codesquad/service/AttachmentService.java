package codesquad.service;

import codesquad.EntityAlreadyExistsException;
import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;

@Service
public class AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Value("${Attachment.upload.directory.path}")
    private String location;

    @Transactional
    public Attachment add(MultipartFile file, User loginUser, Issue issue) {
        try {
            Attachment attachment = new Attachment(loginUser, issue, file.getOriginalFilename());
            String path = attachment.generateHashedPath(location);
            logger.debug("PATH: {}", path);
            File target = new File(path);
            if (target.exists()) {
                throw new EntityAlreadyExistsException();
            }
            target.createNewFile();
            file.transferTo(target);
            return attachmentRepository.save(attachment);

        } catch (IOException e) {
            logger.debug(e.getMessage());
            throw new IllegalStateException();
        } catch (EntityAlreadyExistsException e) {
            logger.debug(e.getMessage());
            return add(file, loginUser, issue);
        }
    }

    public Attachment findById(long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
