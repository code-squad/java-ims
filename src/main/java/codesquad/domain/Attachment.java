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
	private String publicName;

	private String privateName;

	public Attachment() {
	}

	public Attachment(String publicName, String privateName) throws IllegalArgumentException {
		this(0L, publicName, privateName);
	}

	public Attachment(long id, String publicName, String privateName) throws IllegalArgumentException {
		super(id);

		if (publicName == null || privateName == null) {
			throw new IllegalArgumentException();
		}

		this.publicName = publicName;
		this.privateName = privateName;
	}

	public String getPublicName() {
		return publicName;
	}

	public String getPrivateName() {
		return privateName;
	}

	@Override
	public String toString() {
		return "Attachment{" +
				"publicName='" + publicName + '\'' +
				", privateName='" + privateName + '\'' +
				'}';
	}
}
