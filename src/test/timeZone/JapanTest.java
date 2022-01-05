package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysJP;

public class JapanTest {
	@Test
	public void testDstAdjustment() {
		int hour1 = 0, hour2 = 3;

		TimeZone japan = Japan.getInstance();
		
		LocalDate date1 = LocalDate.parse("2022-01-01");
		LocalDate date2 = US.getInstance().dstStarts;
		LocalDate date3 = US.getInstance().dstEnds;
		LocalDate date4 = LocalDate.parse("2022-12-31");
		
		int hour;
		
		hour = japan.getLocalHour(date1, hour1);
		assertEquals(japan.offsetFromPT, hour);
		
		hour = japan.getLocalHour(date2, hour1);
		assertEquals(japan.offsetFromPT, hour);
		
		hour = japan.getLocalHour(date2, hour2);
		assertEquals(japan.offsetFromPT - 1, hour - hour2);
		
		hour = japan.getLocalHour(date3, hour1);
		assertEquals(japan.offsetFromPT - 1, hour);

		hour = japan.getLocalHour(date3, hour2);
		assertEquals(japan.offsetFromPT, hour - hour2);
		
		hour = japan.getLocalHour(date4, hour1);
		assertEquals(japan.offsetFromPT, hour);
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
}
