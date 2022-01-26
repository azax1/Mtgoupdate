package commandProcessor;

import static commandProcessor.CommandMode.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timeZone.TimeZone;

public class CommandProcessor {
	TimeZone timeZone;
	boolean dryRun;
	CommandProcessorHelper helper;

	public CommandProcessor(TimeZone timeZone, boolean dryRun) {
		this.timeZone = timeZone;
		this.dryRun = dryRun;
		this.helper = new CommandProcessorHelper(timeZone);
	}

	public String scheduleTweet(String tweet, String time) {
		long scheduledTime = OffsetDateTime.parse(time).toEpochSecond();
		if (
			scheduledTime < TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
			&& !dryRun // Twitter rejects these tweets but they might be useful for testing
		) {
			return "can't schedule tweet in the past";
		}

		tweet = helper.condenseUnderCharacterLimit(tweet, timeZone);
		if (tweet.length() > CommandProcessorHelper.MAX_TWEET_LENGTH) {
			return time + " error: tweet is too long to post";
		}

		String ret;
		if (dryRun) {
			ret = tweet + "\n";
		} else {
			String tweetBody = tweet.replace("\n", "\\\\n");
			// \\\\ escapes the backslashes so the string has \\n when printed
			// Twitter then parses \\n as \n
			String curlString = helper.getCurlString(SEND, tweetBody, String.valueOf(scheduledTime));
			ret = helper.execute(curlString);
		}
		return ret;
	}

	public List<String> retrieveScheduledTweets(LocalDate startDate, LocalDate endDate) {
		String curlString = helper.getCurlString(RETRIEVE);
		String response = helper.execute(curlString);
		if (dryRun) {
			System.out.println(response);
		}

		// these get printed out in a big JSON, but we don't need to bust out
		// the big guns to parse it. The response looks like this:
		// {"data":{"viewer":{"scheduled_tweet_list":[{"rest_id":"$NUMBER","scheduling_info":{"execute_at":$TIME,
		// ...
		// we can scrape the rest_id by finding each occurrence of "rest_id" and
		// grabbing the number right after it
		// and the scheduled time always comes exactly 34 characters later

		List<String> ret = new ArrayList<>();
		String target = "rest_id";
		long startTime = OffsetDateTime.parse(startDate.toString() + "T00:00:00Z").toEpochSecond();
		long endTime = OffsetDateTime.parse(endDate.toString() + "T00:00:00Z").toEpochSecond();
		for (int i = 0; i < response.length() - target.length(); i++) {
			boolean match = true;
			for (int j = 0; j < target.length(); j++) {
				if (response.charAt(i + j) != target.charAt(j)) {
					match = false;
					break;
				}
			}
			if (!match) {
				continue;
			}
			StringBuilder restId = new StringBuilder();
			int k;
			for (k = i + 10; Character.isDigit(response.charAt(k)); k++) {
				restId.append(response.charAt(k));
			}
			StringBuilder timeString = new StringBuilder();
			for (k += 34; Character.isDigit(response.charAt(k)); k++) {
				timeString.append(response.charAt(k));
			}
			long time = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(timeString.toString()));
			if (startTime < time && time < endTime) {
				ret.add(restId.toString());
			}
		}
		return ret;
	}

	public String deleteTweet(String tweetId) {
		String ret;
		if (dryRun) {
			ret = "dry-run to delete tweet with id " + tweetId;
		} else {
			String curlString = helper.getCurlString(DELETE, tweetId);
			ret = helper.execute(curlString);
		}
		return ret;
	}
}
