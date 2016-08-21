package com.nvrl;

/**
 * This is a combination of variables that are in most of the classes
 *
 * @author Michael
 * @version 1.0
 */
public class CommonExtensions {

	String name = "";
	Point Pos = new Point();
	Point Size = new Point();
	private Point AbsolutePos = new Point();

	/**
	 * @return point
	 */
	public Point getPos() {
		return Pos;
	}

	/**
	 * @return point
	 */
	public Point getSize() {
		return Size;
	}

	/**
	 * @param p
	 */
	public void setPos(Point p) {
		Pos = p.copy();
	}

	/**
	 * @param p
	 */
	public void setSize(Point p) {
		Size = p.copy();
	}

	/**
	 * @return point
	 */
	public Point getAbsolutePos() {
		return AbsolutePos;
	}

	/**
	 * @param AbsolutePos
	 */
	public void setAbsolutePos(Point AbsolutePos) {
		this.AbsolutePos = AbsolutePos.copy();
	}

	/**
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
