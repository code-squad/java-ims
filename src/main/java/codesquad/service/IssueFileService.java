package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.issue.answer.Attachment;
import codesquad.domain.issue.answer.FileRepository;
import codesquad.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import support.util.FileUtils;

import java.io.File;
import java.io.IOException;

@Service
public class IssueFileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public Attachment upload(User loginUser, MultipartFile file) throws IOException {
        if (loginUser.isGuestUser()) {
            throw new UnAuthorizedException("로그인이 필요합니다.");
        }
        String savedFileName = FileUtils.uploadFile(file, uploadPath);
        Attachment attachment = new Attachment(file.getOriginalFilename(), savedFileName);
        return fileRepository.save(attachment);
    }
}
