package codesquad.domain;

import org.springframework.beans.propertyeditors.ReaderEditor;

import static codesquad.domain.UserTest.RED;

public class IssueFixture {
    public static final Issue issue = new Issue(RED, new IssueBody("subject", "comment"));

}
