package eurekasan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.UploadedMedia;

public abstract class Bot {
	public static final String BR = System.lineSeparator();
	private final Twitter twitter;
	private long id;
	protected StringBuilder build;
	protected String word;
	protected StatusUpdate statup;

	public Bot(Twitter twitter) {
		this.twitter = twitter;
	}

	/**
	 * Botを実行するメソッド
	 * 文字列をトリミングしてmain(Status status, String str)に送る
	 * @param status ステータス
	 * @param start トリミングする先頭位置
	 * @param end トリミングする終了位置
	 */
	public void main(Status status, int start, int end) {
		main(status, status.getText().substring(start, status.getText().length() - end));
	}

	/**
	 * Botを実行するメソッド
	 * @param status ステータス（主に送信元を指定するのに利用）
	 * @param str 検索したりする文字列
	 */
	public void main(Status status, String str) {
		this.id = status.getId();
		this.build = new StringBuilder("@").append(status.getUser().getScreenName()).append(' ');
		this.word = str;

		StringBuilder result = new StringBuilder(build);
		result.append(word != null && word.length() > 0 ? method() : "ちゃんと語句をいれてちょうだい");
		postTwitter(statup != null ? statup : new StatusUpdate(result.toString()));
	}

	/**
	 * 付け足す文字列を指定する
	 * もしStatusUpdateに特殊な効果を付けたい場合返り値なしでstatupにセットする
	 * @return 付け足される文字列
	 */
	public abstract StringBuilder method();

	/**
	 * StatusUpdateをTwitterへin_Reply_toを付けて投稿
	 * @param su 投稿するStatusUpdate
	 */
	public void postTwitter(StatusUpdate su) {
		postTwitter(su, false);
	}

	/**
	 * TwitterへIDのin_Reply_toを付けて投稿
	 * @param su 投稿するStatusUpdate
	 * @param isRetry リトライかどうか 通常は必ずfalse
	 */
	public void postTwitter(StatusUpdate su, boolean isRetry) {
		try {
			twitter.updateStatus(su.inReplyToStatusId(id));
		} catch (TwitterException e) {
			if (!isRetry) catchError(e);
		}
	}

	/**
	 * Twitterエラーが発生した際にリトライする
	 * @param e TwitterExceptionをそのまま引数にする
	 */
	private void catchError(TwitterException e) {
		StringBuilder reply = new StringBuilder(build);
		switch (e.getStatusCode()) {
		case 403:
			reply.append("内容が重複か文字数オーバーしているわ、別の言葉で検索してちょうだい");
			break;
		default:
			reply.append("不明なエラーが発生しているわ");
			break;
		}
		reply.append(BR)
		.append("StatusCode: ").append(e.getStatusCode()).append(BR)
		.append("Message: ").append(e.getErrorMessage());
		postTwitter(new StatusUpdate(reply.toString()), true);
	}

	/**
	 * Twitterにメディアをアップロードする
	 *
	 * URLに接続してInputStreamを取得したあと
	 * 一時ファイルを作成しそこへダウンロードし
	 * アップロード後に削除する
	 * @param url アップロードしたいURL
	 * @return アップロードされたメディアの情報
	 */
	public UploadedMedia setMedia(URL url) {
		try {
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			if (connect.getResponseCode() != 200) {
				return null;
			}
			File file = File.createTempFile("rodora-", null);
			readAndSet(connect.getInputStream(), new FileOutputStream(file));
			UploadedMedia media = twitter.uploadMedia(file);
			file.delete();
			return media;
		} catch (IOException | TwitterException e1) {
			return null;
		}
	}

	public static void readAndSet(InputStream in, OutputStream out) {
		try (BufferedOutputStream output = new BufferedOutputStream(out);
				BufferedInputStream input = new BufferedInputStream(in)) {
			byte[] b = new byte[2048];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
