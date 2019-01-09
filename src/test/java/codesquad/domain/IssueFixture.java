package codesquad.domain;

import org.springframework.beans.propertyeditors.ReaderEditor;

import static codesquad.domain.UserTest.RED;

public class IssueFixture {
    public static final Issue ISSUE_WRITER_RED = new Issue(RED, new IssueBody("subject", "comment"));

}
