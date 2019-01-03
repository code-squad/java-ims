package codesquad.service;

import codesquad.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MileStoneService {

    @Resource(name = "mileStoneRepository")
    private MileStoneRepository mileStoneRepository;

    public List<MileStone> findAll() {
        return mileStoneRepository.findAll();
    }

    public MileStone add(MileStone mileStone) {
        return mileStoneRepository.save(mileStone);
    }

    @Transactional
    public MileStone create(MileStone mileStone) {
        return add(mileStone);
    }
}
