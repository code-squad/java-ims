package codesquad.web;

import codesquad.domain.Multipart;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MultipartFileService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.naming.CannotProceedException;

import java.nio.file.Path;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/attatchments")
public class AttatchmentController {
    private static final Logger log = getLogger(AttatchmentController.class);

    @Resource(name = "multipartFileService")
    private MultipartFileService multipartFileService;

    @PostMapping("/{issueId}")
    public String upload(@LoginUser User user, @PathVariable long issueId, MultipartFile file, Model model) throws Exception{
        if (file.isEmpty()) {
            throw new CannotProceedException();
        }
        multipartFileService.add(user, issueId, file);
        return "redirect:/issues/{issueId}";
    }
}
