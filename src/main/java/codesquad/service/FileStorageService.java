package codesquad.service;

import codesquad.domain.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class FileStorageService {
    private static final Logger log =  LoggerFactory.getLogger(FileStorageService.class);

    public FileInfo storeFile(MultipartFile file) {

        FileInfo fileInfo = new FileInfo(file);
        Path location = FileInfo.rootLocation.resolve(String.valueOf(Math.abs(new Random().nextLong())));
        try {
            Files.createDirectories(location);
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, location.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }
}
