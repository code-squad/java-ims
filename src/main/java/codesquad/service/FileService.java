package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.issue.File;
import codesquad.domain.issue.FileRepository;
import codesquad.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    @Value("${file.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public File upload(User loginUser, MultipartFile uploadedFile) throws IOException {
        if (loginUser.isGuestUser()) throw new UnAuthorizedException("로그인이 필요 합니다.");
        byte[] bytes = uploadedFile.getBytes();
        Path path = Paths.get(uploadPath + uploadedFile.getResource().hashCode() + uploadedFile.getOriginalFilename());
        Files.write(path, bytes);
        File file = new File(uploadedFile.getOriginalFilename(), path.getFileName().toString(), path.toString(), loginUser);
        return fileRepository.save(file);
    }

    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public File findFile(long id) {
        return fileRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);
    }
}
