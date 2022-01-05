package timeZone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class US extends TimeZone {
	static US instance;
	
	private US() {
		this.offsetFromPT = 0;
 		this.dstStarts = LocalDate.parse("2022-03-13");
		this.dstEnds = LocalDate.parse("2022-11-06");
		this.timeZoneName = "PT";
	}
	
	static {
		instance = new US();
	}

	public static TimeZone getInstance() {
		return instance;
	}

	@Override
	public String getPostTime(LocalDate date) {
		if (date.isBefore(dstStarts) || date.isAfter(dstEnds)) {
			return date.toString() + "T06:00:00Z";
		} else {
			return date.toString() + "T05:00:00Z";
		}
	}

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("MM/dd");
	}
}
