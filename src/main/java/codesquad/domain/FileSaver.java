package codesquad.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSaver {
    String save(MultipartFile file, String fileName) throws IOException;
}
