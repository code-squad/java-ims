package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String create(@LoginUser User loginUser, Label label, RedirectAttributes redirectAttrs) {
        try {
            labelService.add(label);
            return "redirect:/labels";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Duplicate");
            return "redirect:/labels";
        }
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model, RedirectAttributes redirectAttrs) {
        try {
            model.addAttribute("label", labelService.findById(loginUser, id));
            return "/label/updateForm";
        } catch (UnAuthorizedException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/labels";
        }
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Label updatedLabel, RedirectAttributes redirectAttrs) {
        try {
            labelService.update(loginUser, id, updatedLabel);
            return "redirect:/labels";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/labels";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id, RedirectAttributes redirectAttrs) {
        try {
            labelService.delete(loginUser, id);
            return "redirect:/labels";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/labels";
        }
    }
}
