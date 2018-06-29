package codesquad.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component(value = "localFileSaver")
public class LocalFileSaver implements FileSaver {
    private static final String PATH = "/Users/imjinbro/Desktop/codesquad/workspace/jwp/java-ims/src/main/resources/upload/";

    @Override
    public String save(MultipartFile file, String fileName) throws IOException {
        File saveFile = new File(PATH + fileName);
        file.transferTo(saveFile);
        return fileName;
    }
}
