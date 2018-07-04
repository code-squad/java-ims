package codesquad.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileSaver {
    private String uploadPath;

    public FileSaver(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public abstract String save(MultipartFile file, String fileManageName) throws IOException;

    Path getPath(String fileManageName) {
        return Paths.get(uploadPath + fileManageName);
    }

    protected String getSavePath() {
        return uploadPath;
    }
}
