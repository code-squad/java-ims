package codesquad.dto;

import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.Size;

public class AnswerDto {
    @Size(min = 1)
    @Lob
    private String comment;

    private IssueDto issueDto;

    private UserDto userDto;

    private boolean deleted = false;

    public AnswerDto() {
    }

    public AnswerDto(String comment, IssueDto issueDto, UserDto userDto, boolean deleted) {
        this.comment = comment;
        this.issueDto = issueDto;
        this.userDto = userDto;
        this.deleted = deleted;
    }

    public Answer _toAnswer(Issue issue, User loginUser) {
        return new Answer(loginUser, this.comment, issue, this.deleted);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IssueDto getIssueDto() {
        return issueDto;
    }

    public void setIssueDto(IssueDto issueDto) {
        this.issueDto = issueDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "comment='" + comment + '\'' +
                ", issueDto=" + issueDto +
                ", userDto=" + userDto +
                ", deleted=" + deleted +
                '}';
    }
}
