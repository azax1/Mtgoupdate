package event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleInfo {
	@SuppressWarnings("unchecked")
	public static List<Event>[] getMasterEventSchedule() {
		List<Event> Monday = new ArrayList<Event>();
		Monday.add(new Event(0, Format.PIONEER));
		Monday.add(new Event(3, Format.LIMITED));
		Monday.add(new Event(7, Format.MODERN));
		Monday.add(new Event(9, Format.LIMITED));
		Monday.add(new Event(11, Format.LIMITED));
		Monday.add(new Event(14, Format.MODERN));
		Monday.add(new Event(18, Format.LIMITED));
		Monday.add(new Event(19, Format.PIONEER));
		Monday.add(new Event(21, Format.VINTAGE));
		
		List<Event> Tuesday = new ArrayList<Event>();
		Tuesday.add(new Event(0, Format.PAUPER));
		Tuesday.add(new Event(3, Format.MODERN));
		Tuesday.add(new Event(7, Format.PIONEER));
		Tuesday.add(new Event(9, Format.LIMITED));
		Tuesday.add(new Event(11, Format.PAUPER));
		Tuesday.add(new Event(14, Format.STANDARD));
		Tuesday.add(new Event(15, Format.LIMITED));
		Tuesday.add(new Event(19, Format.LEGACY));
		
		List<Event> Wednesday = new ArrayList<Event>();
		Wednesday.add(new Event(0, Format.LIMITED));
		Wednesday.add(new Event(3, Format.LEGACY));
		Wednesday.add(new Event(7, Format.STANDARD));
		Wednesday.add(new Event(11, Format.PIONEER));
		Wednesday.add(new Event(14, Format.MODERN));
		Wednesday.add(new Event(15, Format.LIMITED));
		Wednesday.add(new Event(18, Format.VINTAGE));
		
		List<Event> Thursday = new ArrayList<Event>();
		Thursday.add(new Event(0, Format.MODERN));
		Thursday.add(new Event(3, Format.PIONEER));
		Thursday.add(new Event(7, Format.PAUPER));
		Thursday.add(new Event(11, Format.MODERN));
		Thursday.add(new Event(12, Format.LIMITED));
		Thursday.add(new Event(15, Format.LEGACY));
		Thursday.add(new Event(16, Format.LIMITED));
		Thursday.add(new Event(19, Format.STANDARD));
		Thursday.add(new Event(21, Format.PIONEER));
		
		List<Event> Friday = new ArrayList<Event>();
		Friday.add(new Event(0, Format.LEGACY));
		Friday.add(new Event(2, Format.LIMITED));
		Friday.add(new Event(4, Format.MODERN));
		Friday.add(new Event(7, Format.LEGACY));
		Friday.add(new Event(10, Format.LIMITED));
		Friday.add(new Event(11, Format.VINTAGE));
		Friday.add(new Event(15, Format.PIONEER));
		Friday.add(new Event(18, Format.LIMITED));
		Friday.add(new Event(19, Format.MODERN));
		
		List<Event> Saturday = new ArrayList<Event>();
		Saturday.add(new Event(0, Format.PIONEER));
		Saturday.add(new Event(1, Format.LIMITED));
		Saturday.add(new Event(2, Format.PAUPER, EventType.CHALLENGE));
		Saturday.add(new Event(4, Format.LEGACY, EventType.CHALLENGE));
		Saturday.add(new Event(6, Format.STANDARD, EventType.CHALLENGE));
		Saturday.add(new Event(8, Format.MODERN, EventType.CHALLENGE));
		Saturday.add(new Event(10, Format.VINTAGE, EventType.CHALLENGE));
		Saturday.add(new Event(12, Format.LIMITED));
		Saturday.add(new Event(14, Format.PIONEER, EventType.CHALLENGE));
		Saturday.add(new Event(16, Format.LIMITED));

		List<Event> Sunday = new ArrayList<Event>();
		Sunday.add(new Event(0, Format.VINTAGE, EventType.CHALLENGE));
		Sunday.add(new Event(4, Format.MODERN, EventType.CHALLENGE));
		Sunday.add(new Event(6, Format.PIONEER, EventType.CHALLENGE));
		Sunday.add(new Event(8, Format.LEGACY, EventType.CHALLENGE));
		Sunday.add(new Event(10, Format.PAUPER, EventType.CHALLENGE));
		Sunday.add(new Event(12, Format.LIMITED));
		Sunday.add(new Event(14, Format.STANDARD, EventType.CHALLENGE));
		Sunday.add(new Event(15, Format.VINTAGE));
		Sunday.add(new Event(16, Format.LIMITED));
				
		return (List<Event>[]) (new List[] { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday });
	}
	
	/*
	 * Returns the current season's list of PTQs, Super PTQs, and Showcase Challenges.
	 */
	public static Map<LocalDate, List<Event>> getSpecialEventSchedule() {
		return null;
		// TODO implement
	}
}
