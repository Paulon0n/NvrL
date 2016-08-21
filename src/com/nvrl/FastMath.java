package com.nvrl;

/**
 * Utility for math
 *
 * @author Michael
 * @version 1.0
 */
public class FastMath {

	private static final int BIG_ENOUGH_INT = 16 * 1024;
	private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
	private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;

	/**
	 * A quick floor
	 *
	 * @param x
	 * @return int
	 */
	public static int fastFloor(float x) {
		return (int) (x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
	}

	/**
	 * weird same as floor
	 *
	 * @param x
	 * @return int
	 */
	public static int round(float x) {
		return (int) (x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
	}

	/**
	 * a fast ceiling
	 *
	 * @param x
	 * @return int
	 */
	public static int fastCeil(float x) {
		return BIG_ENOUGH_INT - (int) (BIG_ENOUGH_FLOOR - x);
	}

	/**
	 * sin using degrees
	 *
	 * @param degree
	 * @return float
	 */
	public static float Sin(float degree) {
		return (float) Math.sin((float) Math.toRadians(degree));
	}

	/**
	 * cos using degrees
	 *
	 * @param degree
	 * @return float
	 */
	public static float Cos(float degree) {
		return (float) Math.cos((float) Math.toRadians(degree));
	}

	/**
	 * @param a
	 * @param min
	 * @param max
	 * @return float
	 */
	public static float clamp(float a, float min, float max) {
		return Math.max(min, Math.min(max, a));
	}
	public static double clamp(double a, double min, double max) {
		return Math.max(min, Math.min(max, a));
	}
}
