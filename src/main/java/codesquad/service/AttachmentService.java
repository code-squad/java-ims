package codesquad.service;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.UUID;

@Service
public class AttachmentService {

    @Resource(name = "attachmentInfoRepository")
    private AttachmentInfoRepository attachmentInfoRepository;

    private final Logger log = LoggerFactory.getLogger(AttachmentService.class);
    private final String STORAGE_PATH = "src/main/resources/storage";

    public void store(MultipartFile file, Issue issue, User loginUser) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }
        byte[] bytes = file.getBytes();

        File dir = new File(STORAGE_PATH);
        makeDirectories(dir);

        String fileUuid = convertToUUID(file, dir);
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileUuid);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        AttachmentInfo attachmentInfo = new AttachmentInfo()
                .setContentType(file.getContentType())
                .setFileName(file.getOriginalFilename())
                .setFileUuid(fileUuid)
                .setFileSize(file.getSize())
                .setIssue(issue)
                .setOwner(loginUser);

        attachmentInfoRepository.save(attachmentInfo);
        log.info("Server File Location=" + serverFile.getAbsolutePath());
    }

    private void makeDirectories(File dir) {
        if (!dir.exists()) dir.mkdirs();
    }

    private String convertToUUID(MultipartFile file, File dir) throws UnsupportedEncodingException {
        String origName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");
        String ext = origName.substring(origName.lastIndexOf('.'));
        String saveFileName = getUuid() + ext;

        // Create the file on server
        return saveFileName;
    }

    //uuid생성
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
