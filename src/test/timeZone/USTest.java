package timeZone;

import referenceTweets.Day;
import referenceTweets.DaysUS;

public class USTest extends TimeZoneTest {

	@Override
	public TimeZone getTimeZone() {
		return US.getInstance();
	}

	@Override
	public Day getReferenceTimeZone() {
		return DaysUS.MONDAY;
	}
}
