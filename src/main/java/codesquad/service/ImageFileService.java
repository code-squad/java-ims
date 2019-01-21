package codesquad.service;

import codesquad.domain.ImageFile;
import codesquad.domain.ImageFileRepository;
import codesquad.domain.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageFileService {
    private static final Logger log = LogManager.getLogger(ImageFileService.class);

    @Value("${users.img.path}")
    private String path;

    @Value("${users.img.path.defaultUserImg}")
    private String defaultImg;

    @Resource(name = "imageFileRepository")
    private ImageFileRepository imageFileRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public File findById(long id) {
        Long imgid = userRepository.findById(id).get().getImg().getId();
        log.debug("dzdz : {} " , imgid);
        Optional<ImageFile> imageFile = imageFileRepository.findById(imgid);
        log.debug("image : {} ",imageFile.isPresent());
        if (!imageFile.isPresent()) {
            log.debug("디폴트이미지");
            return new File(defaultImg);
        }
        String imgPath = path + "/" + imageFile.get().getName();
        log.debug("다 나오는가 : {} ",imageFile.get().getName());
        return new File(imgPath);
    }

    @Transactional
    public ImageFile add(MultipartFile pic) throws IOException {
//        if (!pic.isEmpty()) {
            String fileName =  UUID.randomUUID().toString();
            Files.copy(pic.getInputStream(), Paths.get(path,fileName), StandardCopyOption.REPLACE_EXISTING);
        log.debug("나는 파일명: {}",pic.getOriginalFilename());

        return imageFileRepository.save(new ImageFile(pic.getOriginalFilename(),fileName));
    }
}
