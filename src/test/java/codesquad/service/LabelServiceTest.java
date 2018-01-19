package codesquad.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.dto.LabelDto;

public class LabelServiceTest {

	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;

	@Test
	public void findAll_label() {
		LabelDto labelDto = new LabelDto();
		labelDto.setSubject("subject");
		Label label = labelDto._toLabel();
		labelRepository.save(label);
		assertEquals("subject", labelRepository.findAll().toString());
	}

}
