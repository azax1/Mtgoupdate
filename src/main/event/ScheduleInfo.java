package event;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timeZone.TimeZone;

import static event.Format.*;
import static event.EventType.*;

public class ScheduleInfo {
	public static List<Event> getMasterEventSchedule(LocalDate date) {
		DayOfWeek day = date.getDayOfWeek();
		if (day == DayOfWeek.MONDAY) {
			List<Event> Monday = new ArrayList<Event>();
			
			Monday.add(new Event(0, PIONEER));
			Monday.add(new Event(3, LIMITED));
			Monday.add(new Event(4, LEGACY));
			Monday.add(new Event(7, MODERN));
			Monday.add(new Event(9, LIMITED));
			Monday.add(new Event(11, LEGACY));
			Monday.add(new Event(15, MODERN));
			Monday.add(new Event(17, VINTAGE));
			Monday.add(new Event(18, LIMITED));
			Monday.add(new Event(19, PIONEER));
			Monday.add(new Event(21, MODERN));
			
			return Monday;
		} else if (day == DayOfWeek.TUESDAY) {
			List<Event> Tuesday = new ArrayList<Event>();
			
			Tuesday.add(new Event(0, LIMITED));
			Tuesday.add(new Event(3, MODERN));
			Tuesday.add(new Event(7, PIONEER));
			Tuesday.add(new Event(9, LIMITED));
			Tuesday.add(new Event(11, MODERN));
			Tuesday.add(new Event(15, PIONEER));
			Tuesday.add(new Event(16, LIMITED));
			Tuesday.add(new Event(17, MODERN));
			Tuesday.add(new Event(19, LEGACY));
			
			return Tuesday;
		} else if (day == DayOfWeek.WEDNESDAY) {
			List<Event> Wednesday = new ArrayList<Event>();
			
			Wednesday.add(new Event(0, LIMITED));
			Wednesday.add(new Event(3, LEGACY));
			Wednesday.add(new Event(7, LIMITED));
			Wednesday.add(new Event(9, MODERN));
			Wednesday.add(new Event(13, PIONEER));
			Wednesday.add(new Event(15, MODERN));
			Wednesday.add(new Event(16, LIMITED));
			Wednesday.add(new Event(18, VINTAGE));
			Wednesday.add(new Event(21, LIMITED));
			
			return Wednesday;
		} else if (day == DayOfWeek.THURSDAY) {
			List<Event> Thursday = new ArrayList<Event>();
			
			Thursday.add(new Event(0, MODERN));
			Thursday.add(new Event(3, PIONEER));
			Thursday.add(new Event(7, LIMITED));
			Thursday.add(new Event(11, MODERN));
			Thursday.add(new Event(12, LIMITED));
			Thursday.add(new Event(15, LEGACY));
			Thursday.add(new Event(16, LIMITED));
			Thursday.add(new Event(17, STANDARD));
			Thursday.add(new Event(19, MODERN));
			Thursday.add(new Event(21, PIONEER));
			
			return Thursday;
		} else if (day == DayOfWeek.FRIDAY) {
			List<Event> Friday = new ArrayList<Event>();

			Friday.add(new Event(0, LIMITED));
			Friday.add(new Event(2, LIMITED));
			Friday.add(new Event(4, MODERN));
			Friday.add(new Event(7, LEGACY));
			Friday.add(new Event(10, LIMITED));
			Friday.add(new Event(11, VINTAGE));
			Friday.add(new Event(14, PIONEER, EventType.CHALLENGE));
			Friday.add(new Event(16, LIMITED, EventType.CHALLENGE_32));
			Friday.add(new Event(18, MODERN, EventType.CHALLENGE));
			
			return Friday;
		} else if (day == DayOfWeek.SATURDAY) {
			List<Event> Saturday = new ArrayList<Event>();
			
			Saturday.add(new Event(0, PIONEER));
			Saturday.add(new Event(1, LIMITED, EventType.CHALLENGE_32));
			Saturday.add(new Event(2, PAUPER, EventType.CHALLENGE));
			Saturday.add(new Event(4, LEGACY, EventType.CHALLENGE_32));
			Saturday.add(new Event(6, STANDARD, EventType.CHALLENGE_32));
			Saturday.add(new Event(8, MODERN, EventType.CHALLENGE_96));
			Saturday.add(new Event(10, VINTAGE, EventType.CHALLENGE));
			Saturday.add(new Event(12, LIMITED, EventType.CHALLENGE_32));
			Saturday.add(new Event(14, PIONEER, EventType.CHALLENGE));
			Saturday.add(new Event(16, LIMITED));
			Saturday.add(new Event(18, MODERN, EventType.CHALLENGE));
			
			return Saturday;
		} else {
			List<Event> Sunday = new ArrayList<Event>();
			
			Sunday.add(new Event(0, VINTAGE, EventType.CHALLENGE));
			Sunday.add(new Event(1, LIMITED, EventType.CHALLENGE_32));
			Sunday.add(new Event(4, MODERN, EventType.CHALLENGE_96));
			Sunday.add(new Event(6, PIONEER, EventType.CHALLENGE));
			Sunday.add(new Event(8, LEGACY, EventType.CHALLENGE));
			Sunday.add(new Event(9, MODERN, EventType.PRELIM));
			Sunday.add(new Event(10, PAUPER, EventType.CHALLENGE));
			Sunday.add(new Event(12, LIMITED, EventType.CHALLENGE_32));
			Sunday.add(new Event(13, PIONEER, EventType.CHALLENGE_32));
			Sunday.add(new Event(14, STANDARD, EventType.CHALLENGE_96));
			Sunday.add(new Event(16, LIMITED));
			Sunday.add(new Event(18, MODERN, EventType.CHALLENGE_32));
			
			return Sunday;
		}
	}
	
	/*
	 * Returns the current season's list of PTQs, Super PTQs, and Showcase Challenges.
	 */
	public static Map<LocalDate, List<Event>> getSpecialEventSchedule() {
		Map<LocalDate, List<Event>> map = new LinkedHashMap<>(); // iteration order = insertion order
		
		addAll(map, LocalDate.parse("2023-12-16"), new Event(7, LIMITED, "LCI", MOCS_OPEN));
		addAll(map, LocalDate.parse("2023-12-17"), new Event(7, LIMITED, "LCI", MOCS_OPEN));
		
		addAll(map, LocalDate.parse("2023-12-23"), new Event(7, PIONEER, SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-25"), new Event(14, LIMITED, "LCI", SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-26"), new Event(1, MODERN, SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-27"), new Event(7, MODERN, SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-28"), new Event(7, LEGACY, SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-29"), new Event(7, PIONEER, SUPER_RCQ));
		addAll(map, LocalDate.parse("2023-12-30"), new Event(7, LIMITED, "LCI", SUPER_RCQ));
		
		addAll(map, LocalDate.parse("2024-01-01"), new Event(7, STANDARD, RCQ));
		addAll(map, LocalDate.parse("2024-01-06"), new Event(8, MODERN, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-01-07"), new Event(6, PIONEER, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-01-14"), new Event(1, MODERN, RCQ));
		addAll(map, LocalDate.parse("2024-01-15"), new Event(7, MODERN, RCQ));
		addAll(map, LocalDate.parse("2024-01-20"), new Event(10, VINTAGE, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-01-21"), new Event(8, LEGACY, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-01-27"), new Event(7, PIONEER, RCQ));
		addAll(map, LocalDate.parse("2024-01-28"), new Event(7, LIMITED, "RVR", SUPER_RCQ));
		
		addAll(map, LocalDate.parse("2024-02-02"), new Event(14, LIMITED, "RVR", RCQ));
		addAll(map, LocalDate.parse("2024-02-10"), new Event(8, MODERN, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-02-11"), new Event(6, PIONEER, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-02-17"), new Event(10, VINTAGE, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-02-18"), new Event(8, LEGACY, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-02-19"), new Event(7, LIMITED, "MKM", RCQ));
		addAll(map, LocalDate.parse("2024-02-23"), new Event(14, LEGACY, RCQ));
		addAll(map, LocalDate.parse("2024-02-24"), new Event(7, LIMITED, "MKM", SUPER_RCQ));
		addAll(map, LocalDate.parse("2024-02-25"), new Event(1, LIMITED, "MKM", SUPER_RCQ));
		
		addAll(map, LocalDate.parse("2024-03-02"), new Event(7, PAUPER, RCQ));
		addAll(map, LocalDate.parse("2024-03-03"), new Event(7, VINTAGE, SUPER_RCQ));
		addAll(map, LocalDate.parse("2024-03-08"), new Event(14, LIMITED, "MKM", RCQ));
		addAll(map, LocalDate.parse("2024-03-09"), new Event(8, MODERN, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-03-10"), new Event(6, PIONEER, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-03-16"), new Event(10, VINTAGE, SHOWCASE_CHALLENGE));
		addAll(map, LocalDate.parse("2024-03-17"), new Event(8, LEGACY, SHOWCASE_CHALLENGE));
		
		addAll(map, LocalDate.parse("2024-04-06"), new Event(7, LIMITED, "MKM", MOCS_OPEN));
		addAll(map, LocalDate.parse("2024-04-07"), new Event(7, LIMITED, "MKM", MOCS_OPEN));
		
		return map;
	}
	
	public static Map<LocalDate, String> getSpecialEventAnnouncementStrings
	(LocalDate startDate, LocalDate endDate, TimeZone timeZone) {
		Map<LocalDate, LinkedHashMap<LocalDate, List<Event>>> eventsByReminderDay =
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
					if (event.eventType == LCQ) {
						continue;
					}
					int hour = timeZone.getLocalHour(date, event.getHour());
					if (hour >= 24) {
						date = date.plus(Period.ofDays(hour / 24));
						hour %= 24;
					} else if (hour < 0) { // should be impossible with current time zones
						date = date.minus(Period.ofDays(1));
						hour = hour + 24;
						System.out.println("nice time zone you have there, would be a real shame if it were appropriately documented");
					}
					event = event.cloneWithHour(hour);

					TemporalAdjuster previousFridayAdjuster = TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY);
					LocalDate prevFriday = date.with(previousFridayAdjuster);
					
					TemporalAdjuster previousMondayAdjuster = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
					LocalDate prevMonday = date.with(previousMondayAdjuster);
					
					LocalDate[] keyDates = new LocalDate[] { prevMonday, prevFriday };
					Arrays.sort(keyDates); // maintain chronological order of keys in returned map	
					for (LocalDate prevDate : keyDates) {
						if (eventsByReminderDay.containsKey(prevDate)) {
							Map<LocalDate, List<Event>> value = eventsByReminderDay.get(prevDate);
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
							eventsByReminderDay.put(prevDate, value);
						}
					}
				}
			}
		}
		Map<LocalDate, String> ret = new LinkedHashMap<>();
		for (LocalDate date : eventsByReminderDay.keySet()) {
			StringBuilder sb = new StringBuilder();
			LinkedHashMap<LocalDate, List<Event>> events = eventsByReminderDay.get(date);
			sb.append("Special events this ")
				.append(date.getDayOfWeek() != DayOfWeek.FRIDAY ? "week" : "weekend")
				.append(" (")
				.append(timeZone.getName())
				.append("):");
			for (LocalDate eventDate : events.keySet()) {
				if (date.isAfter(eventDate)) {
					continue;
				}
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
			String tweet = sb.toString();
			if (tweet.contains("Tuesday") || tweet.contains("Wednesday") || tweet.contains("Thursday")) {
				tweet = tweet.replace("Special events this weekend", "Upcoming events");
			}
			ret.put(date, tweet);
		}
		return ret;
	}
	
	public static String getLCQAnnouncementString(TimeZone timeZone) {
		StringBuilder ret = new StringBuilder();
		ret.append("Upcoming MOCS LCQs (" + timeZone.getName() + "):");
		for (LocalDate date = getLCQStartDate(); !date.isAfter(getLCQEndDate()); date = date.plusDays(1)) {
			String[] tweet = timeZone.getTweetText(date).split("\n");
			String dayString = "";
			boolean used = false;
			for (String line : tweet) {
				if (line.contains("day")) {
					used = false;
					dayString = "\n\n" + line.substring(0, line.indexOf(' '));
				} else if (line.contains("LCQ") && !(line.contains("Midnight") && date.getDayOfWeek() != DayOfWeek.WEDNESDAY)) {
					if (!used) {
						used = true;
						ret.append(dayString);
					}
					ret.append("\n" + line);
				}
			}
		}
		return ret.toString();
	}
	
	public static LocalDate getLCQStartDate() {
		return LocalDate.parse("2024-03-18");
	}
	
	public static LocalDate getLCQEndDate() {
		return LocalDate.parse("2024-03-20");
	}
	
	private static void addAll(Map<LocalDate, List<Event>> map, LocalDate date, Event... events) {
		List<Event> list = new ArrayList<>();
		for (int i = 0; i < events.length; i++) {
			list.add(events[i]);
		}
		map.put(date, list);
	}
}
