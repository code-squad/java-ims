package codesquad.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LabelTest {
	Label label;
	@Before
	public void before() {
		label = new Label();
	}
	
	@Test
	public void labelTest() {
		label.setName("sss");
		assertEquals("sss", label.getName());
	}
}
