package codesquad.service;

import codesquad.domain.DirectoryPathMaker;
import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Resource(name = "fileStorageRepository")
    private FileStorageRepository fileStorageRepository;

    public List<FileInfo> findAll() {
        return fileStorageRepository.findAll();
    }

    public FileInfo store(MultipartFile file) {
        return saveFile(file, saveFileInfo(file));
    }

    private FileInfo saveFile(MultipartFile file, FileInfo fileInfo) {
        try {
            Path resultPath = Files.createDirectories(fileInfo.getDirPath());
            log.debug("result path : {}", resultPath);

            Files.copy(file.getInputStream(), fileInfo.getPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    private FileInfo saveFileInfo(MultipartFile file) {
        FileInfo fileInfo = new FileInfo(file, new DirectoryPathMaker().makePath());

        while(true) {
            try {
                fileStorageRepository.save(fileInfo);
                break;
            } catch (Exception e) { // 파일 이름 중복된 것이 있을 때 발생
                fileInfo.addNumberToFilename();
                log.debug("updated fileInfo : {}, {}", fileInfo.getName(), fileInfo.getPath());
            }
        }
        return fileInfo;
    }
}
