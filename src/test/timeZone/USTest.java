package timeZone;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysUS;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class USTest {
	
	@Test
	public void sameAsReference() {
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-01-08");
		LocalDate date = start;
		
		while (!date.equals(end)) {
			String calculatedTweet = US.getInstance().getTweetText(date);
			String referenceTweet = Day.fromDayOfWeek(date.getDayOfWeek(), DaysUS.MONDAY).text();
			assertEquals(referenceTweet, calculatedTweet);
			
			date.plusDays(1);
		}
	}
}
