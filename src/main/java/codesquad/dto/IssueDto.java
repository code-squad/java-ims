package codesquad.dto;
import javax.validation.constraints.Size;
import codesquad.domain.Issue;
public class IssueDto {
	@Size(min = 3, max = 20)
	private long issueId;
	
	@Size(min = 3, max = 20)
	private String title;
	
	@Size(min = 3, max = 20)
	private String contents;
	
	public IssueDto() {
		
	}
	
	public IssueDto(long issueId, String title, String contents) {
		super();
		this.issueId = issueId;
		this.title = title;
		this.contents = contents;
		
	}
	
	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
