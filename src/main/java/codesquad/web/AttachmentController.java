package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
	@PostMapping("")
	public String upload(MultipartFile file) throws Exception {
		log.debug("original file name: {}", file.getOriginalFilename());
		log.debug("contenttype: {}", file.getContentType());
		return "redirect:/";
	}
}
