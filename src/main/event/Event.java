package event;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a specific MTGO event in a specific format and at a specific time.
 */
@Getter
@Builder
public class Event {
	int hour;
	Format format;
	EventType eventType;
	
	public Event(int hour, Format format) {
		this(hour, format, EventType.PRELIM);
	}
	
	public Event(int hour, Format format, EventType eventType) {
		this.hour = hour;
		this.format = format;
		this.eventType = eventType;
	}
	
	public String prettyPrint() {
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
		
		ret.append(format.toString());
		ret.append(" ");
		ret.append(eventType.toString());
		
		return ret.toString();
	}
}
