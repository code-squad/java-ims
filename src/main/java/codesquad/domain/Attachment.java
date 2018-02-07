package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Attachment extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Attachment.class);

	private String path;
	private String fileName;

	public Attachment() {
	}

	public Attachment(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}
}
