package codesquad.web;

import codesquad.domain.MileStone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MileStoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/milestones")
public class MileStoneController {
    private static final Logger log = LoggerFactory.getLogger(MileStoneController.class);

    @Resource(name = "mileStoneService")
    private MileStoneService mileStoneService;

    @GetMapping("/form")
    public String form() {
        return "/milestone/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, MileStone mileStone) {
        log.debug("***** milestone : {}", mileStone);

        mileStoneService.create(mileStone);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("milestones", mileStoneService.findAll());
        return "/milestone/list";
    }
}
