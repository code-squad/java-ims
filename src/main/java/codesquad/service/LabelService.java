package codesquad.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;

@Service
public class LabelService {
	
	@Resource(name = "labelRepository")
	LabelRepository labelRepository;
	
	public Iterable<Label> findLabelAll(){
		return labelRepository.findAll();
	}
	
	public Label findByLabelId(Long id) {
		return labelRepository.findOne(id);
	}
	
	public Label createLabel(Label label) {
		return labelRepository.save(label);
	}
	
	@Transactional
	public void update(long id, Label updateLabel) {
		Label oldLabel = findByLabelId(id);
		oldLabel.update(updateLabel);
	}
	
	public void delete(long id) {
		Label oldLabel = findByLabelId(id);
		labelRepository.delete(oldLabel);
	}

	@Transactional
	public void addLabel(Issue issue,long labelId) {
		issue.setLabel(findByLabelId(labelId));
	}

}
