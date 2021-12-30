package timeZone;

import java.time.LocalDate;
import java.time.Period;

import event.Event;

public class Europe extends TimeZone {
	static Europe instance;
	
	private Europe() {
		this.offsetFromPT = 9;
		this.dstStarts = LocalDate.parse("2022-03-27");
		this.dstEnds = LocalDate.parse("2022-10-30");
		this.timeZoneName = "CET";
	}
	
	static {
		instance = new Europe();
	}
	
	@Override
	Event[] getEventsForDay(LocalDate date) {
		// TODO this shit
		return getEventsForDay(date, offsetFromPT);
	}

	@Override
	public String getPostTime(LocalDate date) {
		String postDate = date.minus(Period.ofDays(1)).toString();
		String postTime;
		if (date.isBefore(dstStarts) || date.isAfter(dstEnds)) {
			postTime = "T21:00:00Z";
		}
		else {
			postTime = "T20:00:00Z";
		}
		return postDate + postTime;
	}

	public static TimeZone getInstance() {
		return instance;
	}
}
