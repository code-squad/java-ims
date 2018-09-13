package codesquad.service;

import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.security.Permission;
import java.util.Random;

@Service
public class FileStorageService {
    private static final Logger log =  LoggerFactory.getLogger(FileStorageService.class);

    @Resource(name = "fileStorageRepository")
    private FileStorageRepository fileStorageRepository;

    public FileInfo storeFile(MultipartFile file) {
        Path dirPath = Paths.get(String.valueOf(Math.abs(new Random().nextLong())));
        FileInfo fileInfo = new FileInfo(file, dirPath);
        try {
            Path resultPath = Files.createDirectories(dirPath);
            Path fullDirPath = FileInfo.rootLocation.resolve(resultPath);
            fullDirPath = Files.createDirectory(fullDirPath);
            File dir = new File(fullDirPath.toString());
            dir.setWritable(true);
            log.debug("fullDirPath : {}", fullDirPath.toString());
            log.debug("result path : {}", resultPath.toString());

            log.debug("fileInfo Path : {}", fileInfo.getPath());
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, fileInfo.getPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    public FileInfo storeFileInfo(FileInfo fileInfo) {
        return fileStorageRepository.save(fileInfo);
    }
}
