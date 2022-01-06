package event;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timeZone.TimeZone;

import static event.Format.*;
import static event.EventType.*;

public class ScheduleInfo {
	public static List<Event> getMasterEventSchedule(DayOfWeek day) {
		if (day == DayOfWeek.MONDAY) {
			List<Event> Monday = new ArrayList<Event>();
			
			Monday.add(new Event(0, PIONEER));
			Monday.add(new Event(3, LIMITED));
			Monday.add(new Event(7, MODERN));
			Monday.add(new Event(9, LIMITED));
			Monday.add(new Event(11, LEGACY));
			Monday.add(new Event(15, MODERN));
			Monday.add(new Event(18, LIMITED));
			Monday.add(new Event(19, PIONEER));
			Monday.add(new Event(21, VINTAGE));
			
			return Monday;
		} else if (day == DayOfWeek.TUESDAY) {
			List<Event> Tuesday = new ArrayList<Event>();
			
			Tuesday.add(new Event(0, PAUPER));
			Tuesday.add(new Event(3, MODERN));
			Tuesday.add(new Event(7, PIONEER));
			Tuesday.add(new Event(9, LIMITED));
			Tuesday.add(new Event(11, PAUPER));
			Tuesday.add(new Event(15, STANDARD));
			Tuesday.add(new Event(16, LIMITED));
			Tuesday.add(new Event(19, LEGACY));
			
			return Tuesday;
		} else if (day == DayOfWeek.WEDNESDAY) {
			List<Event> Wednesday = new ArrayList<Event>();
			
			Wednesday.add(new Event(0, LIMITED));
			Wednesday.add(new Event(3, LEGACY));
			Wednesday.add(new Event(7, STANDARD));
			Wednesday.add(new Event(11, PIONEER));
			Wednesday.add(new Event(15, MODERN));
			Wednesday.add(new Event(16, LIMITED));
			Wednesday.add(new Event(19, VINTAGE));
			
			return Wednesday;
		} else if (day == DayOfWeek.THURSDAY) {
			List<Event> Thursday = new ArrayList<Event>();
			
			Thursday.add(new Event(0, MODERN));
			Thursday.add(new Event(3, PIONEER));
			Thursday.add(new Event(7, PAUPER));
			Thursday.add(new Event(11, MODERN));
			Thursday.add(new Event(12, LIMITED));
			Thursday.add(new Event(15, LEGACY));
			Thursday.add(new Event(16, LIMITED));
			Thursday.add(new Event(19, STANDARD));
			Thursday.add(new Event(21, PIONEER));
			
			return Thursday;
		} else if (day == DayOfWeek.FRIDAY) {
			List<Event> Friday = new ArrayList<Event>();
			
			Friday.add(new Event(0, LEGACY));
			Friday.add(new Event(2, LIMITED));
			Friday.add(new Event(4, MODERN));
			Friday.add(new Event(7, LEGACY));
			Friday.add(new Event(10, LIMITED));
			Friday.add(new Event(11, VINTAGE));
			Friday.add(new Event(15, PIONEER));
			Friday.add(new Event(18, LIMITED));
			Friday.add(new Event(19, MODERN));
			
			return Friday;
		} else if (day == DayOfWeek.SATURDAY) {
			List<Event> Saturday = new ArrayList<Event>();
			
			Saturday.add(new Event(0, PIONEER));
			Saturday.add(new Event(1, LIMITED));
			Saturday.add(new Event(2, PAUPER, EventType.CHALLENGE));
			Saturday.add(new Event(4, LEGACY, EventType.CHALLENGE));
			Saturday.add(new Event(6, STANDARD, EventType.CHALLENGE));
			Saturday.add(new Event(8, MODERN, EventType.CHALLENGE));
			Saturday.add(new Event(10, VINTAGE, EventType.CHALLENGE));
			Saturday.add(new Event(12, LIMITED));
			Saturday.add(new Event(14, PIONEER, EventType.CHALLENGE));
			Saturday.add(new Event(16, LIMITED));
			
			return Saturday;
		} else {
			List<Event> Sunday = new ArrayList<Event>();
			
			Sunday.add(new Event(0, VINTAGE, EventType.CHALLENGE));
			Sunday.add(new Event(4, MODERN, EventType.CHALLENGE));
			Sunday.add(new Event(6, PIONEER, EventType.CHALLENGE));
			Sunday.add(new Event(8, LEGACY, EventType.CHALLENGE));
			Sunday.add(new Event(10, PAUPER, EventType.CHALLENGE));
			Sunday.add(new Event(12, LIMITED));
			Sunday.add(new Event(14, STANDARD, EventType.CHALLENGE));
			Sunday.add(new Event(15, VINTAGE));
			Sunday.add(new Event(16, LIMITED));
			
			return Sunday;
		}
	}
	
	/*
	 * Returns the current season's list of PTQs, Super PTQs, and Showcase Challenges.
	 */
	public static Map<LocalDate, List<Event>> getSpecialEventSchedule() {
		Map<LocalDate, List<Event>> map = new LinkedHashMap<>(); // iteration order = insertion order
		
		map.put(LocalDate.parse("2021-12-17"), listify(new Event(23, LIMITED.with("VOW"), SUPER_PTQ)));
		map.put(LocalDate.parse("2021-12-19"), listify(new Event(1, MODERN, SUPER_PTQ)));
		map.put(LocalDate.parse("2021-12-26"), listify(new Event(7, LIMITED.with("VOW"), SUPER_PTQ)));
		map.put(LocalDate.parse("2022-01-02"), listify(new Event(11, PIONEER, PTQ)));
		map.put(LocalDate.parse("2022-01-07"), listify(new Event(14, LIMITED.with("VOW"), PTQ)));
		map.put(LocalDate.parse("2022-01-14"), listify(new Event(14, PIONEER, SUPER_PTQ)));
		
		map.put(LocalDate.parse("2022-01-15"),
				listify(new Event(5, LEGACY, SUPER_PTQ), new Event(15, MODERN, SUPER_PTQ)));
		
		map.put(LocalDate.parse("2022-01-16"), listify(new Event(7, LIMITED.with("VOW"), SUPER_PTQ)));
		map.put(LocalDate.parse("2022-01-22"), listify(new Event(8, MODERN, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-01-23"), listify(new Event(6, PIONEER, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-01-28"), listify(new Event(14, PAUPER, SUPER_PTQ)));
		map.put(LocalDate.parse("2022-01-29"), listify(new Event(10, VINTAGE, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-01-30"), listify(new Event(8, LEGACY, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-02-19"), listify(new Event(8, MODERN, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-02-20"), listify(new Event(6, PIONEER, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-03-05"), listify(new Event(10, VINTAGE, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-03-06"), listify(new Event(8, LEGACY, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-04-02"), listify(new Event(8, MODERN, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-04-03"), listify(new Event(6, PIONEER, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-04-09"), listify(new Event(10, VINTAGE, SHOWCASE_CHALLENGE)));
		map.put(LocalDate.parse("2022-04-10"), listify(new Event(8, LEGACY, SHOWCASE_CHALLENGE)));
		
		return map;
	}
	
	public static Map<LocalDate, String> getSpecialEventAnnouncementStrings
	(LocalDate startDate, LocalDate endDate, TimeZone timeZone) {
		Map<LocalDate, LinkedHashMap<LocalDate, List<Event>>> eventsBySunday =
				new LinkedHashMap<LocalDate, LinkedHashMap<LocalDate, List<Event>>>();
		Map<LocalDate, List<Event>> specialEventSchedule = getSpecialEventSchedule();
		
		for (Map.Entry<LocalDate, List<Event>> entry : specialEventSchedule.entrySet()) {
			LocalDate date = entry.getKey();
			List<Event> theseEvents = entry.getValue();
			if (date.isBefore(startDate)) {
				continue;
			} else if (!date.isBefore(endDate)) {
				break;
			} else {
				for (Event event : theseEvents) {
					TemporalAdjuster previousSundayAdjuster = TemporalAdjusters.previous(DayOfWeek.SUNDAY);
					int hour = timeZone.getLocalHour(date, event.getHour());
					if (hour >= 24) {
						date = date.plus(Period.ofDays(hour / 24));
						hour %= 24;
					} else if (hour < 0) { // should be impossible
						date = date.minus(Period.ofDays(1));
						hour = hour + 24;
					}
					event = Event.builder()
								.hour(hour)
								.format(event.getFormat())
								.eventType(event.getEventType())
								.build();
					LocalDate prevSunday = date.with(previousSundayAdjuster);
					if (eventsBySunday.containsKey(prevSunday)) {
						Map<LocalDate, List<Event>> value = eventsBySunday.get(prevSunday);
						if (value.containsKey(date)) {
							value.get(date).add(event);
						} else {
							List<Event> list = new LinkedList<>();
							list.add(event);
							value.put(date, list);
						}
					} else {
						LinkedHashMap<LocalDate, List<Event>> value = new LinkedHashMap<>();
						List<Event> valueOfValue = new LinkedList<>();
						valueOfValue.add(event);
						value.put(date, valueOfValue);
						eventsBySunday.put(prevSunday, value);
					}
				}
			}
		}
		Map<LocalDate, String> ret = new LinkedHashMap<>();
		for (LocalDate date : eventsBySunday.keySet()) {
			StringBuilder sb = new StringBuilder();
			LinkedHashMap<LocalDate, List<Event>> events = eventsBySunday.get(date);
			sb.append("Special events this week (")
				.append(timeZone.getName())
				.append("):");
			for (LocalDate eventDate : events.keySet()) {
				sb.append("\n\n");
				sb.append(eventDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US))
					.append(" ")
					.append(eventDate.format(timeZone.getDateTimeFormatter()));
				for (Event event : events.get(eventDate)) {
					sb.append("\n")
						.append(event.prettyPrint());
					if (event.getEventType() == EventType.SHOWCASE_CHALLENGE && event.getFormat() != Format.PAUPER) {
						sb.append(" (replaces regular Challenge)");
					}
				}
			}
			
			ret.put(date, sb.toString());
		}
		return ret;
	}
	
	public static LocalDate getLCQStartDate() {
		return LocalDate.parse("2022-04-10");
	}
	
	public static LocalDate getLCQEndDate() {
		return LocalDate.parse("2022-04-13");
	}
	
	private static List<Event> listify(Event... events) {
		List<Event> ret = new ArrayList<>();
		for (int i = 0; i < events.length; i++) {
			ret.add(events[i]);
		}
		return ret;
	}
}
