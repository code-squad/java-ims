package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    public List<Label> findAll() {
        return (List<Label>) labelRepository.findAll();
    }

    public Label add(User loginUser, LabelDto labelDto) {
        Label label = labelDto.toLabel();
        label.writedBy(loginUser);

        return labelRepository.save(label);
    }

    public Label findById(User loginUser, Long id) {
        Label label = labelRepository.findOne(id);

        if (!label.isWritedBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정할 수 있습니다.");
        }

        return label;
    }

    @Transactional
    public void update(User loginUser, Long id, LabelDto labelDto) {
        Label label = labelRepository.findOne(id);

        label.update(loginUser, labelDto);
    }

    @Transactional
    public void delete(User loginUser, Long id) {
        Label label = labelRepository.findOne(id);

        if (!label.isWritedBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 삭제할 수 있습니다.");
        }

        labelRepository.delete(label);
    }
}
