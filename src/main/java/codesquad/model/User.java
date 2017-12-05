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
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public boolean matchingPassword(String enteredPassword) {
		return !enteredPassword.equals(password);
	}
	
	public void update(User updateUser) {
		this.name = updateUser.name;
		this.password = updateUser.password;
	}

	public String toString() {
		return "id : " + id + " name : " + name + " password : " + password;
	}
}
