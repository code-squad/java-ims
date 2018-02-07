package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
	private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);

	@Resource(name = "attachmentRepository")
	private AttachmentRepository attachmentRepository;

	public List<Attachment> findAll() {
		return attachmentRepository.findAll();
	}

	public Optional<Attachment> findById(long attachmentId) {
		return Optional.ofNullable(attachmentRepository.findOne(attachmentId));
	}
}
