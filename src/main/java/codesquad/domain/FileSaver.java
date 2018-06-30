package codesquad.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSaver extends Savable {
    String save(MultipartFile file, String fileManageName) throws IOException;

    Path getPath(String fileManageName);
}
