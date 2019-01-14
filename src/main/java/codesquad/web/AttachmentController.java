package codesquad.web;

import codesquad.ApplicationConfigurationProp;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationConfigurationProp applicationConfigurationProp;

    @GetMapping(value = "/thumbnail/{target}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<PathResource> thumbnailDownload(@PathVariable String target) {
        Path path = Paths.get(applicationConfigurationProp.getPath() + "/" + target);
        PathResource resource = new PathResource(path);
        return new ResponseEntity<PathResource>(resource, HttpStatus.OK);
    }
}
