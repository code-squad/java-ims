package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Resource(name = "attachmentRepository")
    private AttachmentRepository attachmentRepository;

    public Attachment addAttachment(User loginUser, String fileName, String path){
        return attachmentRepository.save(new Attachment(loginUser, fileName, path));
    }

    public List<Attachment> findAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> findById(long id) {return attachmentRepository.findById(id);}
}
