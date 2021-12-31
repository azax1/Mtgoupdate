package application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import httpHelper.HttpHelper;
import lombok.SneakyThrows;
import timeZone.Europe;
import timeZone.Japan;
import timeZone.TimeZone;
import timeZone.US;

public class Application {
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
		
		TimeZone timeZone;
		if ("US".equals(region)) {
			timeZone = US.getInstance();
		} else if ("EU".equals(region)) {
			timeZone = Europe.getInstance();
		} else if ("JP".equals(region)) {
			timeZone = Japan.getInstance();
		} else {
			throw new IllegalArgumentException("unexpected region: \"" + region + "\"");
		}
		
		scheduleTweets(startDate, endDate, timeZone, dryRun);
	}
	
	private static void scheduleTweets(LocalDate startDate, LocalDate endDate, TimeZone timeZone, boolean dryRun) {
		LocalDate date = startDate;
		while (!date.equals(endDate)) {
			String response = scheduleTweet(date, timeZone, dryRun);
			date = date.plusDays(1);
			System.out.println(response + "\n"); // TODO error handling
		}
	}
	
	@SneakyThrows
	private static String scheduleTweet(LocalDate date, TimeZone timeZone, boolean dryRun) {
		String scheduledTime = timeZone.getPostTime(date);
		String tweetBody = timeZone.getTweetText(date);
		Map<String, String> params = new HashMap<>();
		params.put(HttpHelper.SCHEDULED_TIME_PLACEHOLDER, scheduledTime);
		params.put(HttpHelper.TWEET_BODY_PLACEHOLDER, tweetBody);

		return HttpHelper.sendRequest(params, dryRun);
	}
}
