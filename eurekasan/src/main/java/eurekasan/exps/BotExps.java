package eurekasan.exps;

import java.io.FileNotFoundException;

import twitter4j.JSONException;
import twitter4j.Twitter;
import eurekasan.Bot;
import eurekasan.unit.Unit;
import eurekasan.unit.UnitImpl;

public class BotExps extends Bot {
	public BotExps(Twitter twitter) {
		super(twitter);
	}

	@Override
	public StringBuilder method() {
		StringBuilder result = new StringBuilder();
		String[] split = word.split(" ");
		try {
			Unit unit = new UnitImpl(UnitImpl.getUnitJSONFile(split[0]));
			int integer = split.length > 1 ? Integer.parseInt(split[2]) : 510000;
			return result.append(new Exps(unit, Integer.parseInt(split[1]), integer).result());
		} catch (JSONException e) {
			System.err.println("JSONの構文が間違っています -> " + word);
			return result.append("このユニットを調べるのはもうしばらく待ってちょうだい");
		} catch (FileNotFoundException e) {
			return result.append("残念だけどそんなユニットは存在しないわ");
		}
	}
}
