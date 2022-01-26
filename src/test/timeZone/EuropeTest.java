package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysEU;

public class EuropeTest {
	int offset = Europe.getInstance().getOffsetFromPT();
	LocalDate jan1 = LocalDate.parse("2022-01-01");
	LocalDate usDstStart = US.getInstance().dstStarts;
	LocalDate euDstStart = Europe.getInstance().dstStarts;
	LocalDate euDstEnd = Europe.getInstance().dstEnds;
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
	
	@Test
	public void sameAsReference() {
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-01-08");
		LocalDate date = start;
		
		while (!date.equals(end)) {
			String calculatedTweet = Europe.getInstance().getTweetText(date);
			String referenceTweet = Day.fromDayOfWeek(date.getDayOfWeek(), DaysEU.MONDAY).text();
			assertEquals(referenceTweet, calculatedTweet);
			
			date = date.plusDays(1);
		}
	}
	
	private int getOffset(LocalDate date, int hour) {
		return Europe.getInstance().getLocalHour(date, hour) - hour;
	}
}
