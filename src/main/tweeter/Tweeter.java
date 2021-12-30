package tweeter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import day.Day;
import day.DaysEU;
import day.DaysJP;
import day.DaysUS;
import httpHelper.HttpHelper;
import lombok.SneakyThrows;

public class Tweeter {
	// expects these arguments:
	// args[0] = begin date for tweets (inclusive)
	// args[1] = end date for tweets (exclusive)
	// args[2] = region (US, EU, JP)
	// args[3] = not dry-run (true / false)
	public static void main(String[] args) {
		LocalDate startDate = LocalDate.parse(args[0]);
		LocalDate endDate = LocalDate.parse(args[1]);
		String region = args[2];
		boolean dryRun = !Boolean.parseBoolean(args[3]);
		scheduleTweets(startDate, endDate, region, dryRun);
	}
	
	private static void scheduleTweets(LocalDate startDate, LocalDate endDate, String region, boolean dryRun) {
		LocalDate date = startDate;
		while (!date.equals(endDate)) {
			String response = scheduleTweet(date, region, dryRun);
			date = date.plusDays(1);
			System.out.println(response + "\n"); // TODO error handling
		}
	}
	
	@SneakyThrows
	private static String scheduleTweet(LocalDate date, String region, boolean dryRun) {
		Day refDay;
		if ("US".equals(region)) {
			refDay = DaysUS.MONDAY;
		} else if ("EU".equals(region)) {
			refDay = DaysEU.MONDAY;
		} else if ("JP".equals(region)) {
			refDay = DaysJP.MONDAY;
		} else {
			throw new IllegalArgumentException("unexpected region: \"" + region + "\"");
		}
		DayOfWeek day = date.getDayOfWeek();
		String scheduledTime;
		if (refDay instanceof DaysUS) {
			 scheduledTime = date.toString() + refDay.postTime();
		} else {
			 scheduledTime = date.minus(Period.ofDays(1)).toString() + refDay.postTime();
		}
		String tweetBody = Day.fromDayOfWeek(day, refDay).text();
		
		Map<String, String> params = new HashMap<>();
		params.put(HttpHelper.SCHEDULED_TIME_PLACEHOLDER, scheduledTime);
		params.put(HttpHelper.TWEET_BODY_PLACEHOLDER, tweetBody);

		return HttpHelper.sendRequest(params, dryRun);
	}
}
