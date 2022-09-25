package commandProcessor;

import static commandProcessor.CommandMode.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import timeZone.Europe;
import timeZone.Japan;
import timeZone.TimeZone;
import timeZone.US;

@AllArgsConstructor
public class CommandProcessorHelper {
	TimeZone timeZone;
	
	public String getCurlPath(CommandMode mode) {
		if (timeZone instanceof Europe) {
			if (mode == SEND) {
				return PATH_TO_EU_SEND_CURL;
			} else if (mode == RETRIEVE) {
				return PATH_TO_EU_RETRIEVE_CURL;
			} else if (mode == DELETE) {
				return PATH_TO_EU_DELETE_CURL;
			} else {
				throw new UnsupportedOperationException("unsupported tweet mode " + mode);
			}
		} else if (timeZone instanceof Japan) {
			if (mode == SEND) {
				return PATH_TO_JP_SEND_CURL;
			} else if (mode == RETRIEVE) {
				return PATH_TO_JP_RETRIEVE_CURL;
			} else if (mode == DELETE) {
				return PATH_TO_JP_DELETE_CURL;
			} else {
				throw new UnsupportedOperationException("unsupported tweet mode " + mode);
			}
		} else if (timeZone instanceof US) {
			if (mode == SEND) {
				return PATH_TO_US_SEND_CURL;
			} else if (mode == RETRIEVE) {
				return PATH_TO_US_RETRIEVE_CURL;
			} else if (mode == DELETE) {
				return PATH_TO_US_DELETE_CURL;
			} else {
				throw new UnsupportedOperationException("unsupported tweet mode " + mode);
			}
		} else {
			throw new UnsupportedOperationException("unsupported time zone " + timeZone.toString());
		}
	}
	
	@SneakyThrows
	public String getCurlString(CommandMode mode, String... params) {
		String curlPath = getCurlPath(mode);
		Scanner sc = new Scanner(new File(curlPath));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		if (params.length == 1) { // DELETE
			curlString = curlString
							.replace(TWEET_ID_PLACEHOLDER, params[0]);
		} else if (params.length == 2) { // SEND
			curlString = curlString
							.replace(TWEET_BODY_PLACEHOLDER, params[0])
							.replace(SCHEDULED_TIME_PLACEHOLDER, params[1]);
		} else if (params.length != 0) { // not RETRIEVE
			System.out.println("Nice commandMode you got there, would be a real shame if I didn't know what to do with it");
		}
		return curlString;
	}
	
	@SneakyThrows
	public String execute(String curlString) {
		Process process = new ProcessBuilder(new String[] { "bash", "-c", curlString }).start();
		String ret = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		process.destroy();
		return ret;
	}
	
	/*
	 * Abbreviations to squeeze a tweet under the character limit
	 */
	public String condenseUnderCharacterLimit(String tweet) {
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" (" + timeZone.getName() + ")", "");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Tournament", "Event");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" (replaces regular Challenge)", "");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Challenge", "Chally");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Challenge (32-player)", "Challenge-32");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" (32-player)", "-32");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" Event", "");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace(" Schedule", " Events");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Prelim", "Prlm");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Super RCQ", "SRCQ");
		}
		return tweet;
	}

	public static final String TWEET_BODY_PLACEHOLDER = "$TWEET_BODY";
	public static final String SCHEDULED_TIME_PLACEHOLDER = "$TIME";
	public static final String TWEET_ID_PLACEHOLDER = "$TWEET_ID";

	public static final String PATH_TO_EU_SEND_CURL = "src/main/commandProcessor/Europe/send_tweet_curl.txt";
	public static final String PATH_TO_EU_RETRIEVE_CURL = "src/main/commandProcessor/Europe/retrieve_tweets_curl.txt";
	public static final String PATH_TO_EU_DELETE_CURL = "src/main/commandProcessor/Europe/delete_tweet_curl.txt";

	public static final String PATH_TO_JP_SEND_CURL = "src/main/commandProcessor/Japan/send_tweet_curl.txt";
	public static final String PATH_TO_JP_RETRIEVE_CURL = "src/main/commandProcessor/Japan/retrieve_tweets_curl.txt";
	public static final String PATH_TO_JP_DELETE_CURL = "src/main/commandProcessor/Japan/delete_tweet_curl.txt";

	public static final String PATH_TO_US_SEND_CURL = "src/main/commandProcessor/US/send_tweet_curl.txt";
	public static final String PATH_TO_US_RETRIEVE_CURL = "src/main/commandProcessor/US/retrieve_tweets_curl.txt";
	public static final String PATH_TO_US_DELETE_CURL = "src/main/commandProcessor/US/delete_tweet_curl.txt";

	public static final int MAX_TWEET_LENGTH = 280;
}
