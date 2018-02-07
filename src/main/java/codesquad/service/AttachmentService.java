package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {
	private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

	@Autowired
	private HttpServletRequest request;

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	public List<Attachment> findAll() {
		return attachmentRepository.findAll();
	}

	public Optional<Attachment> findById(long attachmentId) {
		return Optional.ofNullable(attachmentRepository.findOne(attachmentId));
	}

	public String saveFile(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException();
		}

		String directoryName = request.getSession().getServletContext().getRealPath("upload");
		String privateName = UUID.randomUUID().toString();

		file.transferTo(new File(directoryName + privateName));

		return privateName;
	}

	public PathResource readFile(String privateName) throws UnsupportedEncodingException {
		String directoryName = request.getSession().getServletContext().getRealPath("upload");

		Path path = Paths.get(directoryName + privateName);
		PathResource resource = new PathResource(path);

		return resource;
	}
}
