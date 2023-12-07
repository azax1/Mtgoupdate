package timeZone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class US extends TimeZone {
	static US instance;
	
	private US() {
		this.offsetFromPT = 0;
 		this.dstStarts = LocalDate.parse("2024-03-10");
		this.dstEnds = LocalDate.parse("2024-11-03");
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
			return date.toString() + "T04:00:00Z";
		} else {
			return date.toString() + "T03:00:00Z";
		}
	}

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("MM/dd");
	}

	@Override
	public Map<LocalDate, String> getDstTweets() {
		Map<LocalDate, String> ret = new LinkedHashMap<LocalDate, String>();
		ret.put(
			dstStarts,
			"Daylight Saving Time starts Sunday at 2am (in between the Vintage and Modern Challenges)."
			+ " Remember to turn your clocks forward!"
		);

		ret.put(
			dstEnds,
			"Daylight Saving Time ends Sunday at 2am (in between the Vintage and Modern Challenges)."
			+ " Remember to turn your clocks black!"
		);
		return ret;
	}
}
