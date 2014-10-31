package eurekasan.exps;

import eurekasan.unit.Unit;

public class Exps {
	private Unit unit;
	private int nowExp;
	private int exp;

	/**
	 * コンストラクタ
	 * @param unit 対象のユニット
	 * @param nowExp 現在の経験値
	 * @param exp 合成する経験値
	 */
	public Exps(Unit unit, int nowExp, int exp) {
		this.unit = unit;
		this.nowExp = nowExp;
		this.exp = exp;
	}

	/**
	 * 合成後のレベルを返す
	 * TODO 計算式を書く
	 * @return レベル
	 */
	public short getLevel() {
		return 0;
	}

	/**
	 * 合成後のレベルが最大に到達する場合に最大経験値を上回る経験値の量を返す
	 * @return 経験値
	 */
	public int overExp() {
		int overExp = nowExp + exp;
		return overExp > unit.maxExp() ? overExp - unit.maxExp() : -1;
	}

	/**
	 * 結果を返す
	 * @return 結果
	 */
	public StringBuilder result() {
		StringBuilder result;
		if (overExp() != -1) {
			result = new StringBuilder("最大になり、経験値が").append(overExp()).append("余るわ");
		} else {
			result = new StringBuilder(getLevel()).append("になるわ");
		}
		return new StringBuilder("この合成をした場合レベルは").append(result);
	}
}