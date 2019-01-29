package codesquad.service;


import codesquad.domain.*;
import codesquad.exception.FileStorageException;
import codesquad.exception.MyFileNotFoundException;
import codesquad.exception.UnAuthorizedException;
import codesquad.property.FileStorageProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import support.utils.CommonUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AttachmentService {
    private static final Logger logger = getLogger(AttachmentService.class);
    private final Path fileStorageLocation;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        logger.debug("## AttachmentService : constructor");
        logger.debug("## AttachmentService : {}",  fileStorageLocation);

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }

    }

    @Transactional
    public String storeFile(long id, MultipartFile file, User loginUser) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.debug("## storeFile : fileName{}",  fileName);
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            logger.debug("## storeFile : {}",  targetLocation);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Issue issue = issueRepository.findById(id)
                    .orElseThrow(UnAuthorizedException::new);

            String storeName = CommonUtils.getRandomString();
            Attachment attachment = new Attachment(issue, fileName, storeName, targetLocation.toString());
            attachment.writerBy(loginUser);
            issue.addAttachment(attachment);

            logger.debug("## storeFile : {}",  attachment);
            attachmentRepository.save(attachment);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
