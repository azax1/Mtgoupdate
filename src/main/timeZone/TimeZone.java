package timeZone;

import event.Event;
import event.EventType;

import static event.EventType.*;
import event.ScheduleInfo;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
public abstract class TimeZone {
	int offsetFromPT;
	LocalDate dstStarts;
	LocalDate dstEnds;
	String name;
	
	/*
	 * Returns the event schedule for this date in this time zone.
	 */
	public String getTweetText(LocalDate date) {
		List<Event> events = getEventsForDay(date);
		
		StringBuilder sb = new StringBuilder();
		sb.append(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
		sb.append(" Tournament Schedule (");
		sb.append(name);
		sb.append(")\n");
		sb.append("\n");
		sb.append(prettyPrint(events));
		
		return sb.toString();
	}
	
	/*
	 * The indicator function of DST in this time zone.
	 */
	public abstract int getExtraOffset(LocalDate date, int hour);
	
	/*
	 * Returns the hour (in this time zone) of the given event occurring on the given date in PT.
	 */
	public int getLocalHour(LocalDate date, int hour) {
		return
			hour +
			offsetFromPT +
			getExtraOffset(date, hour) -
			US.getInstance().getExtraOffset(date, hour);

	}
	
	/*
	 * Returns the UTC time when the event schedule should be posted on this date.
	 */
	public abstract String getPostTime(LocalDate date);
	
	/*
	 * Specifies how dd-MM information is formatted (for special event announcement tweets).
	 */
	public abstract DateTimeFormatter getDateTimeFormatter();

	/**
	 * Gets the list of events for the given date given its offset from PT.
	 */
	List<Event> getEventsForDay(LocalDate date) {
		List<Event> ret = new ArrayList<>();
		DayOfWeek day = date.getDayOfWeek();
		List<Event> firstDay, secondDay;
		List<Event> firstSpecial, secondSpecial;
		int offset = 0;
		
		// In general, one day's events straddle two different days in the master schedule
		// Both because of a time zone offset and because midnight is included in the day at both ends
		if (!(this instanceof US)) {
			firstDay = ScheduleInfo.getMasterEventSchedule(date.minus(Period.ofDays(1)).getDayOfWeek());
			secondDay = ScheduleInfo.getMasterEventSchedule(day);
			
			firstSpecial = specialSchedule.get(date.minus(Period.ofDays(1)));
			secondSpecial = specialSchedule.get(date);
		} else {
			firstDay = ScheduleInfo.getMasterEventSchedule(day);
			secondDay =  ScheduleInfo.getMasterEventSchedule(date.plus(Period.ofDays(1)).getDayOfWeek());
			
			firstSpecial = specialSchedule.get(date);
			secondSpecial = specialSchedule.get(date.plus(Period.ofDays(1)));
			
			offset = 24; // US is weird
		}
		
		specialify(date, firstDay, firstSpecial);
		specialify(date.plus(Period.ofDays(1)), secondDay, secondSpecial);
		
		for (Event e : firstDay) {
			int hour = getLocalHour(date, e.getHour());
			if (hour + offset < 24) {
				continue;
			}
			ret.add(Event.builder()
						.hour(hour % 24)
						.format(e.getFormat())
						.eventType(e.getEventType())
						.build()
			);
		}
		for (Event e : secondDay) {
			int hour = getLocalHour(date, e.getHour());
			if (hour + offset > 24) {
				break;
			}
			ret.add(Event.builder()
					.hour(hour + offset)
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
	
	/*
	 * Modifies regular schedule to accurately reflect special events held on that day
	 */
	private void specialify(LocalDate date, List<Event> regular, List<Event> special) {
		if (special != null) {
			for (Event event : special) {
				EventType type = event.getEventType();
				if (type == PTQ || type == SUPER_PTQ) { // just insert into schedule
					int i;
					for (i = 0; i < regular.size() &&
							regular.get(i).getHour() <= event.getHour(); i++) {}
					regular.add(i, event);
				} else if (type == SHOWCASE_CHALLENGE) { // replaces corresponding Challenge
					for (int i = 0; i < regular.size(); i++) {
						Event e = regular.get(i);
						if (e.getEventType() == CHALLENGE && e.getFormat() == event.getFormat()) {
							regular.remove(i);
							break;
						}
					}
					
					// Now insert the Showcase Challenge
					// the Pauper Showcase Challenge is at a different time than regular Challenge
					// so have to search anew for insertion point
					int i;
					for (i = 0; i < regular.size() &&
							regular.get(i).getHour() <= event.getHour(); i++) {}
					regular.add(i, event);
				} else {
					throw new UnsupportedOperationException("Unexpected special event type " + type);
				}
			}
		}
		LocalDate lcqStart = ScheduleInfo.getLCQStartDate();
		LocalDate lcqEnd = ScheduleInfo.getLCQEndDate();
		if (date.isBefore(lcqStart) || date.isAfter(lcqEnd)) {
			return;
		} else if (date.equals(lcqEnd)) {
			for (int i = 0; i < regular.size(); i++) {
				Event e = regular.get(i);
				if (e.getHour() >= 11) { // magic time when LCQs end on Wednesday
					break;
				}
				if (e.getFormat().isShowcaseFormat() && e.getEventType() == PRELIM) {
					regular.remove(i);
					regular.add(i, new Event(e.getHour(), e.getFormat(), LCQ));
				}
			}
		} else {
			for (int i = 0; i < regular.size(); i++) {
				Event e = regular.get(i);
				if (e.getFormat().isShowcaseFormat() && e.getEventType() == PRELIM) {
					regular.remove(i);
					regular.add(i, new Event(e.getHour(), e.getFormat(), LCQ));
				}
			}
		}
	}
	
	public final static Map<LocalDate, List<Event>> specialSchedule = ScheduleInfo.getSpecialEventSchedule();
}
