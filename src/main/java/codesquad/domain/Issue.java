package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Embedded
    private IssueBody issueBody;

    public Issue() {
    }

    public Issue(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.issueBody);
    }
}
