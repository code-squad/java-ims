package codesquad.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileManager extends FileManager {

    public LocalFileManager(@Value("${file.save.local}") String uploadPath) {
        super(uploadPath);
    }

    @Override
    public String upload(MultipartFile file, String fileManageName) throws IOException {
        File saveFile = new File(getSavePath() + fileManageName);
        file.transferTo(saveFile);
        return fileManageName;
    }

    @Override
    public WritableResource download(String fileManageName) {
        Path path = Paths.get(getSavePath() + fileManageName);
        return new PathResource(path);
    }
}
