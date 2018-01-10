package codesquad.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;


@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
	private String subject;
    
    @Lob
	@Size(min = 3)
	private String comment;
    
    public Issue() {
    	
    }
    
    public Issue(String subject, String comment) {
    		this(0L, subject, comment);
    }
    
    public Issue(long id, String subject, String comment) {
    		super(id);
    		this.subject = subject;
    		this.comment = comment;
    }

	public IssueDto _toIssueDto() {
    		return new IssueDto(this.subject, this.comment);
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
	
	
}
