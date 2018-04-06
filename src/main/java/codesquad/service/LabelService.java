package codesquad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;

@Service
public class LabelService {
	@Autowired
	private LabelRepository labelRepository;
	
	public Label findOne(long id) {
		return labelRepository.findOne(id);
	}
	
	public List<Label> findAll() {
		return labelRepository.findAll();
	}
	
	public void create(User loginUser, String level) {
		Label label = new Label(level);
		label.writedBy(loginUser);
		labelRepository.save(label);
	}
}
