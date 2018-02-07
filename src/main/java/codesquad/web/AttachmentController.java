package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private IssueService issueService;

	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("/{issueId}")
	public String upload(@PathVariable long issueId, MultipartFile file) throws Exception {
		log.debug("original file name: {}", file.getOriginalFilename());
		log.debug("contenttype: {}", file.getContentType());

		if (file.isEmpty()) {
			throw new IllegalArgumentException();
		}

		String fileName = file.getOriginalFilename();
		String directoryName = request.getSession().getServletContext().getRealPath("upload");
		log.debug("path: {}, name: {}", directoryName, fileName);
		file.transferTo(new File(directoryName + fileName));

		issueService.attachFile(issueId, new Attachment(fileName));

		return String.format("redirect:/issues/%d", issueId);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
		Attachment attachment = attachmentService.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		String fileName = new String(attachment.getName().getBytes("UTF-8"), "ISO-8859-1");
		String directoryName = request.getSession().getServletContext().getRealPath("upload");
		Path path = Paths.get(directoryName + attachment.getName());
		PathResource resource = new PathResource(path);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_XML);
		header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));
		header.setContentLength(resource.contentLength());
		return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}
}