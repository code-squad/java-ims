package codesquad.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LabelTest {
	
	private Label label = new Label("test label", "white");
	
	@Test
	public void update() throws Exception{
		Label updateLabel = new Label("new label", "gray");
		label.update(updateLabel);
		assertThat(label.getTitle(), is(updateLabel.getTitle()));
		assertThat(label.getColor(), is(updateLabel.getColor()));
	}
	

}
