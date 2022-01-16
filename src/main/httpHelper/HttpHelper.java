package httpHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import timeZone.Europe;
import timeZone.Japan;
import timeZone.TimeZone;
import timeZone.US;

public class HttpHelper {
	@SneakyThrows
	public static String scheduleTweet(TimeZone timeZone, String tweet, String time, boolean dryRun) {
		String curlPath;
		if (timeZone instanceof US) {
			curlPath = PATH_TO_US_SEND_CURL;
		} else if (timeZone instanceof Japan) {
			curlPath = PATH_TO_JP_SEND_CURL;
		} else if (timeZone instanceof Europe) {
			curlPath = PATH_TO_EU_SEND_CURL;
		} else {
			throw new UnsupportedOperationException("unsupported time zone " + timeZone.toString());
		}
		Scanner sc = new Scanner(new File(curlPath));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		long scheduledTime = 
				OffsetDateTime.parse(time)
				.toEpochSecond();
		
		if (
				scheduledTime < TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
				&& !dryRun // Twitter rejects these tweets but they might be useful for testing
		) {
			return "can't schedule tweet in the past";
		}
		
		tweet = condense(tweet, timeZone);
		if (tweet.length() > MAX_TWEET_LENGTH) {
			return time + " error: tweet is too long to post";
		}
		
		String ret;
		if(dryRun) {
			ret = tweet + "\n";
		} else {
			String tweetBody = tweet.replace("\n", "\\\\n");
			// \\\\ escapes the backslashes so the string has \\n when printed
			// Twitter then parses \\n as \n
			
			curlString = curlString
						.replace(TWEET_BODY_PLACEHOLDER, tweetBody)
						.replace(SCHEDULED_TIME_PLACEHOLDER, String.valueOf(scheduledTime));
			
			ret = execute(curlString);
		}
		return ret;
	}
	
	@SneakyThrows
	public static List<String> retrieveScheduledTweets(LocalDate startDate, LocalDate endDate, TimeZone timeZone, boolean dryRun) {
		String curlPath;
		if (timeZone instanceof US) {
			curlPath = PATH_TO_US_RETRIEVE_CURL;
		} else if (timeZone instanceof Japan) {
			curlPath = PATH_TO_JP_RETRIEVE_CURL;
		} else if (timeZone instanceof Europe) {
			curlPath = PATH_TO_EU_RETRIEVE_CURL;
		} else {
			throw new UnsupportedOperationException("unsupported time zone " + timeZone.toString());
		}
		Scanner sc = new Scanner(new File(curlPath));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		String response = execute(curlString);
		if (dryRun) {
			System.out.println(response);
		}
		
		// these get printed out in a big JSON, but we don't need to bust out the big guns to parse it
		// the response looks like this:
		// {"data":{"viewer":{"scheduled_tweet_list":[{"rest_id":"$NUMBER","scheduling_info":{"execute_at":$TIME, ...
		// we can scrape the rest_id by finding each occurrence of "rest_id" and grabbing the number right after it
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
	
	@SneakyThrows
	public static String deleteTweet(String tweetId, TimeZone timeZone, boolean dryRun) {
		String curlPath;
		if (timeZone instanceof US) {
			curlPath = PATH_TO_US_DELETE_CURL;
		} else if (timeZone instanceof Japan) {
			curlPath = PATH_TO_JP_DELETE_CURL;
		} else if (timeZone instanceof Europe) {
			curlPath = PATH_TO_EU_DELETE_CURL;
		} else {
			throw new UnsupportedOperationException("unsupported time zone " + timeZone.toString());
		}
		Scanner sc = new Scanner(new File(curlPath));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		String ret;
		if (dryRun) {
			ret = "dry-run to delete tweet with id " + tweetId;
		} else {
			curlString = curlString.replace(TWEET_ID_PLACEHOLDER, tweetId);
			ret = execute(curlString);
		}
		return ret;
	}
	
	@SneakyThrows
	private static String execute(String curlString) {
		Process process = new ProcessBuilder(new String[] { "bash", "-c", curlString }).start();
		String ret = new BufferedReader(
				new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n")
		);
		process.destroy();
		return ret;
	}
	
	/*
	 * Abbreviations to squeeze a tweet under the character limit
	 */
	private static String condense(String tweet, TimeZone timeZone) {
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" (" + timeZone.getName() + ")", "");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Tournament", "Event");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Challenge", "Chally");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Prelim", "Prlm");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Super PTQ", "SPTQ");
		}
		return tweet;
	}
	
	public static final String TWEET_BODY_PLACEHOLDER = "$TWEET_BODY";
	public static final String SCHEDULED_TIME_PLACEHOLDER = "$TIME";
	public static final String TWEET_ID_PLACEHOLDER = "$TWEET_ID";
	
	public static final String PATH_TO_EU_SEND_CURL = "src/main/httpHelper/Europe/send_tweet_curl.txt";
	public static final String PATH_TO_EU_RETRIEVE_CURL = "src/main/httpHelper/Europe/retrieve_tweets_curl.txt";
	public static final String PATH_TO_EU_DELETE_CURL = "src/main/httpHelper/Europe/delete_tweet_curl.txt";
	
	public static final String PATH_TO_JP_SEND_CURL = "src/main/httpHelper/Japan/send_tweet_curl.txt";
	public static final String PATH_TO_JP_RETRIEVE_CURL = "src/main/httpHelper/Japan/retrieve_tweets_curl.txt";
	public static final String PATH_TO_JP_DELETE_CURL = "src/main/httpHelper/Japan/delete_tweet_curl.txt";

	public static final String PATH_TO_US_SEND_CURL = "src/main/httpHelper/US/send_tweet_curl.txt";
	public static final String PATH_TO_US_RETRIEVE_CURL = "src/main/httpHelper/US/retrieve_tweets_curl.txt";
	public static final String PATH_TO_US_DELETE_CURL = "src/main/httpHelper/US/delete_tweet_curl.txt";
	
	public static final int MAX_TWEET_LENGTH = 280;
}
