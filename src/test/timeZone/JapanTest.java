package timeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import referenceTweets.Day;
import referenceTweets.DaysJP;

public class JapanTest {
	
	@Test
	public void sameAsReference() {
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-01-08");
		LocalDate date = start;
		
		while (!date.equals(end)) {
			String calculatedTweet = Japan.getInstance().getTweetText(date);
			String referenceTweet = Day.fromDayOfWeek(date.getDayOfWeek(), DaysJP.MONDAY).text();
			assertEquals(calculatedTweet, referenceTweet);
			
			date.plusDays(1);
		}
	}
}
