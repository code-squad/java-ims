package codesquad.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/attachments")
public class AttchmentController {
    private static final Logger log = getLogger(AttchmentController.class);

    @PostMapping
    public String upload(MultipartFile file){
        log.debug("original file name: {}", file.getOriginalFilename());
        log.debug("contenttype: {}", file.getContentType());
        String originalFileName = file.getOriginalFilename();

        String filePath = "/Users/brad/Desktop/test";

        File dir = new File(filePath);
        if(!dir.exists()) {
            dir.mkdir();
        }

        String fileFullPath = filePath +  "/" + originalFileName;

        try {
            file.transferTo(new File(fileFullPath));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/";
    }
}
