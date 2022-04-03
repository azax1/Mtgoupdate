package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysEU;

public class EuropeTest extends TimeZoneTest {
	int offset = getTimeZone().getOffsetFromPT();
	LocalDate jan1 = LocalDate.parse(
		Integer.toString((LocalDate.now()).getYear())
		+ "-01-01"
	);
	LocalDate usDstStart = US.getInstance().dstStarts;
	LocalDate euDstStart = getTimeZone().dstStarts;
	LocalDate euDstEnd = getTimeZone().dstEnds;
	LocalDate usDstEnd = US.getInstance().dstEnds;
	LocalDate dec31 = LocalDate.parse(
			Integer.toString((LocalDate.now()).getYear())
			+ "-12-31"
		);
	
	@Test
	public void testBoundaryConditions() {
		assertEquals(
			offset,
			getOffset(jan1, 0)
		);
		
		assertEquals(
			offset,
			getOffset(dec31, 0)
		);
	}
	
	@Test
	public void testUsDstStart() {
		assertEquals(
			offset,
			getOffset(usDstStart, 0)
		);
		
		assertEquals(
			offset,
			getOffset(usDstStart, 1)
		);
		
		assertEquals(
			offset - 1,
			getOffset(usDstStart, 2)
		);
	}
	
	@Test
	public void testEuDstStart() {
		assertEquals(
			offset - 1,
			getOffset(euDstStart, 0)
		);
		
		assertEquals(
			offset - 1,
			getOffset(euDstStart, 15)
		);
		
		assertEquals(
			offset,
			getOffset(euDstStart, 17)
		);
	}
	
	@Test
	public void testEuDstEnd() {
		assertEquals(
			offset,
			getOffset(euDstEnd, 0)
		);
		
		assertEquals(
			offset,
			getOffset(euDstEnd, 15)
		);
		
		assertEquals(
			offset - 1,
			getOffset(euDstEnd, 17)
		);
	}
	
	@Test
	public void testUsDstEnd() {
		assertEquals(
			offset - 1,
			getOffset(usDstEnd, 0)
		);
		
		assertEquals(
			offset - 1,
			getOffset(usDstEnd, 1)
		);
		
		assertEquals(
			offset,
			getOffset(usDstEnd, 3)
		);	
	}

	@Override
	public TimeZone getTimeZone() {
		return Europe.getInstance();
	}

	@Override
	public Day getReferenceTimeZone() {
		return DaysEU.MONDAY;
	}
}
