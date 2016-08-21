package com.nvrl;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * this will handle the growth of connections and the making /
 * breaking of them will only effect growth Neuron types
 *
 * @author Michael
 */
public class Growth {

	private float s, ms, r;

	/**
	 * construct a growth node
	 *
	 * @param n
	 */
	public Growth(Neuron n) {
		s = 20;
		ms = 300;
		r = 4;
	}

	/**
	 * set the maximum size of growth distance
	 *
	 * @param ms
	 */
	public void setMaxSize(float ms) {
		this.ms = ms;
	}

	/**
	 * get the max distance
	 *
	 * @return float
	 */
	public float getMaxSize() {
		return ms;
	}

	/**
	 * set the rate at which growth occurs
	 *
	 * @param r
	 */
	public void setRate(float r) {
		this.r = r;
	}

	/**
	 * get the rate at which growth occurs
	 *
	 * @return float
	 */
	public float getRate() {
		return r;
	}

	/**
	 * set the size?
	 *
	 * @param s
	 */
	public void setSize(float s) {
		this.s = s;
	}

	/**
	 * get the size?
	 *
	 * @return float
	 */
	public float getSize() {
		return s;
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @param X
	 * @param Y
	 * @return boolean
	 */
	public boolean pointInSqr(float x, float y, int X, int Y) {
		float s = this.s / DisplayEnvironment.getZoom();
		return (x - s <= X && x + s >= X && y - s <= Y && y + s >= Y);
	}

	// public static boolean pointInSqr(int X,int Y){
	// return(n.getX()-s<=X&&n.getX()+s>=X&&n.getY()-s<=Y&&n.getY()+s>=Y);
	// }
	/**
	 *
	 * @param X
	 * @param Y
	 * @param c
	 * @param p
	 */
	public void draw(float X, float Y, Canvas c, Paint p) {
		float s = this.s / DisplayEnvironment.getZoom();
		c.drawRect(X - s, Y - s, X + s, Y + s, p);
	}

	/**
	 *
	 */
	public void update() {
		s = s < ms ? s + r : 20;
	}

	/**
	 *
	 */
	public static void updateAll() {

	}

	/**
	 *
	 * @param c
	 * @param p
	 */
	public static void drawAll(Canvas c, Paint p) {

	}
}
