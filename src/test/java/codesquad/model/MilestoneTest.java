package codesquad.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class MilestoneTest {
	Milestone milestone;

	// set DateTimeFormat("yyyy-MM-dd'T'HH:mm")
	DateTimeFormatter setFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	DateTimeFormatter getFormat = DateTimeFormatter.ofPattern("MMMM dd,yyyy");

	@Before
	public void before() {
		milestone = new Milestone();
	}

	@Test
	public void dateTest() {
		milestone.setStartDate(LocalDateTime.of(2017, 12, 20, 00, 00).format(setFormat));

		// get DateTimeFormat("MMMM dd,yyyy")
		assertEquals(milestone.getStartDate(), LocalDateTime.of(2017, 12, 20, 00, 00).format(getFormat));
		assertEquals(milestone.getStartDate(), "12월 20,2017");

		milestone.setEndDate(LocalDateTime.of(2018, 1, 25, 00, 00).format(setFormat));

		assertEquals(milestone.getEndDate(), LocalDateTime.of(2018, 1, 25, 00, 00).format(getFormat));
		assertEquals(milestone.getEndDate(), "1월 25,2018");
	}
}