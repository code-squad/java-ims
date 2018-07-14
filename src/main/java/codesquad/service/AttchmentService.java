package codesquad.service;

import codesquad.domain.Attchment;
import codesquad.domain.AttchmentRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AttchmentService {
    private static final Logger log =  LoggerFactory.getLogger(AttchmentService.class);

    @Value("${spring.file.path}")
    private String path;

    @Autowired
    private AttchmentRepository attchmentRepository;

    public Attchment add(MultipartFile file) throws Exception {
        log.debug("original file name on service : {}", file.getOriginalFilename());
        log.debug("contenttype on service : {}", file.getContentType());
        if (!file.isEmpty()) {
            byte [] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            log.info("uploading directory : {}", path.toString());
            Files.write(path, bytes);
            File target = new File(getPath() + file.getOriginalFilename());
            FileCopyUtils.copy(file.getBytes(), target);
            return attchmentRepository.save(new Attchment(file.getOriginalFilename(), file.getContentType(), target.getPath()));
        }
        throw new FileUploadBase.IOFileUploadException("파일을 업로드 할 수 없습니다.");
    }

    public Attchment download(long id) {
        return attchmentRepository.findById(id).get();
    }

    public String getPath() {
        return path;
    }
}
