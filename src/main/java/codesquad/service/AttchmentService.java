package codesquad.service;

import codesquad.domain.Attchment;
import codesquad.domain.AttchmentRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Service
public class AttchmentService {
    private static final Logger log =  LoggerFactory.getLogger(AttchmentService.class);

    @Value("${spring.file.path}")
    private String path;

    @Autowired
    private AttchmentRepository attchmentRepository;

    public Attchment add(MultipartFile file) throws Exception {
        log.debug("original file name on service : {}", file.getOriginalFilename());
        log.debug("content-type on service : {}", file.getContentType());
        if (!file.isEmpty()) {
            File target = new File(getPath(), file.getOriginalFilename());
            log.info("target : {}", target.toString());
            file.transferTo(target);
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
