package codesquad.service;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import support.converter.AttachmentNameConverter;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    private FileManager fileManager;

    @Resource(name = "localFileManager")
    public AttachmentService setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
        return this;
    }

    public Issue upload(User loginUser, Issue issue, MultipartFile file) throws IOException {
        String savedFileName = fileManager.upload(file, AttachmentNameConverter.convertName(file.getOriginalFilename()));
        Attachment attachment = new Attachment()
                .uploadBy(loginUser)
                .toIssue(issue)
                .setOriginName(file.getOriginalFilename())
                .setManageName(savedFileName);
        attachmentRepo.save(attachment);
        return issue;
    }

    public WritableResource download(User loginUser, Issue issue, Long id) throws IOException {
        Attachment attachment = attachmentRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return attachment.download(loginUser, issue, fileManager);
    }

    public Attachment findById(Long id) {
        return attachmentRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
