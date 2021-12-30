package event;

/**
 * Represents a specific MTGO event (Prelim or Challenge, currently) in a specific format and at a specific time.
 */
public class Event {
	Format format;
	EventType eventType;
	
	public Event(Format format) {
		this(format, EventType.PRELIM);
	}
	
	public Event(Format format, EventType eventType) {
		this.format = format;
		this.eventType = eventType;
	}
	
	public static String prettyPrint(int hour, Event event) {
		StringBuilder ret = new StringBuilder();
		if (hour == 0) {
			ret.append("12am ");
		} else if (hour == 24) {
			ret.append("Midnight ");
		}
		else {
			if (hour < 12) {
				ret.append(Integer.toString(hour));
				ret.append("am ");
			} else if (hour > 12) {
				ret.append(Integer.toString(hour - 12));
				ret.append("pm ");
			} else {
				ret.append("Noon ");
			}
		}
		
		ret.append(event.format.toString());
		ret.append(" ");
		ret.append(event.eventType.toString());
		
		return ret.toString();
	}
}
