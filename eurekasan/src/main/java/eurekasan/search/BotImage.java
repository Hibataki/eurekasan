package eurekasan.search;

import java.net.URL;
import java.util.List;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import eurekasan.Bot;

public class BotImage extends Bot {
	public BotImage(Twitter twitter) {
		super(twitter);
	}

	@Override
	public StringBuilder method() {
		StringBuilder result = new StringBuilder();
		if (word.length() > 20) {
			return result.append("…ちょっと長すぎるんじゃないかしら？");
		} else {
			result.append(word).append("の画像よ");
			List<URL> urls = Search.imageSearch("ロードラ " + word);
			long[] longs = new long[urls.size()];
			for (int i = 0; i < urls.size(); i++) {
				longs[i] = setMedia(urls.get(i)).getMediaId();
			}
			statup = new StatusUpdate(new StringBuilder(build).append(result).toString());
			statup.setMediaIds(longs);
			return result;
		}
	}
}
