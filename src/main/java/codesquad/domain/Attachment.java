package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Attachment extends AbstractEntity {
	@Column(nullable = false)
	private String name;

	public Attachment() {
	}

	public Attachment(String name) throws IllegalArgumentException {
		this(0L, name);
	}

	public Attachment(long id, String name) throws IllegalArgumentException {
		super(id);

		if (name == null) {
			throw new IllegalArgumentException();
		}

		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Attachment{" +
				"name='" + name + '\'' +
				'}';
	}
}
