package event;

import static event.EventType.*;
import lombok.Getter;

/**
 * Represents a specific MTGO event in a specific format and at a specific time.
 */
@Getter
public class Event {
	int hour;
	Format format;
	String remark;
	EventType eventType;
	
	public Event(int hour, Format format, String remark, EventType eventType) {
		this.hour = hour;
		this.remark = remark;
		this.format = format;
		this.eventType = eventType;
	}
	
	public Event(int hour, Format format, EventType eventType) {
		this(hour, format, null, eventType);
	}
	
	public Event(int hour, Format format) {
		this(hour, format, EventType.PRELIM);
	}

	@Override
	public Event clone() {
		return new Event(hour, format, remark, eventType);
	}
	
	public Event cloneWithHour(int newHour) {
		return new Event(newHour, format, remark, eventType);
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
		
		if (format == Format.LIMITED && remark != null) {
			if (eventType == RCQ || eventType == SUPER_RCQ || eventType == MOCS_OPEN) {
				ret.append(format.toString() + " (" + remark + ")");
			} else if (eventType == DUELS) {
				ret.append(remark);
			}
		} else {
			ret.append(format.toString());
		}
		
		ret.append(" ");
		ret.append(eventType.toString());
		
		return ret.toString();
	}
}
