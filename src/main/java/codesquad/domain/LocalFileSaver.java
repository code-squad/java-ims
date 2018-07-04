package codesquad.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component(value = "localFileSaver")
public class LocalFileSaver extends FileSaver {

    public LocalFileSaver(@Value("${file.save.local}") String uploadPath) {
        super(uploadPath);
    }

    @Override
    public String save(MultipartFile file, String fileManageName) throws IOException {
        File saveFile = new File(getSavePath() + fileManageName);
        file.transferTo(saveFile);
        return fileManageName;
    }
}
