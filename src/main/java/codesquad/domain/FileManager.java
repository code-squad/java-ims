package codesquad.domain;

import org.springframework.core.io.WritableResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class FileManager {
    private String uploadPath;

    public FileManager(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public abstract String upload(MultipartFile file, String fileManageName) throws IOException;

    public abstract WritableResource download(String fileManageName);

    protected String getSavePath() {
        return uploadPath;
    }
}
