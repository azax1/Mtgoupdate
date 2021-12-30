package timeZone;

import java.time.LocalDate;
import java.time.Period;

public class Japan extends TimeZone {
	static Japan instance;
	
	private Japan() {
		this.offsetFromPT = 17;
		this.dstStarts = null;
		this.dstEnds = null;
		this.timeZoneName = "JST";
	}
	
	static {
		instance = new Japan();
	}

	@Override
	public String getPostTime(LocalDate date) {
		return date.minus(Period.ofDays(1)).toString() + "T13:00:00Z";
	}

	public static TimeZone getInstance() {
		return instance;
	}
}
