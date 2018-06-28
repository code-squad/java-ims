package codesquad.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.UUID;

@Service
public class AttachmentService {
//
//    @Resource(name = "attachmentRepository")
//    private AttachmentRepository attachmentRepository;

    private final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    public void store(MultipartFile file, Long issueId) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }

//        file.getBytes();
//        file.getContentType();
//        file.getSize()
        byte[] bytes = file.getBytes();
        File dir = new File("src/main/resources/storage");
        makeDirectories(dir);
        File serverFile = convertServerFile(file, dir);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        log.info("Server File Location=" + serverFile.getAbsolutePath());


    }

    private void makeDirectories(File dir) {
        if (!dir.exists()) dir.mkdirs();
    }

    private File convertServerFile(MultipartFile file, File dir) throws UnsupportedEncodingException {
        String origName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");
        String ext = origName.substring(origName.lastIndexOf('.'));
        String saveFileName = getUuid() + ext;

        // Create the file on server
        return new File(dir.getAbsolutePath() + File.separator + saveFileName);
    }

    //uuid생성
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
