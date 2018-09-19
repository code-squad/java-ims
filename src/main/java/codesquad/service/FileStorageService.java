package codesquad.service;

import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import codesquad.domain.PathMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Resource(name = "fileStorageRepository")
    private FileStorageRepository fileStorageRepository;

    @Resource(name = "pathMaker")
    private PathMaker pathMaker;

    public FileInfo getOne(Long id) {
        return fileStorageRepository.getOne(id);
    }

    public List<FileInfo> getFiles(Long issueId) {
        return fileStorageRepository.findByIssueId(issueId);
    }

    public FileInfo store(MultipartFile file, Long issueId) {
        return saveFile(file, saveFileInfo(file, issueId));
    }

    public FileInfo saveFile(MultipartFile file, FileInfo fileInfo) {
        try {
            Path dirPathWithRoot = Files.createDirectories(Paths.get(fileInfo.getDirPathWithRoot(pathMaker)));
            log.debug("result path : {}", dirPathWithRoot);
            String fullPath = fileInfo.getFullPath(pathMaker);
            log.debug("fullpath : {}", fullPath);
            Files.copy(file.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    public FileInfo saveFileInfo(MultipartFile file, Long issueId) {
        FileInfo fileInfo = new FileInfo(file, pathMaker.makeRandomDirPath(), issueId);

        while (true) {
            try {
                fileStorageRepository.save(fileInfo);
                break;
            } catch (RuntimeException e) { // 파일 이름 중복된 것이 있을 때 발생
                if (e.getMessage().contains("ConstraintViolationException")) {
                    fileInfo.addNumberToFilename();
                }
                log.debug("updated fileInfo : {}, {}", fileInfo.getName(), fileInfo.getDirPath());
            }
        }
        return fileInfo;
    }
}
