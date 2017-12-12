package codesquad.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import codesquad.exception.InvalidStoreFileException;
import codesquad.model.FileStorage;
import codesquad.model.Issue;
import codesquad.model.IssueRepository;
import codesquad.model.Label;
import codesquad.model.LabelRepository;
import codesquad.model.Milestone;
import codesquad.model.MilestoneRepository;
import codesquad.model.Reply;
import codesquad.model.ReplyFilePath;
import codesquad.model.ReplyFilePathRepository;
import codesquad.model.ReplyRepository;
import codesquad.model.User;
import codesquad.model.UserRepository;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("issue")
public class IssueController {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private ReplyFilePathRepository replyFilePathRepository;

	@GetMapping("write")
	public String issueForm(HttpSession session) {
		if (!HttpSessionUtil.isLoginSession(session)) {
			log.debug("로그인 후 작성해주세요.");
			return "redirect:/";
		}
		return "issue/form";
	}

	@PostMapping("write")
	public String writeIssue(Issue issue, HttpSession session) {
		issue.setWriter(HttpSessionUtil.loginSessionUser(session));
		issueRepository.save(issue);
		log.debug(issue.toString());
		return "redirect:/";
	}

	@PostMapping("{id}/reply")
	public String writeReply(@PathVariable long id, Reply reply, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		reply.setWriter(HttpSessionUtil.loginSessionUser(session));
		reply.setIssue(issue);
		replyRepository.save(reply);
		return "redirect:/issue/{id}";
	}

	@PutMapping("{id}/milestone/{milestoneId}")
	public String setMilestone(@PathVariable long id, @PathVariable long milestoneId) {
		Issue issue = issueRepository.findOne(id);
		Milestone milestone = milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	@GetMapping("{id}")
	public String showIssue(@PathVariable long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		model.addAttribute("assignee", userRepository.findAll());
		model.addAttribute("label", labelRepository.findAll());
		model.addAttribute("milestone", milestoneRepository.findAll());
		model.addAttribute("isChecked", getSelectedMark(true));
		
		issue.setLoginUser(HttpSessionUtil.loginSessionUser(session));
		model.addAttribute("issue", issue);
		
		return "issue/show";
	}

	@GetMapping("{id}/edit")
	public String editForm(@PathVariable long id, Model model, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		issue.setLoginUser(HttpSessionUtil.loginSessionUser(session));
		if (issue.getIsOwner()) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}";
		}
		model.addAttribute("issue", issue);
		return "issue/edit";
	}

	@PutMapping("{id}")
	public String edit(@PathVariable long id, String subject, String comment) {
		Issue issue = issueRepository.findOne(id);
		issue.updateSubject(subject);
		issue.updateComment(comment);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable long id, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		issue.setLoginUser(HttpSessionUtil.loginSessionUser(session));
		if (issue.getIsOwner()) {
			log.debug("본인이 작성한 issue가 아닙니다.");
			return "redirect:/issue/{id}";
		}
		issueRepository.delete(id);
		return "redirect:/";
	}

	@PutMapping("{id}/label/{labelId}")
	public String setLabel(@PathVariable long id, @PathVariable long labelId) {
		Issue issue = issueRepository.findOne(id);
		Label label = labelRepository.findOne(labelId);
		issue.setLabel(label);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	@PostMapping("{id}/file")
	public String uploadFile(@PathVariable long id, @RequestParam("file") MultipartFile file, HttpSession session)
			throws InvalidStoreFileException, IOException {
		FileStorage storage = new FileStorage();
		Issue issue = issueRepository.findOne(id);
		Reply reply = new Reply();
		User uploader = HttpSessionUtil.loginSessionUser(session);
		reply.setIssue(issue);
		reply.setReplyComment("upload file");
		reply.setWriter(uploader);
		replyRepository.save(reply);
		ReplyFilePath replyFilePath = new ReplyFilePath();
		replyFilePath.setFilePath(storage.getFilePath() + "\\" + storage.getFileName(file));
		replyFilePath.setReply(reply);
		replyFilePathRepository.save(replyFilePath);
		storage.store(file);
		return "redirect:/issue/{id}";
	}

	@GetMapping("file/{fileId}")
	public String downloadFile(@PathVariable long fileId, HttpServletResponse response) throws IOException {
		ReplyFilePath replyFilePath = replyFilePathRepository.findOne(fileId);
		String filePath = replyFilePath.getFilePath();
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		return "redirect:/issue/{id}";
	}

	@PutMapping("{id}/assignee/{assigneeId}")
	public String setAssignee(@PathVariable long id, @PathVariable String assigneeId) {
		Issue issue = issueRepository.findOne(id);
		User assignee = userRepository.findOne(assigneeId);
		issue.setAssignee(assignee);
		issueRepository.save(issue);
		return "redirect:/issue/{id}";
	}

	private String getSelectedMark(boolean isSelected) {
		if (isSelected) {
			return "mdl-button--accent";
		}
		return "";
	}
}
