package codesquad.web;

import org.junit.Test;

public class IssueControllerTest {
	private IssueController issueController;
	
	@org.junit.Before
	public void setup() {
		issueController = new IssueController();
	}
	
	@Test
	public void input_title_contents_is_null() {
// Exception이 발생해야 할 상황에 잘 발생하는지는 어떻게 테스트 해볼 수 있을까?		
//		assertThatNullPointerException(issueController.vaild(""));
	}
}
