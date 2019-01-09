package codesquad.web;

import codesquad.service.FileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class ImageController {
    private static final Logger log = getLogger(ImageController.class);

    @Resource(name = "fileService")
    private FileService fileService;

    @GetMapping(value = "/image/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable String fileName) throws IOException {
        File image = fileService.serveFile(fileName);
        log.debug("image name : {}", image.getName());
        InputStream in = new FileInputStream(image);
        return IOUtils.toByteArray(in);
    }
}
