package timeZone;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import event.Event;

public class Japan extends TimeZone {
	static Japan instance;
	
	private Japan() {
		this.offsetFromPT = 17;
		this.dstStarts = null;
		this.dstEnds = null;
		this.timeZoneName = "JST";
	}
	
	static {
		instance = new Japan();
	}
	
	@Override
	List<Event> getEventsForDay(LocalDate date) {
		TimeZone UnitedStates = US.getInstance();
		if (date.isBefore(UnitedStates.dstStarts) || date.isAfter(UnitedStates.dstEnds)) {
			return getEventsForDay(date, offsetFromPT);
		} else {
			return getEventsForDay(date, offsetFromPT - 1);
		}
	}

	@Override
	public String getPostTime(LocalDate date) {
		return date.minus(Period.ofDays(1)).toString() + "T13:00:00Z";
	}

	public static TimeZone getInstance() {
		return instance;
	}
}
