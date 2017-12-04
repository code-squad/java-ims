package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class HomeController {
	
	@RequestMapping("")
	public String indexForm() {
		return "index";
	}
}
