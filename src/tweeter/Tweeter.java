package tweeter;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
	public static void main(String[] args) {
		LocalDate startDate = LocalDate.parse(args[0]);
		LocalDate endDate = LocalDate.parse(args[1]);
		String region = args[2];
		scheduleTweets(startDate, endDate, region);
	}
	
	private static void scheduleTweets(LocalDate startDate, LocalDate endDate, String region) {
		LocalDate date = startDate;
		while (!date.equals(endDate)) {
			String response = scheduleTweet(date, region);
			date = date.plusDays(1);
			System.out.println(response + "\n"); // TODO error handling
		}
	}
	
	@SneakyThrows
	private static String scheduleTweet(LocalDate date, String region) {
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
		String scheduledTime = date.toString() + refDay.postTime();
		String tweetBody = Day.fromDayOfWeek(day, refDay).text();
		
		Map<String, String> params = new HashMap<>();
		params.put(HttpHelper.SCHEDULED_TIME_PLACEHOLDER, scheduledTime);
		params.put(HttpHelper.TWEET_BODY_PLACEHOLDER, tweetBody);

		return HttpHelper.sendRequest(params);
	}
}
