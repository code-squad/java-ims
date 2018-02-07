package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@PostMapping("/issues/{issueId}")
	public String upload(@PathVariable long issueId, MultipartFile file) throws Exception {
		log.debug("original file name: {}", file.getOriginalFilename());
		log.debug("contenttype: {}", file.getContentType());

		String fileName = file.getOriginalFilename();
		String path = "./src/main/resources/attachments/";

		File target = new File(path, fileName);
		FileCopyUtils.copy(file.getBytes(), target);

		attachmentService.save(issueId, target.getPath(), target.getName());

		return "redirect:/issues/{issueId}";
	}

	@GetMapping("/{id}")
	public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
		Attachment attachment = attachmentService.findOne(id);

		Path path = Paths.get(attachment.getPath());
		PathResource resource = new PathResource(path);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_XML);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getFileName());
		header.setContentLength(resource.contentLength());

		return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}
}
