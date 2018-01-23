package codesquad.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.dto.LabelDto;

@Service
public class LabelService {
	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;
	
	public List<LabelDto> findAll() {
    	List<LabelDto> labelDtoList = new ArrayList<>();
		for (Label label : labelRepository.findAll()) {
			labelDtoList.add(label._toLabelDto());
		}
		return labelDtoList;
	}

	public Label findById(Long labelId) {
		return labelRepository.findOne(labelId);
	}
	
	public Label saveLabel(LabelDto labelDto) {
		return labelRepository.save(labelDto._toLabel());
	}

	public Label updateLabel(LabelDto labelDto, long id) {
		Label originLabel = labelRepository.getOne(id);
		originLabel.update(labelDto);
		return labelRepository.save(originLabel);
	}

	public void delete(Long id) {
		labelRepository.delete(id);
	}
}
