package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AttachmentService {

    private static final String defaultFilePath = "./src/main/resources/attached-files/";
    @Autowired
    private AttachmentRepository attachmentRepository;

    private Attachment findOneAttachment(long id) throws FileNotFoundException {
         return Optional.ofNullable(attachmentRepository.findOne(id))
                 .orElseThrow(FileNotFoundException::new);
    }

    private String getFilePath(String filename) {
        return defaultFilePath + filename;
    }


    @Transactional
    public void upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String storedFilename = originalFilename + ":" + file.getSize();
        String filepath = getFilePath(storedFilename);

        File localFile = new File(filepath);
        FileCopyUtils.copy(file.getBytes(), localFile);

        attachmentRepository.save(new Attachment(originalFilename,
                storedFilename,
                localFile.getAbsolutePath()));
    }

    @Transactional
    public ResponseEntity<PathResource> download(long id) throws Exception {
        Attachment attachment = findOneAttachment(id);

        Path path = Paths.get(attachment.getFilepath());
        PathResource resource = new PathResource(path);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                + resource.getFilename().split(":")[0]);
        header.setContentLength(resource.contentLength());

        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
