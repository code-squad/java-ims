package codesquad.web;

import codesquad.UnsupportedFormatException;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Value("${error.not.supported}")
    private String errorMessage;

    private static final Logger logger = getLogger(LabelController.class);

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/form")
    public String labelForm(@LoginUser User loginUser, Model model) {
        return "/label/form";
    }

    @PostMapping
    public String createLabel(@LoginUser User loginUser, @Valid LabelDto labelDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new UnsupportedFormatException(errorMessage);
        }
        logger.debug("Call createLabel Method(), LabelDto : {}", labelDto);
        labelService.createLabel(loginUser, labelDto);
        return "redirect:/labels";
    }
}
