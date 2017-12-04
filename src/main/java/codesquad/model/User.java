package codesquad.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String id;
	private String name;
	private String password;

	public User() {

	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "id : " + id + " name : " + name + " password : " + password;
	}
}
