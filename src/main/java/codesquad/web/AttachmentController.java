package codesquad.web;

import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
	
	@Resource
	private AttachmentRepository attachmentRepository;

	@GetMapping("/{id}")
	public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
		log.debug("attachment download controller in!");
		Attachment file = attachmentRepository.findOne(id);
		
		String name = URLEncoder.encode(file.getOriginName(),"UTF-8");
		log.debug("name is " + name);
		
		Path path = Paths.get(file.getPath());
		PathResource resource = new PathResource(path);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType(file.getType()));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
		header.setContentLength(resource.contentLength());
		return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}
}