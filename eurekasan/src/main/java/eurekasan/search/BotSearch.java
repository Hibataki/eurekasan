package eurekasan.search;

import twitter4j.Twitter;
import eurekasan.Bot;

public class BotSearch extends Bot {
	public BotSearch(Twitter twitter) {
		super(twitter);
	}

	@Override
	public StringBuilder method() {
		StringBuilder result = new StringBuilder();
		if (word.length() > 20) {
			return result.append("…ちょっと長すぎるんじゃないかしら？");
		} else {
			result.append(word).append("の検索結果よ");
			for (String s : Search.wordSearch("ロードラ " + word)) {
				result.append(BR).append(s);
			}
			return result;
		}
	}
}
