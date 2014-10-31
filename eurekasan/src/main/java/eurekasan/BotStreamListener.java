package eurekasan;

import java.util.function.LongPredicate;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserStreamAdapter;
import eurekasan.exps.BotExps;
import eurekasan.search.BotImage;
import eurekasan.search.BotSearch;
import eurekasan.search.SearchWord;
import eurekasan.unit.BotUnit;
import eurekasan.unit.SearchUnit;

public class BotStreamListener extends UserStreamAdapter {
	private final SearchWord start = new SearchWord("ユーリカさん", "@Eureka_san ", "ユーリカちゃん");
	private final SearchWord imageEnd = new SearchWord("の画像を調べて", "の画像ください");
	private final SearchWord statusEnd = new SearchWord("のステータスをください", "のステータスを調べて");
	private final SearchWord yorishiroEnd = new SearchWord("に上よりを合成して");
	private final SearchWord levelEnd = new SearchWord("のレベルを計算して");
	private final SearchWord searchEnd = new SearchWord("を調べて", "で調べて", "でググって", "でぐぐって");
	private final SearchWord unitEnd = new SearchWord("を含むユニットを検索して");
	private final Bot image;
	private final Bot unit;
	private final Bot level;
	private final Bot search;
	private final Bot searchUnit;
	private final Twitter twitter;
	private final LongPredicate lp;

	public BotStreamListener(Twitter twitter, long id) {
		this.twitter = twitter;
		this.lp = s -> s != id;
		this.image = new BotImage(twitter);
		this.unit = new BotUnit(twitter);
		this.level = new BotExps(twitter);
		this.search = new BotSearch(twitter);
		this.searchUnit = new SearchUnit(twitter);
	}

	@Override
	public void onStatus(Status status) {
		String text = status.getText();
		if (start.startsWith(text)) {
			if (imageEnd.endsWith(text)) {
				image.main(status, start.getLength(), imageEnd.getLength());
			} else if (statusEnd.endsWith(text)) {
				unit.main(status, start.getLength(), statusEnd.getLength());
			} else if (yorishiroEnd.endsWith(text)) {
				level.main(status, start.getLength(), yorishiroEnd.getLength());
			} else if (levelEnd.endsWith(text)) {
				level.main(status, start.getLength(), levelEnd.getLength());
			} else if (unitEnd.endsWith(text)) {
				searchUnit.main(status, start.getLength(), unitEnd.getLength());
			} else if (searchEnd.endsWith(text)) {
				search.main(status, start.getLength(), searchEnd.getLength());
			}
		}
	}

	@Override
	public void onFollow(User source, User from) {
		try {
			if (lp.test(source.getId()))
				twitter.createFriendship(source.getId());
		} catch (TwitterException ignore) {
		}
	}
}
