package codesquad.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import support.domain.FileEntity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FileService {
    private static final Logger log = getLogger(FileService.class);

    @Value("${upload.path}")
    private String defaultLocation;

    public void upload(FileEntity fileEntity, MultipartFile file) throws IOException {
        String savedFileName = uploadFile(fileEntity.getOriginalFilename(), file.getBytes());
        fileEntity.changeFilename(savedFileName);
    }

    private String uploadFile(String originalFilename, byte[] in) throws IOException {
        UUID uid = UUID.randomUUID();
        String savedFileName = uid.toString() + "_" + originalFilename;
        File out = new File(defaultLocation, savedFileName);
        FileCopyUtils.copy(in, out);
        return savedFileName;
    }
}
