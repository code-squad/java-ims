package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/labels")
public class LabelController {
    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/label/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Label label, Model model) {
        try {
            labelService.add(label);
            return "redirect:/labels";
        } catch (Exception e) {
            model.addAttribute("errorMessage" , "Duplicate");
            return "/label/list";
        }
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("label", labelService.findById(id));
        return "/label/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Label updatedLabel) {
        labelService.update(id, updatedLabel);
        return "redirect:/labels";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        labelService.delete(id);
        return "redirect:/labels";
    }
}
