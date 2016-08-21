package com.nvrl;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

//this is the growth part
/**
 * used for growth to a route a connection ( this is in very early dev
 * )
 *
 * @author Michael
 */
public class tunneler {

	private Neuron Host;

	private ArrayList<Point> pnt;

	/**
	 * set the host / source Neuron
	 *
	 * @param host
	 */
	public void setHost(Neuron host) {
		Host = host;
	}

	/**
	 * get the host / source Neuron
	 *
	 * @return Neuron
	 */
	public Neuron getHost() {
		return Host;
	}

	/**
	 * add a point into the route
	 *
	 * @param p
	 */
	public void addPoint(Point p) {
		pnt.add(p);
	}

	/**
	 * draw the route
	 *
	 * @param c
	 * @param p
	 */
	public void draw(Canvas c, Paint p) {
		for (int i = 0; i < pnt.size() - 1; i++) {
			Point pn1 = pnt.get(i), pn2 = pnt.get(i + 1);
			c.drawLine(pn1.X, pn1.Y, pn2.X, pn2.Y, p);
		}
	}
}
