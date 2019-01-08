package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private static final Logger logger = getLogger(LabelController.class);

    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/label/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Label label) {
        logger.debug("label!!!!!!!!!!!!!!!!!!!!!!: {}", label);
        labelService.add(label);
        return "redirect:/labels/list";
    }

    @GetMapping("/{id}")
    public String show(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("label", labelService.findById(id));
        return "/label/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("label", labelService.findById(id));
        return "/label/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Label updatedLabel) {
        labelService.update(loginUser, id, updatedLabel);
        return String.format("redirect:/labels/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        labelService.delete(loginUser,id);
        return "redirect:/labels/list";
    }
}
