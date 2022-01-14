package timeZone;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Europe extends TimeZone {
	static Europe instance;
	
	private Europe() {
		this.offsetFromPT = 9;
		this.dstStarts = LocalDate.parse("2022-03-26");
		this.dstEnds = LocalDate.parse("2022-10-29");
		this.name = "CET";
	}
	
	static {
		instance = new Europe();
	}

	public static TimeZone getInstance() {
		return instance;
	}
	
	public int getExtraOffset(LocalDate date, int hour) {
		int offset = 0;
		if (date.isAfter(dstStarts) ||
				date.equals(dstStarts) && hour >= 16) {
				offset++;
			}
		if (date.isBefore(dstEnds) ||
			date.equals(dstEnds) && hour < 17) { // TODO epsilon
			offset++;
		}
		return offset - 1;
	}

	@Override
	public String getPostTime(LocalDate date) {
		String postDate = date.minus(Period.ofDays(1)).toString();
		String postTime;
		if (date.isBefore(dstStarts) || date.isAfter(dstEnds)) {
			postTime = "T21:00:00Z";
		}
		else {
			postTime = "T20:00:00Z";
		}
		return postDate + postTime;
	}

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM");
	}
}
