package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysJP;

public class JapanTest {
	int offset = Japan.getInstance().getOffsetFromPT();
	LocalDate jan1 = LocalDate.parse("2022-01-01");
	LocalDate usDstStart = US.getInstance().dstStarts;
	LocalDate usDstEnd = US.getInstance().dstEnds;
	LocalDate dec31 = LocalDate.parse("2022-12-31");
	
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
	
	@Test
	public void sameAsReference() {
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-01-08");
		LocalDate date = start;
		
		while (!date.equals(end)) {
			String calculatedTweet = Japan.getInstance().getTweetText(date);
			String referenceTweet = Day.fromDayOfWeek(date.getDayOfWeek(), DaysJP.MONDAY).text();
			assertEquals(referenceTweet, calculatedTweet);
			
			date = date.plusDays(1);
		}
	}	
	
	private int getOffset(LocalDate date, int hour) {
		return Japan.getInstance().getLocalHour(date, hour) - hour;
	}
}
