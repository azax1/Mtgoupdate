package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;

public abstract class TimeZoneTest {
	@Test
	public void sameAsReference() {
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-01-08");
		LocalDate date = start;
		
		while (!date.equals(end)) {
			String calculatedTweet = getTimeZone().getTweetText(date);
			String referenceTweet = Day.fromDayOfWeek(date.getDayOfWeek(), getReferenceTimeZone()).text();
			assertEquals(referenceTweet, calculatedTweet);
			
			date = date.plusDays(1);
		}
	}
	
	protected abstract TimeZone getTimeZone();
	protected abstract Day getReferenceTimeZone();
	
	protected int getOffset(LocalDate date, int hour) {
		return getTimeZone().getLocalHour(date, hour) - hour;
	}
}
