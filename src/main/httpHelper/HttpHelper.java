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

public class HttpHelper {
	public static final String TWEET_BODY_PLACEHOLDER = "$TWEET_BODY";
	public static final String SCHEDULED_TIME_PLACEHOLDER = "$TIME";
	public static final String PATH_TO_CURL = "src/main/httpHelper/curl.txt";
	
	@SneakyThrows
	public static String sendRequest(Map<String, String> params, boolean dryRun) {
		Scanner sc = new Scanner(new File(PATH_TO_CURL));
		sc.useDelimiter("\\Z");
		String curlString = sc.next();
		sc.close();
		
		long scheduledTime = 
				OffsetDateTime.parse(params.get(SCHEDULED_TIME_PLACEHOLDER))
				.toEpochSecond();
		
		String ret;
		if(dryRun) {
			ret = params.get(TWEET_BODY_PLACEHOLDER) + "\n";
		} else {
			String tweetBody = params.get(TWEET_BODY_PLACEHOLDER).replace("\n", "\\\\n");
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
