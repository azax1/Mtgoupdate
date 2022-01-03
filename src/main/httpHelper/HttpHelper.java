package httpHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import timeZone.Europe;
import timeZone.Japan;
import timeZone.TimeZone;
import timeZone.US;

public class HttpHelper {
	public static final String TWEET_BODY_PLACEHOLDER = "$TWEET_BODY";
	public static final String SCHEDULED_TIME_PLACEHOLDER = "$TIME";
	
	public static final String PATH_TO_EU_CURL = "src/main/httpHelper/eu_curl.txt";
	public static final String PATH_TO_JP_CURL = "src/main/httpHelper/jp_curl.txt";
	public static final String PATH_TO_US_CURL = "src/main/httpHelper/us_curl.txt";
			
	public static final int MAX_TWEET_LENGTH = 280;
	
	@SneakyThrows
	public static String sendRequest(TimeZone timeZone, Map<String, String> params, boolean dryRun) {
		String curlPath;
		if (timeZone instanceof US) {
			curlPath = PATH_TO_US_CURL;
		} else if (timeZone instanceof Japan) {
			curlPath = PATH_TO_JP_CURL;
		} else if (timeZone instanceof Europe) {
			curlPath = PATH_TO_EU_CURL;
		} else {
			throw new UnsupportedOperationException("unsupported time zone " + timeZone.toString());
		}
		Scanner sc = new Scanner(new File(curlPath));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		long scheduledTime = 
				OffsetDateTime.parse(params.get(SCHEDULED_TIME_PLACEHOLDER))
				.toEpochSecond();
		
		String tweet = params.get(TWEET_BODY_PLACEHOLDER);
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Challenge", "Chally");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			tweet = tweet.replace("Prelim", "Prlm");
		}
		if (tweet.length() > MAX_TWEET_LENGTH) {
			return params.get(SCHEDULED_TIME_PLACEHOLDER) + " tweet was too long to post";
		}
		
		String ret;
		if(dryRun) {
			ret = params.get(SCHEDULED_TIME_PLACEHOLDER) + "\n" + tweet + "\n";
		} else {
			String tweetBody = tweet.replace("\n", "\\\\n");
			// \\\\ escapes the backslashes so the string has \\n when printed
			// Twitter parses \\n as \n
			
			curlString = curlString
						.replace(TWEET_BODY_PLACEHOLDER, tweetBody)
						.replace(SCHEDULED_TIME_PLACEHOLDER, String.valueOf(scheduledTime));
			
			Process process = new ProcessBuilder(new String[] { "bash", "-c", curlString }).start();
			ret = new BufferedReader(
					new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))
					.lines()
					.collect(Collectors.joining("\n")
			);
			process.destroy();
		}
		return ret;
	}
}
