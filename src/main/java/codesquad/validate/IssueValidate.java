package codesquad.validate;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class IssueValidate {

	private String subjectMsg;
	private String commentMsg;
	
	public IssueValidate(String subjectMsg, String commentMsg) {
		this.subjectMsg = subjectMsg;
		this.commentMsg = commentMsg;
	}
	
	
	public static IssueValidate of(BindingResult bindingResult) {
		return new IssueValidate(check(bindingResult.getFieldError("subject")),check(bindingResult.getFieldError("comment")));
	}
	
	public static String check(FieldError msg) {
		if(msg==null) {
			return "";
		}
		return msg.getDefaultMessage();
	}


	public String getSubjectMsg() {
		return subjectMsg;
	}


	public String getCommentMsg() {
		return commentMsg;
	}
	
	
}
