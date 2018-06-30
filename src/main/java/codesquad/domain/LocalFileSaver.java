package codesquad.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component(value = "localFileSaver")
public class LocalFileSaver implements FileSaver {

    @Override
    public String save(MultipartFile file, String fileManageName) throws IOException {
        File saveFile = new File(getSavePath() + fileManageName);
        file.transferTo(saveFile);
        return fileManageName;
    }

    @Override
    public Path getPath(String fileManageName) {
        return Paths.get(getSavePath() + fileManageName);
    }

    @Override
    public String getSavePath() {
        return "/Users/imjinbro/Desktop/codesquad/workspace/jwp/java-ims/src/main/resources/upload/";
    }
}
