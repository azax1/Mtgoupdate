package timeZone;

import event.Event;
import event.EventType;
import event.Format;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
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
		Event[] events = getEventsForDay(date);
		
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
	
	Event[] getEventsForDay(LocalDate date) {
		return getEventsForDay(date, offsetFromPT);
	}
	
	/**
	 * Gets the list of events for the given date given its offset from PT.
	 */
	Event[] getEventsForDay(LocalDate date, int offset) {
		Event[] ret = new Event[25];
		DayOfWeek day = date.getDayOfWeek();
		DayOfWeek firstDay, secondDay;
		
		// In general, one day's events straddle two different days in the master schedule
		// Both because of a time zone offset and because midnight is included in the day at both ends
		
		if (offset > 0) {
			firstDay = date.minus(Period.ofDays(1)).getDayOfWeek();
			secondDay = day;
		} else {
			firstDay = day;
			secondDay = date.plus(Period.ofDays(1)).getDayOfWeek();
			
		}
		if (offset == 0) {
			offset = 24; // PT has to grab all 24 hours from the first day, not 0
		}
		for (int i = 0; i < offset; i++) {
			Event e = masterSchedule[firstDay.getValue() - 1][24 - offset + i];
			ret[i] = e;
		}
		for (int i = offset; i <= 24; i++) {
			Event e = masterSchedule[secondDay.getValue() - 1][i - offset];
			ret[i] = e;
		}
		return ret;
	}
	
	String prettyPrint(Event[] events) {
		boolean firstLine = true; // avoid trailing newline
		StringBuilder sb = new StringBuilder();
		for (int hour = 0; hour < events.length; hour++) {
			Event event = events[hour];
			if (event != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					sb.append("\n");
				}
				sb.append(Event.prettyPrint(hour, event));
			}
		}
		return sb.toString();
	}
	
	public final static Event[][] masterSchedule = initializeMasterSchedule();
	
	private static Event[][] initializeMasterSchedule() {
		Event[] Monday = new Event[24];
		Monday[0] = new Event(Format.PIONEER);
		Monday[3] = new Event(Format.LIMITED);
		Monday[7] = new Event(Format.MODERN);
		Monday[9] = new Event(Format.LIMITED);
		Monday[11] = new Event(Format.LIMITED);
		Monday[14] = new Event(Format.MODERN);
		Monday[18] = new Event(Format.LIMITED);
		Monday[19] = new Event(Format.PIONEER);
		Monday[21] = new Event(Format.VINTAGE);
		
		Event[] Tuesday = new Event[24];
		Tuesday[0] = new Event(Format.PAUPER);
		Tuesday[3] = new Event(Format.MODERN);
		Tuesday[7] = new Event(Format.PIONEER);
		Tuesday[9] = new Event(Format.LIMITED);
		Tuesday[11] = new Event(Format.PAUPER);
		Tuesday[14] = new Event(Format.STANDARD);
		Tuesday[15] = new Event(Format.LIMITED);
		Tuesday[19] = new Event(Format.LEGACY);
		
		Event[] Wednesday = new Event[24];
		Wednesday[0] = new Event(Format.LIMITED);
		Wednesday[3] = new Event(Format.LEGACY);
		Wednesday[7] = new Event(Format.STANDARD);
		Wednesday[11] = new Event(Format.PIONEER);
		Wednesday[14] = new Event(Format.MODERN);
		Wednesday[15] = new Event(Format.LIMITED);
		Wednesday[18] = new Event(Format.VINTAGE);
		
		Event[] Thursday = new Event[24];
		Thursday[0] = new Event(Format.MODERN);
		Thursday[3] = new Event(Format.PIONEER);
		Thursday[7] = new Event(Format.PAUPER);
		Thursday[11] = new Event(Format.MODERN);
		Thursday[12] = new Event(Format.LIMITED);
		Thursday[15] = new Event(Format.LEGACY);
		Thursday[16] = new Event(Format.LIMITED);
		Thursday[19] = new Event(Format.STANDARD);
		Thursday[21] = new Event(Format.PIONEER);
		
		Event[] Friday = new Event[24];
		Friday[0] = new Event(Format.LEGACY);
		Friday[2] = new Event(Format.LIMITED);
		Friday[4] = new Event(Format.MODERN);
		Friday[7] = new Event(Format.LEGACY);
		Friday[10] = new Event(Format.LIMITED);
		Friday[11] = new Event(Format.VINTAGE);
		Friday[15] = new Event(Format.PIONEER);
		Friday[18] = new Event(Format.LIMITED);
		Friday[19] = new Event(Format.MODERN);
		
		Event[] Saturday = new Event[24];
		Saturday[0] = new Event(Format.PIONEER);
		Saturday[1] = new Event(Format.LIMITED);
		Saturday[2] = new Event(Format.PAUPER, EventType.CHALLENGE);
		Saturday[4] = new Event(Format.LEGACY, EventType.CHALLENGE);
		Saturday[6] = new Event(Format.STANDARD, EventType.CHALLENGE);
		Saturday[8] = new Event(Format.MODERN, EventType.CHALLENGE);
		Saturday[10] = new Event(Format.VINTAGE, EventType.CHALLENGE);
		Saturday[12] = new Event(Format.LIMITED);
		Saturday[14] = new Event(Format.PIONEER, EventType.CHALLENGE);
		Saturday[16] = new Event(Format.LIMITED);

		Event[] Sunday = new Event[24];
		Sunday[0] = new Event(Format.VINTAGE, EventType.CHALLENGE);
		Sunday[4] = new Event(Format.MODERN, EventType.CHALLENGE);
		Sunday[6] = new Event(Format.PIONEER, EventType.CHALLENGE);
		Sunday[8] = new Event(Format.LEGACY, EventType.CHALLENGE);
		Sunday[10] = new Event(Format.PAUPER, EventType.CHALLENGE);
		Sunday[12] = new Event(Format.LIMITED);
		Sunday[14] = new Event(Format.STANDARD, EventType.CHALLENGE);
		Sunday[15] = new Event(Format.VINTAGE);
		Sunday[16] = new Event(Format.LIMITED);
				
		 return new Event[][] { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday };
	}
}
