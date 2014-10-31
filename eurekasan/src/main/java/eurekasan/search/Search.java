package eurekasan.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

public final class Search {
	private static final String SEARCH_URL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&lr=lang_ja&rsz=3&q=";
	private static final String IMAGESEARCH_URL = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=1&q=";

	/**
	 * InputstreamからJSONObjectを読み取ります
	 * @param input 読み取るInputStream
	 * @return 読み取られたJSONObject
	 * @throws JSONException InputStreamの内容がJSONでない場合に発生します
	 */
	public static JSONObject getJSONResult(InputStream input) throws JSONException {
		StringBuilder strb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"))) {
			reader.lines().forEach(strb::append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JSONObject(strb.toString());
	}

	/**
	 * Google検索のJSONをパースします
	 * @param object 検索結果の格納されたJSONObject
	 * @return 検索結果毎に分けたJSONObject配列
	 */
	private static JSONObject[] getGoogleSearchParse(JSONObject object) {
		try {
			JSONArray arr = object.getJSONObject("responseData").getJSONArray("results");
			JSONObject[] objects = new JSONObject[arr.length()];
			for (int i = 0; i < arr.length(); i++) {
				objects[i] = arr.getJSONObject(i);
			}
			return objects;
		} catch (JSONException e) {
			throw new IllegalStateException(e);
		}
	}

	private static JSONObject[] getParse(URL url) throws JSONException {
		try {
			return getGoogleSearchParse(getJSONResult(url.openStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> wordSearch(String str) {
		List<String> list = new ArrayList<>();
		try {
			for (JSONObject jo : getParse(getSearchURL(SEARCH_URL, str))) {
				String title = jo.getString("titleNoFormatting");
				int cutLength = 10;
				if (title.length() > cutLength) {
					title = title.substring(0, cutLength) + "…";
				}
				list.add(title + " " + getResultURL(jo.getString("url")));
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public static List<URL> imageSearch(String str) {
		List<URL> list = new ArrayList<>();
		try {
			for (JSONObject jo : getParse(getSearchURL(IMAGESEARCH_URL, str))) {
				list.add(new URL(jo.getString("url")));
			}
		} catch (JSONException | MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	private static URL getSearchURL(String url, String word) {
		try {
			return new URL(url + URLEncoder.encode(word, "UTF-8"));
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getResultURL(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}