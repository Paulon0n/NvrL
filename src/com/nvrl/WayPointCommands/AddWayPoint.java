package com.nvrl.WayPointCommands;

import com.nvrl.Command;
import com.nvrl.WayPoint;

/**
 * @author Michael
 *
 */
public class AddWayPoint implements Command {

	private final WayPoint theWayPoint;

	/**
	 * @param wp
	 */
	public AddWayPoint(WayPoint wp) {
		theWayPoint = wp;
	}

	@Override
	public boolean unexecute() {
		theWayPoint.getHost().getWayPoints().remove(theWayPoint);
		WayPoint.getAllWayPoints().remove(theWayPoint);
		return true;
	}

	@Override
	public boolean execute() {
		theWayPoint.getHost().getWayPoints().add(theWayPoint);
		WayPoint.getAllWayPoints().add(theWayPoint);
		return true;
	}

}
