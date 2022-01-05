package timeZone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class US extends TimeZone {
	static US instance;
	
	private US() {
		this.offsetFromPT = 0;
 		this.dstStarts = LocalDate.parse("2022-03-13");
		this.dstEnds = LocalDate.parse("2022-11-06");
		this.name = "PT";
	}
	
	static {
		instance = new US();
	}

	public static TimeZone getInstance() {
		return instance;
	}

	@Override
	public int getExtraOffset(LocalDate date, int hour) {
		int offset = 0;
		if (date.isAfter(dstStarts) ||
				date.equals(dstStarts) && hour >= 2) {
				offset++;
		}
		if (date.isBefore(dstEnds) ||
			date.equals(dstEnds) && hour <= 2) {
			offset++;
		}
		return offset - 1;
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
