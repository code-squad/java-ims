package codesquad.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import support.domain.AbstractEntity;


@Entity
public class Issue extends AbstractEntity {
	
	@ManyToOne 
	@JoinColumn(foreignKey=@ForeignKey(name = "fk_issue_parent_id"))
	User user;
	
	@Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
	private String subject;
    
	
    @Lob
	@Size(min = 3)
	private String comment;
    
    public Issue() {
    	
    }
    
    public Issue(User user, String subject, String comment) {
    		this(0L, user, subject, comment);
    }
    
    public Issue(long id, User user, String subject, String comment) {
    		super(id);
    		this.user = user;
    		this.subject = subject;
    		this.comment = comment;
    }

	public IssueDto _toIssueDto() {
    		return new IssueDto(this.user, this.subject, this.comment);
    }
	
	public boolean isSameUser(@LoginUser User loginUser) {
		return this.user.equals(loginUser);
	}
	
	public void update(@LoginUser User loginUser, String subject, String comment) {
		if(!isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		this.subject = subject;
		this.comment = comment;
	}
	
	public static String getBlank(String blank) {
		if(StringUtils.isBlank(blank)) {
			return blank;
		}
		return null;
	}
	
	public static String getOptionalBlank(String blank) {
		return Optional.ofNullable(getBlank(blank)).orElse("");
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
