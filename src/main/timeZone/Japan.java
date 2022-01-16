package timeZone;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Japan extends TimeZone {
	static Japan instance;
	
	private Japan() {
		this.offsetFromPT = 17;
		this.dstStarts = null;
		this.dstEnds = null;
		this.name = "JST";
	}
	
	static {
		instance = new Japan();
	}

	public static TimeZone getInstance() {
		return instance;
	}

	@Override
	public int getExtraOffset(LocalDate date, int hour) {
		return 0;
	}

	@Override
	public String getPostTime(LocalDate date) {
		return date.minus(Period.ofDays(1)).toString() + "T13:00:00Z";
	}

	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("MM/dd");
	}

	@Override
	public Map<LocalDate, String> getDstTweets() {
		Map<LocalDate, String> ret = new LinkedHashMap<LocalDate, String>();
		LocalDate usStartDate = US.getInstance().dstStarts;
		LocalDate usEndDate = US.getInstance().dstEnds;
		ret.put(
			usStartDate,
			"In the US, Daylight Saving Time starts 7pm Sunday (JST); they turn their clocks forward one hour."
			+ " Because of this, the MTGO schedule will be one hour earlier (in Japan) than before.\n"
			+ "\n"
			+ "For instance, the second weekly Modern Challenge, previously at 9pm JST on Sunday, is now at 8pm."
		);

		ret.put(
			usEndDate,
			"In the US, Daylight Saving Time starts 7pm Sunday (JST); they turn their clocks back one hour."
			+ " Because of this, the MTGO schedule will be one hour later (in Japan) than before.\n"
			+ "\n"
			+ "For instance, the second weekly Modern Challenge, previously at 8pm JST on Sunday, is now at 9pm."
		);
		return ret;
	}
}
