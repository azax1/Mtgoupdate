package referenceTweets;

import java.time.DayOfWeek;

/*
 * Represents the regular schedule for a single day of the week. Implementations specify a time zone.
 */
public interface Day {
	/*
	 * The body of the tweet to be posted on this day.
	 */
	String text();
	
	/*
	 * What UTC time this day's data should be posted.
	 */
	String postTime();
	
	/*
	 * Translates a DayOfWeek object into that day's schedule in the time zone of the example passed in.
	 */
	public static Day fromDayOfWeek(DayOfWeek day, Day example) {
		if (example instanceof DaysUS) {
			switch (day) {
				case MONDAY:
					return DaysUS.MONDAY;
				case TUESDAY:
					return DaysUS.TUESDAY;
				case WEDNESDAY:
					return DaysUS.WEDNESDAY;
				case THURSDAY:
					return DaysUS.THURSDAY;
				case FRIDAY:
					return DaysUS.FRIDAY;
				case SATURDAY:
					return DaysUS.SATURDAY;
				case SUNDAY:
					return DaysUS.SUNDAY;
				default:
					return null;
			}
		} else if (example instanceof DaysEU) {
			switch (day) {
				case MONDAY:
					return DaysEU.MONDAY;
				case TUESDAY:
					return DaysEU.TUESDAY;
				case WEDNESDAY:
					return DaysEU.WEDNESDAY;
				case THURSDAY:
					return DaysEU.THURSDAY;
				case FRIDAY:
					return DaysEU.FRIDAY;
				case SATURDAY:
					return DaysEU.SATURDAY;
				case SUNDAY:
					return DaysEU.SUNDAY;
				default:
					return null;
			}
		} else if (example instanceof DaysJP) {
			switch (day) {
			case MONDAY:
				return DaysJP.MONDAY;
			case TUESDAY:
				return DaysJP.TUESDAY;
			case WEDNESDAY:
				return DaysJP.WEDNESDAY;
			case THURSDAY:
				return DaysJP.THURSDAY;
			case FRIDAY:
				return DaysJP.FRIDAY;
			case SATURDAY:
				return DaysJP.SATURDAY;
			case SUNDAY:
				return DaysJP.SUNDAY;
			default:
				return null;
			}
		}
		return null;
	}
}