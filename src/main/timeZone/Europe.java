package timeZone;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

	public static TimeZone getInstance() {
		return instance;
	}
	
	@Override
	List<Event> getEventsForDay(LocalDate date) {
		TimeZone unitedStates = US.getInstance();
		int offsetFromOffset = 0;
		
		if (date.isBefore(unitedStates.dstStarts) || date.isAfter(unitedStates.dstEnds)) {
			offsetFromOffset--;
		}
		if (date.isBefore(dstStarts) || date.isAfter(unitedStates.dstEnds)) {
			offsetFromOffset++;
		}
		return getEventsForDay(date, offsetFromPT + offsetFromOffset);
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

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM");
	}
}
