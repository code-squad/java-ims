package codesquad.web;


import codesquad.service.ImageFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;

@Controller
@RequestMapping("/img")
public class ImageController {

    @Resource(name = "imageFileService")
    private ImageFileService imageFileService;

    @GetMapping(value = "/{id}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody
    byte[] show(@PathVariable long id) throws IOException {
        File file = imageFileService.findById(id);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }
}
