package com.nvrl.PointCommands;

import com.nvrl.Command;
import com.nvrl.Point;

/**
 *
 * @author Michael
 */
public class PointSubtraction implements Command {

	private Point thePointA;
	private final Point thePointB;

	/**
	 * @param thePointA
	 * @param thePointB
	 */
	public PointSubtraction(Point thePointA, Point thePointB) {
		this.thePointA = thePointA;
		this.thePointB = thePointB;
	}

	@Override
	public boolean unexecute() {
		thePointA = thePointA.add(thePointB);
		return true;
	}

	@Override
	public boolean execute() {
		thePointA = thePointA.sub(thePointB);
		return true;
	}

}
