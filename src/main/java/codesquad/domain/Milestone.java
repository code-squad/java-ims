package codesquad.domain;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import support.domain.AbstractEntity;

@Entity
public class Milestone extends AbstractEntity {

	@Size(min = 6, max = 20)
	@Column(nullable = false, length = 20)
	private String title;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startDate;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date endDate;
	
	public Milestone() {
	}

	public Milestone(String title, String startDate, String endDate) throws ParseException {
		this(0L, title ,startDate, endDate);
	}

	public Milestone(long id, String title, String startDate, String endDate) throws ParseException {
		super(id);
		this.title = title;
		this.startDate = convertTime(startDate);
		this.endDate = convertTime(endDate);
	}
	
	public static Date convertTime(String date) throws ParseException {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return transFormat.parse(date);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public URI createUri() {
		return URI.create("/api/milestones/" + getId());
	}

	@Override
	public String toString() {
		return "Milestone [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate 
				+ "]";
	}

	



}
