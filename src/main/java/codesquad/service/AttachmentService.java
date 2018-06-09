package codesquad.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;

@Service
public class AttachmentService {

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	public Issue findById(Long id) {
		return issueRepository.findById(id).orElseThrow(() -> new NullPointerException("존재하지 않는 이슈"));
	}

	public Attachment addAttachment(User loginUser, Long issueId, MultipartFile file)
			throws IllegalStateException, IOException {
		checkFile(file);
		Attachment attachment = new Attachment(loginUser, findById(issueId), file.getOriginalFilename());
		file.transferTo(new File(attachment.getPath(), attachment.getSaveFileName()));
		return attachmentRepository.save(attachment);
	}

	public PathResource downloadAttachment(Long attachmentId) {
		Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(NullPointerException::new);
		Path path = Paths.get(attachment.getPath() + attachment.getSaveFileName());
		PathResource resource = new PathResource(path);
		return resource;
	}
	
	public void checkFile(MultipartFile file) {
		if(file.isEmpty()) {
			throw new NullPointerException();
		}
	}
	
	
}
