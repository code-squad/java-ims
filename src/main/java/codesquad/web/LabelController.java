package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.LabelDto;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/labels")
public class LabelController {
    private static final Logger logger = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private LabelService labelService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/label/form";
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, LabelDto labelDto) {
        Label label = labelService.add(loginUser, labelDto);
        return String.format("redirect:/labels/%d", label.getId());
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, LabelDto labelDto) {
        labelService.update(loginUser, id, labelDto);

        return String.format("redirect:/labels/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        labelService.delete(loginUser, id);

        return "redirect:/labels";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        try {
            model.addAttribute("label", labelService.findById(loginUser, id));
            return "/label/updateForm";
        } catch (UnAuthorizedException e) {
            model.addAttribute("error", e.getMessage());
            return "/user/login";
        }
    }
}
