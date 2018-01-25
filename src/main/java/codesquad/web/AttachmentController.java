package codesquad.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import codesquad.domain.File;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.FileService;

@Controller
@RequestMapping("/issues/{issueId}/attachments")
public class AttachmentController {
	private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
	private static String HOME = System.getProperty("user.home");
//	private static final String UPLOADED_FOLDER = "../temp/";

	@Resource(name ="fileService")
	private FileService fileService;
	
	@PostMapping("")
	public String upload(@LoginUser User loginUser, MultipartFile file, RedirectAttributes redirectAttributes, @PathVariable long issueId) throws Exception {
		log.debug("original file name: {}", file.getOriginalFilename());
		log.debug("contenttype: {}", file.getContentType());
		// inputStream 을 이용해 파일을 읽는다.
		if(file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/issues/{issueId}";
		}
		try {// 1. 파일을 서버 디렉토리에 저장.
			// file을 byte 단위로 받는다.
			byte[] bytes = file.getBytes();
			// converts the given URI to Path object
//			Path path = Paths.get(HOME, file.getOriginalFilename() + UUID.randomUUID().toString());
			Path path = Paths.get(HOME, file.getOriginalFilename());
			// 주어진 경로에 byte 형태로 저장.
			log.debug("IamHere");
			Files.write(path, bytes);
			log.debug(Files.write(path, bytes).toString());
			// 2. 파일의 정보를 데이터베이스에 저장.
//			String fileName = file.getOriginalFilename() + UUID.randomUUID().toString();
			String fileName = file.getOriginalFilename();
			String originalFileName = file.getOriginalFilename();
			String contentType = file.getContentType();
			File dbFile = new File(fileName, originalFileName, contentType);
			fileService.add(dbFile);
			log.debug(dbFile.toString());
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded" + file.getOriginalFilename() + "");
		}catch(IOException e) {
			e.printStackTrace();
		}		
		return "redirect:/issues/{issueId}";
	}

	@GetMapping("/{id}")
	public ResponseEntity<PathResource> download(@LoginUser User loginUser, @PathVariable long id) throws Exception {
		// 데이터베이스에서 해당 파일의 정보를 찾는다.
		File file = fileService.findById(id);
		// 파일의 경로를 찾는다.
		Path path = Paths.get(HOME, file.getFileName());
		log.debug("path: {}", path.toString());
		// pathResource 생성.
		PathResource resource = new PathResource(path);
		log.debug("resource: {}", resource.toString());
		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.TEXT_XML);
		
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());
		log.debug("hello");
        try {
			header.setContentLength(resource.contentLength());
		} catch (IOException e) {
			log.debug("hereitis");
			e.printStackTrace();
		}
        log.debug("here");
        log.debug(new ResponseEntity<PathResource>(resource, header, HttpStatus.OK).toString());
        return new ResponseEntity<PathResource>(resource, header, HttpStatus.OK);		
	}
}
