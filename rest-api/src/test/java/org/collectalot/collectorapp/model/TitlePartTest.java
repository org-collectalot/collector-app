package org.collectalot.collectorapp.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TitlePartTest {
	private TitlePart tp;
	@BeforeEach
	public void init() {
		tp = new TitlePart();
		tp.setDeleted(true);
		tp.setId(1L);
		tp.setParentId(2L);
		tp.setText("some title");
		tp.setVersion(4);
	}
	@Test
	public void testGetSetI() {
		assertTrue(tp.isDeleted());
		assertSame(1L, tp.getId());
		assertSame(2L, tp.getParentId());
		assertSame("some title", tp.getText());
		assertSame(4, tp.getVersion());
	}
	@Test
	public void testGetSetII() {
		tp.setDeleted(false);
		tp.setId(3L);
		tp.setParentId(5L);
		tp.setText("akjhf");
		tp.setVersion(8);
		
		assertFalse(tp.isDeleted());
		assertSame(3L, tp.getId());
		assertSame(5L, tp.getParentId());
		assertSame("akjhf", tp.getText());
		assertSame(8, tp.getVersion());
	}
}
