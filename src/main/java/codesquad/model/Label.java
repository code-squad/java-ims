package codesquad.model;

import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Label {
	private final static AtomicLong autoIncrementId = new AtomicLong(3);
	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_label_writer"), nullable = false)
	private User writer;
	@Column(nullable = false)
	private String name;

	public Label() {
		id = autoIncrementId.incrementAndGet();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getId() {
		return id;
	}
}