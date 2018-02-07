package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Attachment.class);

	private String path;
	private String publicName;
	private String privateName;

	public Attachment() {
	}

	public Attachment(String path, String publicName, String privateName) {
		this.path = path;
		this.publicName = publicName;
		this.privateName = privateName;
	}

	public String getPath() {
		return path;
	}

	public String getPublicName() {
		return publicName;
	}

	public String getPrivateName() {
		return privateName;
	}

}
