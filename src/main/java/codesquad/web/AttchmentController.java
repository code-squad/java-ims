package codesquad.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import codesquad.domain.Attchment;
import codesquad.service.AttchmentService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/attachments")
public class AttchmentController {
    public static final Logger logger = LoggerFactory.getLogger(AttchmentController.class);

    @Autowired
    private AttchmentService attchmentService;

    @PostMapping("")
    public String upload(MultipartFile file) throws Exception {
        logger.debug("original file name : {}", file.getOriginalFilename());
        logger.debug("contenttype : {}", file.getContentType());
        attchmentService.add(file);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
        Attchment attchment = attchmentService.download(id);
        logger.info("attchment info : {}", attchment.toString());
         Path path = Paths.get(attchment.getPath());
         PathResource resource = new PathResource(path);
         HttpHeaders header = new HttpHeaders();
         header.setContentType(MediaType.parseMediaType(attchment.getContentType()));
         header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attchment.getFileName());
         header.setContentLength(resource.contentLength());
         logger.info("header info : {}", header.toString());
         return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
    }
}
