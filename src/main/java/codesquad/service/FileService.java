package codesquad.service;

import codesquad.domain.user.ProfileImage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FileService {
    private static final Logger log = getLogger(FileService.class);

    @Value("${upload.path}")
    private String defaultLocation;

    public void upload(ProfileImage profileImage, MultipartFile file) throws IOException {
        String savedFileName = uploadFile(profileImage.getOriginalFileName(), file.getBytes());
        profileImage.setSavedFileName(savedFileName);
    }

    private String uploadFile(String originalFileName, byte[] in) throws IOException {
        String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        File out = new File(defaultLocation, savedFileName);
        FileCopyUtils.copy(in, out);
        return savedFileName;
    }
}
