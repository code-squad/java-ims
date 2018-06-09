package codesquad.web;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class ApiAttachmentController {

	private static final Logger log = LoggerFactory.getLogger(ApiAttachmentController.class);

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@PostMapping("/{issueId}")
	public ResponseEntity<Attachment> upload(@LoginUser User loginUser, @PathVariable Long issueId, MultipartFile file)
			throws IllegalStateException, IOException {
		
		log.debug("original file name: {}", file.getOriginalFilename());
		log.debug("contenttype: {}", file.getContentType());
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<Attachment>(attachmentService.addAttachment(loginUser, issueId, file), header, HttpStatus.CREATED);
	}

	@GetMapping("/{attachmentId}")
	public ResponseEntity<PathResource> download(@PathVariable Long attachmentId) throws IOException {
		PathResource resource = attachmentService.downloadAttachment(attachmentId);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_XML);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=+" + resource.getFilename());
		header.setContentLength(resource.contentLength());
		return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}

}
