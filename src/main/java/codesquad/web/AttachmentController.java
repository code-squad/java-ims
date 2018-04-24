package codesquad.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
	
	@Resource
	private AttachmentRepository attachmentRepository;

	@GetMapping("/{id}")
	public void download(@PathVariable long id) throws Exception {
		// TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
		// 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
		Attachment file = attachmentRepository.findOne(id);
		
		Path serverPath = Paths.get("/Users/koo/Documents/projects/level3/java-ims/file/");
		Path filePath = serverPath.resolve(file.getName());
		log.debug("FUCK " + filePath.toString());

		// pom.xml text 파일을 읽어 응답하는 경우 예시

		// Path path = Paths.get("./pom.xml");
		// PathResource resource = new PathResource(path);

		// HttpHeaders header = new HttpHeaders();
		// header.setContentType(MediaType.TEXT_XML);
		// header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
		// header.setContentLength(resource.contentLength());
		// return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
	}
//	@GetMapping("/{id}")
//	public ResponseEntity<PathResource> download(@PathVariable long id) throws Exception {
//		// TODO DB에서 id에 해당하는 파일 경로 정보를 얻는다.
//		// 파일 경로 정보에 해당하는 파일을 읽어 클라이언트로 응답한다.
//		String fileName = attachmentRepository.findOne(id).getName();
//		
//		Path path = Paths.get(String.format("./%s", fileName));
//		
//		// pom.xml text 파일을 읽어 응답하는 경우 예시
//		
//		// Path path = Paths.get("./pom.xml");
//		// PathResource resource = new PathResource(path);
//		
//		// HttpHeaders header = new HttpHeaders();
//		// header.setContentType(MediaType.TEXT_XML);
//		// header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pom.xml");
//		// header.setContentLength(resource.contentLength());
//		// return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);
//	}
}