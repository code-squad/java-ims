package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Service
public class AttachmentService {
	private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	@Transactional
	public void save(long issueId, String path, String fileName) {
		Attachment attachment = new Attachment(path, fileName);

		issueRepository.findOne(issueId).toAttachment(attachment);
		attachmentRepository.save(attachment);
	}
}
