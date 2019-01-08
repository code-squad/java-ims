package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelService {

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;


    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label add(Label label) {
        return labelRepository.save(label);
    }

    public Label findById(long id) {
        return labelRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void update(User loginUser, long id, Label updatedLabel) {
        if(loginUser.isGuestUser()) throw new CannotDeleteException("you can't update this label");
        Label originalLabel = this.findById(id);
        originalLabel.update(loginUser, updatedLabel);
        labelRepository.save(originalLabel);
    }

    @Transactional
    public void delete(User loginUser, long id) {
        if(loginUser.isGuestUser()) throw new CannotDeleteException("you can't delete this label");
        Label originalLabel = this.findById(id);
        List<DeleteHistory> histories = originalLabel.delete(loginUser);
        deleteHistoryRepository.saveAll(histories);
    }
}
