package codesquad.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Issues implements Serializable {
	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	private List<Issue> issues = new ArrayList<>();

	public Issues() {
	}

	public List<Issue> getAnswers() {
		return issues;
	}

	public void setAnswers(List<Issue> answers) {
		this.issues = issues;
	}

	public Issues add(Issue answer) {
		this.issues.add(answer);
		return this;
	}

	public int size() {
		return this.issues.size();
	}
}
