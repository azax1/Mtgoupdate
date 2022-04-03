package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysJP;

public class JapanTest extends TimeZoneTest {
	int offset = getTimeZone().getOffsetFromPT();
	LocalDate jan1 = LocalDate.parse(
			Integer.toString((LocalDate.now()).getYear())
			+ "-01-01"
		);
	LocalDate usDstStart = US.getInstance().dstStarts;
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
		
		assertEquals(
			offset - 1,
			getOffset(usDstStart, 3)
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
			offset - 1,
			getOffset(usDstEnd, 2)
		);
		
		assertEquals(
			offset,
			getOffset(usDstEnd, 3)
		);
	}

	@Override
	public TimeZone getTimeZone() {
		return Japan.getInstance();
	}

	@Override
	public Day getReferenceTimeZone() {
		return DaysJP.MONDAY;
	}
}
