package codesquad.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.web.MilestoneController;

@Service
public class LabelService {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Autowired
	private LabelRepository labelRepository;
	
	public Label findOne(long id) {
		return labelRepository.findOne(id);
	}
	
	public List<Label> findAll() {
		return labelRepository.findAll();
	}
	
	public Iterable<Label> findNotDeleted() {
		return labelRepository.findByDeleted(false);
	}
	
	public void create(User loginUser, String subject) {
		log.debug("Label service(create) in");
		Label label = new Label(subject);
		label.writedBy(loginUser);
		labelRepository.save(label);
	}
	
	@Transactional
	public void update(User loginUser, long id, String subject) throws UnAuthenticationException {
		log.debug("Label service(update) in");
		Label label = labelRepository.findOne(id);
		label.update(loginUser, subject);
	}
	
	@Transactional
	public void delete(User loginUser, long id) throws UnAuthenticationException {
		log.debug("Label service(delete) in");
		Label label = labelRepository.findOne(id);
		label.delete(loginUser);
	}
}
