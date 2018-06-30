package codesquad.service;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import support.service.AttachmentNameConverter;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    @Resource(name = "localFileSaver")
    private FileSaver fileSaver;

    public Issue upload(User loginUser, Issue issue, MultipartFile file) throws IOException {
        String savedFileName = fileSaver.save(file, AttachmentNameConverter.convertName(file.getOriginalFilename()));
        Attachment attachment = new Attachment()
                .uploadBy(loginUser)
                .toIssue(issue)
                .setOriginName(file.getOriginalFilename())
                .setManageName(savedFileName);
        attachmentRepo.save(attachment);
        return issue;
    }

    public PathResource download(User loginUser, Issue issue, Long id) {
        return attachmentRepo.findById(id).map(attachment -> attachment.findPathResource(loginUser, issue, fileSaver)).orElseThrow(EntityNotFoundException::new);
    }

    public Attachment findById(Long id) {
        return attachmentRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
