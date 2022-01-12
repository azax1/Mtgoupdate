package event;

import static event.EventType.*;
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
		if (format == Format.LIMITED && remark != null &&
			(eventType == PTQ || eventType == SUPER_PTQ || eventType == MOCS_OPEN)) {
			ret.append(" (" + remark + ")");
		}
		ret.append(" ");
		ret.append(eventType.toString());
		
		return ret.toString();
	}
}
