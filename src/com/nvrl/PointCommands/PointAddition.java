package com.nvrl.PointCommands;

import com.nvrl.Command;
import com.nvrl.Point;

/**
 *
 * @author Michael
 */
public class PointAddition implements Command {

	private Point thePointA;
	private final Point thePointB;

	/**
	 * @param thePointA
	 * @param thePointB
	 */
	public PointAddition(Point thePointA, Point thePointB) {
		this.thePointA = thePointA;
		this.thePointB = thePointB;
	}

	@Override
	public boolean unexecute() {
		thePointA = thePointA.sub(thePointB);
		return true;
	}

	@Override
	public boolean execute() {
		thePointA = thePointA.add(thePointB);
		return true;
	}

}
