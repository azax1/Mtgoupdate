package timeZone;

import event.Event;
import event.ScheduleInfo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class TimeZone {
	int offsetFromPT;
	LocalDate dstStarts;
	LocalDate dstEnds;
	String timeZoneName;
	
	/*
	 * Returns the event schedule for this date in this time zone.
	 */
	public String getTweetText(LocalDate date) {
		List<Event> events = getEventsForDay(date);
		
		StringBuilder sb = new StringBuilder();
		sb.append(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
		sb.append(" Tournament Schedule (");
		sb.append(timeZoneName);
		sb.append(")\n");
		sb.append("\n");
		sb.append(prettyPrint(events));
		
		return sb.toString();
	}
	
	/*
	 * Returns the UTC time when the event schedule should be posted on this date.
	 */
	public abstract String getPostTime(LocalDate date);
	
	List<Event> getEventsForDay(LocalDate date) {
		return getEventsForDay(date, offsetFromPT);
	}
	
	/**
	 * Gets the list of events for the given date given its offset from PT.
	 */
	List<Event> getEventsForDay(LocalDate date, int offset) {
		List<Event> ret = new ArrayList<>();
		DayOfWeek day = date.getDayOfWeek();
		List<Event> firstDay, secondDay;
		
		// In general, one day's events straddle two different days in the master schedule
		// Both because of a time zone offset and because midnight is included in the day at both ends
		
		if (offset > 0) {
			firstDay = masterSchedule[date.minus(Period.ofDays(1)).getDayOfWeek().getValue() - 1];
			secondDay = masterSchedule[day.getValue() - 1];
		} else {
			firstDay = masterSchedule[day.getValue() - 1];;
			secondDay =  masterSchedule[date.plus(Period.ofDays(1)).getDayOfWeek().getValue() - 1];
			
		}
		for (Event e : firstDay) {
			if (e.getHour() < (24 - offset) % 24) {
				continue;
			}
			ret.add(Event.builder()
						.hour((e.getHour() + offset) % 24)
						.format(e.getFormat())
						.eventType(e.getEventType())
						.build()
			);
		}
		for (Event e : secondDay) {
			if (e.getHour() > (24 - offset) % 24) {
				break;
			}
			int newHour = e.getHour() + offset;
			ret.add(Event.builder()
					.hour(newHour != 0 ? newHour : 24)
					.format(e.getFormat())
					.eventType(e.getEventType())
					.build()
			);
		}
		return ret;
	}
	
	String prettyPrint(List<Event> events) {
		boolean firstLine = true; // avoid trailing newline
		StringBuilder sb = new StringBuilder();
		for (Event e : events) {
			if (!firstLine) {
				sb.append("\n");
			}
			firstLine = false;
			sb.append(e.prettyPrint());
		}
		return sb.toString();
	}
	
	public final static List<Event>[] masterSchedule = ScheduleInfo.getMasterEventSchedule();
}
