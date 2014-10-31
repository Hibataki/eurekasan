package eurekasan.search;

public class SearchWord {
	private String str;
	private String[] strs;
	private int length;

	public SearchWord(String... strs) {
		this.strs = strs;
	}

	public boolean equals(String text) {
		for (String s : strs) {
			if (text.equals(s)) {
				this.str = s;
				this.length = s.length();
				return true;
			}
		}
		return false;
	}

	public boolean startsWith(String text) {
		for (String s : strs) {
			if (text.startsWith(s)) {
				this.str = s;
				this.length = s.length();
				return true;
			}
		}
		return false;
	}

	public boolean endsWith(String text) {
		for (String s : strs) {
			if (text.endsWith(s)) {
				this.str = s;
				this.length = s.length();
				return true;
			}
		}
		return false;
	}

	public String getString() {
		return this.str;
	}

	public int getLength() {
		return this.length;
	}
}
