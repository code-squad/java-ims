package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3)
    @Column(nullable = false, length = 30)
    private String title;

    @Size(min = 3)
    @Column(nullable = false, length = 30)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {
    }

    public Issue(String title, String contents){
        super(0L);
        this.title = title;
        this.contents = contents;
    }

    public void update(User loginUser, Issue target) {
        if(!isOwner(loginUser)){
            throw new UnAuthorizedException();
        }
        this.title = target.title;
        this.contents = target.contents;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void writeBy(User writer){
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public IssueDto toIssueDto(){
        return new IssueDto(title, contents);
    }

    @Override
    public String toString() {
        return "Issue{" +
                super.toString() +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                '}';
    }
}
