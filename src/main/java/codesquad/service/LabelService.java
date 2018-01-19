package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;

@Service
public class LabelService {
	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;
	
	public Label add(LabelDto labelDto) {
		// issue 객체로 바꾸어 집어넣는다.
		return labelRepository.save(labelDto._toLabel());
	}

	public Label findById(long id) {
		return labelRepository.findOne(id);
	}
	
	public List<Label> findAll() {
		return labelRepository.findAll();
	}

	public Label update(@LoginUser User loginUser, long id, String subject) {
		Label label = findById(id);
		label.update(subject);
		return labelRepository.save(label);
	}

	public void delete(@LoginUser User loginUser, long id) {
		labelRepository.delete(findById(id));
	}

	@Override
	public String toString() {
		return "LabelService [labelRepository=" + labelRepository + "]";
	}
	
}
