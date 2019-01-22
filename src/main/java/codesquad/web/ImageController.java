package codesquad.web;


import codesquad.domain.ImageFile;
import codesquad.service.ImageFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/img")
public class ImageController {

    @Value("${users.img.path}")
    private String path;

    @Resource(name = "imageFileService")
    private ImageFileService imageFileService;

    @GetMapping(value = "/{id}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody
    byte[] userImgShow(@PathVariable long id) throws IOException {
        File file = imageFileService.findUserImg(id);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/download/{id}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody
    void answerImgShow(@PathVariable long id, HttpServletResponse response) throws IOException {
        ImageFile imageFile = imageFileService.findById(id);
        InputStream in = new FileInputStream(new File(path + "/" + imageFile.getName()));

        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description", "JSP Generated Data");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + java.net.URLEncoder.encode(imageFile.getOriginName(), "UTF-8").replaceAll("\\+", "\\ ") + "\"");

        IOUtils.copy(in, response.getOutputStream());
        response.flushBuffer();
    }
}
