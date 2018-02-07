package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.AttachmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    private static final String UPLOAD_ROOT_DIR = "upload/";

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public void save(User loginUser, Long id, MultipartFile file) throws IOException {
        Issue issue = issueRepository.findOne(id);

        AttachmentDto attachmentDto = new AttachmentDto(file.getOriginalFilename());
        logger.debug("file name: {}", file.getOriginalFilename());

        attachmentDto.setSavedFileName(saveToStorage(id, file));

        Attachment attachment = attachmentDto.toAttachment();

        attachmentRepository.save(attachment);
        issue.addAttachment(loginUser, attachment);
        logger.debug("saved file name: {}", attachment.getSavedFileName());
    }

    private String saveToStorage(Long id, MultipartFile file) throws IOException {
        String pathAndFileName = getFileNameWithPath(file, id);
        logger.debug("path: {}", pathAndFileName);
        String[] fileNameAndExtension = pathAndFileName.split("\\.");
        logger.debug("array path: {}",  fileNameAndExtension);
        File newFile = new File(pathAndFileName);

        int i = 0;
        while (newFile.exists()) {
            i++;
            pathAndFileName = new StringBuilder(fileNameAndExtension[0])
                    .append("(")
                    .append(i)
                    .append(").")
                    .append(fileNameAndExtension[1]).toString();
            newFile = new File(pathAndFileName);
        }

        newFile.getParentFile().mkdirs();
        FileCopyUtils.copy(file.getBytes(), newFile);

        return pathAndFileName;
    }

    private String getFileNameWithPath(MultipartFile file, Long id) {
        return new StringBuilder(UPLOAD_ROOT_DIR)
                .append(id)
                .append("/")
                .append(file.getOriginalFilename())
                .toString();
    }

    public Attachment findById(Long id) {
        return attachmentRepository.findOne(id);
    }
}
