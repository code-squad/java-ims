package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import support.converter.FileNameConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AvatarService {

    @Value("${file.avatar.path}")
    private String path;

    @Autowired
    private AttachmentRepository attachmentRepository;

    private static final Logger logger = getLogger(AvatarService.class);

    public Attachment createAvatar(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originFileName = multipartFile.getOriginalFilename();
            String fileName = FileNameConverter.convert(originFileName);
            InputStream is = multipartFile.getInputStream();
            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
            return attachmentRepository.save(new Attachment(originFileName, FileNameConverter.convert(originFileName), path));
        }
        return Attachment.DUMMY_ATTACHMENT;
    }
}
