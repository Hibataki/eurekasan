package eurekasan.unit;

import java.io.FileNotFoundException;

import twitter4j.JSONException;
import twitter4j.Twitter;
import eurekasan.Bot;

public class BotUnit extends Bot {
	public BotUnit(Twitter twitter) {
		super(twitter);
	}

	@Override
	public StringBuilder method() {
		StringBuilder result = new StringBuilder();
		try {
			return result.append(new UnitImpl(UnitImpl.getUnitJSONFile(word)).toString());
		} catch (JSONException e) {
			System.err.println("JSONの構文が間違っています -> " + word);
			return result.append("このユニットを調べるのはもうしばらく待ってちょうだい");
		} catch (FileNotFoundException e) {
			return result.append("残念だけどそんなユニットは存在しないわ");
		}
	}
}
