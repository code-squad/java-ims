package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Optional;

@Service
public class AttachmentService {

    private static String defaultFilePath = "/Users/woowahan/Documents/codesquad-woowahan/java-ims/src/main/resources/attached-files/";

    @Autowired
    private AttachmentRepository attachmentRepository;

    public Attachment findOneAttachment(long id) throws FileNotFoundException {
         return Optional.ofNullable(attachmentRepository.findOne(id))
                 .orElseThrow(FileNotFoundException::new);
    }

    public String getFilePath(String filename) {
        return defaultFilePath + filename;
    }

    public void saveOneAttachment(String filename, String filepath) {
        attachmentRepository.save(new Attachment(filename, filepath));
    }
}
