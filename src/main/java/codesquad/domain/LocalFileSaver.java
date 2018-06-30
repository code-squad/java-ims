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
    public String save(MultipartFile file, String fileName) throws IOException {
        File saveFile = new File(getSavePath() + fileName);
        file.transferTo(saveFile);
        return fileName;
    }

    @Override
    public Path getPath(String manageName) {
        return Paths.get(getSavePath() + manageName);
    }

    @Override
    public String getSavePath() {
        return "/Users/imjinbro/Desktop/codesquad/workspace/jwp/java-ims/src/main/resources/upload/";
    }
}
