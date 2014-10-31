package eurekasan;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

public class Main {
	private static final String KEY = "j4Ba3wu1mAg3fPum8ipUlCypl";
	private static final String SEC = "S3KakTgQQnQ4nIlgtOsVMUj29IgU3LZdH9PCPGIHSXIfUtObCE";
	private static final long ID = 2869351824L;

	public static void main(String[] args) {
		System.out.println("Eureka_san ver.1.0.0");
		Configuration cb = new Token(KEY, SEC, ID).configBuild();
		Twitter twitter = new TwitterFactory(cb).getInstance();
		TwitterStream stream = new TwitterStreamFactory(cb).getInstance();
		stream.addListener(new BotStreamListener(twitter, ID));
		stream.user();
	}

}
