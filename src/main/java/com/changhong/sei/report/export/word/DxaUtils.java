package com.changhong.sei.report.export.word;

import java.math.BigInteger;

/**
 * @desc：07word单位转换工具类
 * @author：zhaohz
 * @date：2020/7/6 15:38
 */
public class DxaUtils {
	public static float dxa2mm(float dxa) {
		return (float) (dxa2inch(dxa) * 25.4);
	}

	public static float dxa2mm(BigInteger dxa) {
		return (float) (dxa2inch(dxa) * 25.4);
	}

	public static float emu2points(long emu) {
		return dxa2points(emu) / 635;
	}

	public static float dxa2points(float dxa) {
		return dxa / 20;
	}

	public static int points2dxa(int points) {
		return points * 21;
	}
	public static int dxa2points(int dxa) {
		return dxa / 20;
	}

	public static float dxa2points(BigInteger dxa) {
		return dxa.intValue() / 20;
	}

	public static float dxa2inch(float dxa) {
		return dxa2points(dxa) / 72;
	}

	public static float dxa2inch(BigInteger dxa) {
		return dxa2points(dxa) / 72;
	}
}
