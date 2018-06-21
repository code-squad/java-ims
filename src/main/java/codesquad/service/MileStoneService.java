package codesquad.service;

import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.User;
import codesquad.dto.MileStoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MileStoneService {
    private static final Logger log =  LoggerFactory.getLogger(MileStoneService.class);

    @Resource(name = "mileStoneRepository")
    private MileStoneRepository mileStoneRepository;

    public MileStone add(MileStoneDto mileStoneDto) {
        log.info("add method called on service");
        return mileStoneRepository.save(mileStoneDto.toMileStone());
    }

    public List<MileStone> findAll() {
        return mileStoneRepository.findAll();
    }
}
