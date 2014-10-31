package eurekasan.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import eurekasan.search.Search;

public class UnitImpl implements Unit {
	private static final String BR = System.lineSeparator();
	private int number;
	private String unit;
	private short reality;
	private Unit.Weapon weapon;
	private Unit.Attribute attribute;
	private int minHP;
	private int minATK;
	private int minREC;
	private int maxHP;
	private int maxATK;
	private int maxREC;
	private short maxLevel;
	private short requirePoint;
	private int maxExp;
	private String partySkill;
	private String activeSkill;
	private String panelSkill;
	private String prevUnit;
	private String nextUnit;
	private String story;
	private String word1;
	private String word2;
	private String word3;

	public static File getUnitJSONFile(String unitName) {
		return new File("units", unitName + ".json");
	}

	public UnitImpl(File file) throws JSONException, FileNotFoundException {
		this(Search.getJSONResult(new FileInputStream(file)));
	}

	public UnitImpl(JSONObject obj) throws JSONException {
		this.number = obj.getInt("number");
		this.unit = obj.getString("unit");
		this.reality = (short) obj.getInt("reality");
		this.weapon = Enum.valueOf(Unit.Weapon.class, obj.getString("weapon"));
		this.attribute = Enum.valueOf(Unit.Attribute.class, obj.getString("attribute"));
		this.minHP = obj.getInt("minHP");
		this.minATK = obj.getInt("minATK");
		this.minREC = obj.getInt("minREC");
		this.maxHP = obj.getInt("maxHP");
		this.maxATK = obj.getInt("maxATK");
		this.maxREC = obj.getInt("maxREC");
		this.maxLevel = (short) obj.getInt("maxLevel");
		this.requirePoint = (short) obj.getInt("requirePoint");
		this.maxExp = obj.getInt("maxExp");
		this.partySkill = obj.getString("partySkill");
		this.activeSkill = obj.getString("activeSkill");
		this.panelSkill = obj.getString("panelSkill");
		this.prevUnit = obj.getString("prevUnit");
		this.nextUnit = obj.getString("nextUnit");
		this.story = obj.getString("story");
		//JSONArray wordArray = new JSONArray("word");
		//this.word1 = wordArray.getString(0);
		//this.word2 = wordArray.getString(1);
		//this.word3 = wordArray.getString(2);
	}

	@Override
	public int number() {
		return this.number;
	}

	@Override
	public String unit() {
		return this.unit;
	}

	@Override
	public short reality() {
		return this.reality;
	}

	@Override
	public Unit.Weapon weapon() {
		return this.weapon;
	}

	@Override
	public Unit.Attribute attribute() {
		return this.attribute;
	}

	@Override
	public int minHP() {
		return this.minHP;
	}

	@Override
	public int minATK() {
		return this.minATK;
	}

	@Override
	public int minREC() {
		return this.minREC;
	}

	@Override
	public int maxHP() {
		return this.maxHP;
	}

	@Override
	public int maxATK() {
		return this.maxATK;
	}

	@Override
	public int maxREC() {
		return this.maxREC;
	}

	@Override
	public short maxLevel() {
		return this.maxLevel;
	}

	@Override
	public short requirePoint() {
		return this.requirePoint;
	}

	@Override
	public int maxExp() {
		return this.maxExp;
	}

	@Override
	public String partySkill() {
		return this.partySkill;
	}

	@Override
	public String activeSkill() {
		return this.activeSkill;
	}

	@Override
	public String panelSkill() {
		return this.panelSkill;
	}

	@Override
	public String prevUnit() {
		return this.prevUnit;
	}

	@Override
	public String nextUnit() {
		return this.nextUnit;
	}

	@Override
	public String story() {
		return this.story;
	}

	@Override
	public String word1() {
		return this.word1;
	}

	@Override
	public String word2() {
		return this.word2;
	}

	@Override
	public String word3() {
		return this.word3;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder()
		.append("No.").append(number())
		.append(" ☆").append(reality())
		.append(" ユニット名: ").append(unit().toString())
		.append(' ')
		.append(weapon().toString()).append(' ')
		.append(attribute().toString()).append(BR)
		.append("最大HP: ").append(maxHP())
		.append(" 最大攻撃力: ").append(maxATK())
		.append(" 最大回復力: ").append(maxREC())
		;
		return build.toString();
	}
}