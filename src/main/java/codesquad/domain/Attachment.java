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
	
	@JsonProperty
	private String type;

	public Attachment() {
	}

	public Attachment(String originName, String name, String path, String type) {
		this.originName = originName;
		this.name = name;
		this.path = path;
		this.type = type;
	}
	
	//getter(), setter() methods
	public long getId() {
		return id;
	}

	public String getOriginName() {
		return originName;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", originName=" + originName + ", name=" + name + ", path=" + path + ", type="
				+ type + "]";
	}
}
