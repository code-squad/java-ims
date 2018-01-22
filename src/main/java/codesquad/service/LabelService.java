package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.Milestone;

@Service
public class LabelService {
	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;
	
	public List<Label> findAll() {
		return labelRepository.findAll();
	}

	public Label findById(Long labelId) {
		return labelRepository.findOne(labelId);
	}
	
	public Label saveLabel(Label label) {
		return labelRepository.save(label);
	}

	public Label updateLabel(Label label, long id) {
		Label originLabel = labelRepository.getOne(id);
		originLabel.update(label);
		return labelRepository.save(originLabel);
	}

	public void delete(Long id) {
		labelRepository.delete(id);
	}
}
