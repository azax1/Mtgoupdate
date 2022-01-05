package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysEU;

public class EuropeTest {
	@Test
	public void testDstAdjustment() {
		int hour1 = 0, hour2 = 3;

		TimeZone europe = Europe.getInstance();
		
		LocalDate date1 = LocalDate.parse("2022-01-01");
		LocalDate date2 = US.getInstance().dstStarts;
		LocalDate date3 = europe.dstStarts;
		LocalDate date4 = europe.dstEnds;
		LocalDate date5 = US.getInstance().dstEnds;
		LocalDate date6 = LocalDate.parse("2022-12-31");
		
		int hour;
		
		hour = europe.getLocalHour(date1, hour1);
		assertEquals(europe.offsetFromPT, hour);
		
		hour = europe.getLocalHour(date2, hour1);
		assertEquals(europe.offsetFromPT, hour);

		hour = europe.getLocalHour(date2, hour2);
		assertEquals(europe.offsetFromPT - 1, hour - hour2);
		
		hour = europe.getLocalHour(date3, hour1);
		assertEquals(europe.offsetFromPT - 1, hour);
		
		hour = europe.getLocalHour(date3, hour2);
		assertEquals(europe.offsetFromPT, hour - hour2);
		
		hour = europe.getLocalHour(date4, hour1);
		assertEquals(europe.offsetFromPT, hour);
		
		hour = europe.getLocalHour(date4, hour2);
		assertEquals(europe.offsetFromPT - 1, hour - hour2);
		
		hour = europe.getLocalHour(date5,  hour1);
		assertEquals(europe.offsetFromPT - 1, hour);
		
		hour = europe.getLocalHour(date5, hour2);
		assertEquals(europe.offsetFromPT, hour - hour2);
		
		hour = europe.getLocalHour(date6, hour1);
		assertEquals(europe.offsetFromPT, hour);
		
		
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
}
