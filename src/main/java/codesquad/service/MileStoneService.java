package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.User;
import codesquad.dto.MileStoneDto;

@Service
public class MileStoneService {

	@Resource(name="mileStoneRepository")
	private MileStoneRepository mileStoneRepository;
	
	
	public List<MileStone> findAll(){
		return mileStoneRepository.findAll();
	}


	public MileStone add(User loginUser, MileStoneDto mileStoneDto) {
		MileStone mileStone = mileStoneDto.toMileStone();
		mileStone.writeBy(loginUser);
		return mileStoneRepository.save(mileStone);
	}
	
}
