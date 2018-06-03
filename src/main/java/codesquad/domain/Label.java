package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"))
	private User writer;

	private String name;

	public String getName() {
		return name;
	}

	public User getWriter() {
		return writer;
	}
	
	
}
