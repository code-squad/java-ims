package codesquad.service;

import codesquad.domain.Attchment;
import codesquad.domain.AttchmentRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AttchmentService {
    private static final Logger log =  LoggerFactory.getLogger(AttchmentService.class);

    @Resource(name = "attchmentRepository")
    private AttchmentRepository attchmentRepository;

    public Attchment add(MultipartFile file) throws Exception {
        log.debug("original file name on service : {}", file.getOriginalFilename());
        log.debug("contenttype on service : {}", file.getContentType());
        if (!file.isEmpty()) {
            byte [] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            File target = new File("./src/fileTest", file.getOriginalFilename());
            FileCopyUtils.copy(file.getBytes(), target);
            Attchment attchment = new Attchment(file.getOriginalFilename(), file.getContentType());
            return attchmentRepository.save(new Attchment(file.getOriginalFilename(), file.getContentType()));
        }
        throw new FileUploadBase.IOFileUploadException("파일을 업로드 할 수 없습니다.");
    }
}
