package codesquad.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class IssueTest {

	@Test
	public void returnBlankTest() {
		String blank = Issue.getOptionalBlank("jiwon");
		assertEquals("", blank);
	}

}
