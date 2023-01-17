package timeZone;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Europe extends TimeZone {
	static Europe instance;
	
	private Europe() {
		this.offsetFromPT = 9;
		this.dstStarts = LocalDate.parse("2023-03-26");
		this.dstEnds = LocalDate.parse("2023-10-29");
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
			date.equals(dstEnds) && hour < 17) { // TODO handle the fact that 2am CET happens twice today!
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

	@Override
	public Map<LocalDate, String> getDstTweets() {
		Map<LocalDate, String> ret = new LinkedHashMap<LocalDate, String>();
		LocalDate usStartDate = US.getInstance().dstStarts;
		LocalDate usEndDate = US.getInstance().dstEnds;
		ret.put(
			usStartDate,
			"In the US, Daylight Saving Time starts Sunday at 11am CET (in between the Vintage and Modern Challenges)."
			+ " Americans are turning their clocks forward one hour."
			+ " Because of this, the MTGO schedule will be one hour earlier for Europeans until European Summer time begins."
		);

		// there is an off-by-one issue here because the dates dstStarts/Ends are from the US's perspective,
		// which is the source of truth as far as the schedule is concerned; in Europe dst starts on March 27th
		// but that affects events that "really" happen on March 26th (in the US). These reminder tweets are from
		// the locals' perspective, though, so we have to add one day here.
		ret.put(
			dstStarts.plusDays(1),
			"European Summer Time starts tomorrow at 2am CET, with clocks moving forward one hour."
			+ " Since the US moved its clocks forward earlier this month, the schedule will be going back to normal."
		);

		// And likewise add one day here.
		ret.put(
			dstEnds.plusDays(1),
			"European Summer Time ends tomorrow at 2am CET, with clocks moving backward one hour."
			+ " The schedule will be shifted by one hour for the next two weeks until the US moves its clocks back."
		);

		ret.put(
			usEndDate,
			"In the US, Daylight Saving Time ends Sunday at 7pm CET. Americans are turning their clocks back one hour."
			+ " Since Europe already moved its clocks back, the schedule will be going back to normal."
		);
		return ret;
	}
}
