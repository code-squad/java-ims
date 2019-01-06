package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.IssueBody;

import javax.persistence.Embedded;
import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {
    @Embedded
    private IssueBody issueBody;

    public IssueDto() {
    }

    public IssueDto(IssueBody issueBody) {
        super();
        this.issueBody = issueBody;
    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public Issue _toIssue() {
        return new Issue(this.issueBody);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(issueBody, issueDto.issueBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueBody);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "issueBody=" + issueBody +
                '}';
    }
}
