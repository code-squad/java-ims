package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
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
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private IssueService issueService;

	@Autowired
	private UserService userService;

	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("/issues/{id}")
	public String issueFileUpload(@PathVariable long id, MultipartFile file) throws Exception {
		String publicName = file.getOriginalFilename();
		String privateName = attachmentService.saveFile(file);

		issueService.attachFile(id, new Attachment(publicName, privateName));

		return String.format("redirect:/issues/%d", id);
	}

	@PostMapping("/users/{id}")
	public String userFileUpload(@PathVariable long id, MultipartFile file) throws Exception {
		String publicName = file.getOriginalFilename();
		String privateName = attachmentService.saveFile(file);

		userService.attachFile(id, new Attachment(publicName, privateName));

		return String.format("redirect:/users/%d", id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
		Attachment attachment = attachmentService.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		PathResource resource = attachmentService.readFile(attachment.getPrivateName());
		String publicName = new String(attachment.getPublicName().getBytes("UTF-8"), "ISO-8859-1");

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_XML);
		header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", publicName));
		header.setContentLength(resource.contentLength());

		return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}
}