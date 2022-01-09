package application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import event.ScheduleInfo;
import httpHelper.HttpHelper;
import lombok.SneakyThrows;
import timeZone.Europe;
import timeZone.Japan;
import timeZone.TimeZone;
import timeZone.US;

import static application.ApplicationMode.*;

public class Application {
	// expects these arguments:
	// args[0] = run mode (post normal events, post special announcement tweets, or delete)
	// args[1] = begin date for tweets (inclusive)
	// args[2] = end date for tweets (exclusive)
	// args[3] = region (US, EU, JP)
	// args[4] = not dry-run (true / false)
	public static void main(String[] args) {
		String appMode = args[0];
		LocalDate startDate = LocalDate.parse(args[1]);
		LocalDate endDate = LocalDate.parse(args[2]);
		String region = args[3];
		boolean dryRun = !Boolean.parseBoolean(args[4]);
		
		ApplicationMode mode;
		if ("normal".equalsIgnoreCase(appMode)) {
			mode = SCHEDULE_NORMAL;
		} else if ("special".equalsIgnoreCase(appMode)) {
			mode = SCHEDULE_SPECIAL;
		} else if ("allposts".equalsIgnoreCase(appMode)) {
			mode = NORMAL_AND_SPECIAL;
		} else if ("delete".equalsIgnoreCase(appMode)) {
			mode = DELETE;
		} else {
			throw new UnsupportedOperationException("Unsupported application mode " + appMode);
		}
			
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
		
		if (mode == SCHEDULE_NORMAL) {
			scheduleTweets(startDate, endDate, timeZone, dryRun);
		} else if (mode == SCHEDULE_SPECIAL) {
			scheduleSpecialTweets(startDate, endDate, timeZone, dryRun);
		} else if (mode == NORMAL_AND_SPECIAL) {
			scheduleTweets(startDate, endDate, timeZone, dryRun);
			scheduleSpecialTweets(startDate, endDate, timeZone, dryRun);
		} else if (mode == DELETE) {
			List<String> tweetIds = HttpHelper.retrieveScheduledTweets(startDate, endDate, timeZone, dryRun);
			System.out.println("Beginning to delete tweets for time zone " + timeZone.getName() + "\n");
			for (String tweetId : tweetIds) {
				String response = HttpHelper.deleteTweet(tweetId, timeZone, dryRun);
				if (response.contains("error")) {
					System.out.println(response);
				}
			}
			System.out.println("Done deleting tweets for time zone " + timeZone.getName() + "\n");
		}
	}
	
	private static void scheduleTweets(LocalDate startDate, LocalDate endDate, TimeZone timeZone, boolean dryRun) {
		System.out.println("Scheduling normal tweets for time zone " + timeZone.getName() + "\n");
		LocalDate date = startDate;
		while (!date.equals(endDate)) {
			String response = scheduleTweet(date, timeZone, dryRun);
			date = date.plusDays(1);
			if (response.contains("error") || dryRun) {
				System.out.println(date + "\n" + response + "\n"); // TODO error handling
			}
		}
		System.out.println("Done scheduling normal tweets for time zone " + timeZone.getName() + "\n");
	}
	
	@SneakyThrows
	private static String scheduleTweet(LocalDate date, TimeZone timeZone, boolean dryRun) {
		return HttpHelper.scheduleTweet(
				timeZone,
				timeZone.getTweetText(date),
				timeZone.getPostTime(date),
				dryRun
		);
	}
	
	private static void scheduleSpecialTweets(LocalDate startDate, LocalDate endDate, TimeZone timeZone, boolean dryRun) {
		System.out.println("Scheduling special tweets for time zone " + timeZone.getName() + "\n");
		Map<LocalDate, String> events = ScheduleInfo.getSpecialEventAnnouncementStrings(startDate, endDate, timeZone);
		for (LocalDate date : events.keySet()) {
			String tweet = events.get(date);
			String response = HttpHelper.scheduleTweet(
					timeZone,
					tweet,
					timeZone.getPostTime(date.plusDays(1)).replace(":00:00Z", ":01:00Z"),
					dryRun
			);
			if (response.contains("error") || dryRun) {
				System.out.println(date + "\n" + response + "\n"); // TODO error handling
			}
		}
		System.out.println("Done scheduling special tweets for time zone " + timeZone.getName() + "\n");
	}
}
