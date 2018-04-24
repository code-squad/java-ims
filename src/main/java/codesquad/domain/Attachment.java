package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Attachment {
	@Id
	@GeneratedValue
	private long id;

	@JsonProperty
	@Column(unique = false, nullable = false)
	private String originName;
	
	@JsonProperty
	@Column(unique = false, nullable = false)
	private String name;

	@JsonProperty
	private String path;

	public Attachment() {
	}

	public Attachment(String originName, String name, String path) {
		this.originName = originName;
		this.name = name;
		this.path = path;
	}
	
	//getter(), setter() methods
	public long getId() {
		return id;
	}

	public String getOriginName() {
		return originName;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", originName=" + originName + ", name=" + name + ", path=" + path + "]";
	}
}
