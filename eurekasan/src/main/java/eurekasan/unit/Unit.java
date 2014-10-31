package eurekasan.unit;

public interface Unit {
	public enum Weapon {
		槍,
		剣,
		杖,
		弓,
	}

	public enum Attribute {
		水,
		火,
		光,
		闇,
		無,
	}

	public int number();
	public String unit();

	public short reality();
	public Weapon weapon();
	public Attribute attribute();

	public int minHP();
	public int minATK();
	public int minREC();

	public int maxHP();
	public int maxATK();
	public int maxREC();

	public short maxLevel();
	public short requirePoint();
	public int maxExp();

	public String partySkill();
	public String activeSkill();
	public String panelSkill();

	public String prevUnit();
	public String nextUnit();

	public String story();
	public String word1();
	public String word2();
	public String word3();
}
