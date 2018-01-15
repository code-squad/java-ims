package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.MileStone;
import codesquad.domain.MileStoneDto;
import codesquad.domain.MileStoneRepository;

@Service
public class MileStoneService {
	@Resource(name = "mileStoneRepository")
	private MileStoneRepository mileStoneRepository;
	
	public void add(MileStoneDto mileStoneDto) {
		mileStoneRepository.save(mileStoneDto._toMileStone());
	}
	
	public List<MileStone> findAll(){
		return mileStoneRepository.findAll();
	}
	
	
}
