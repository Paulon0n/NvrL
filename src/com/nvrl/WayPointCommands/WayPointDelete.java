package com.nvrl.WayPointCommands;

import com.nvrl.Command;
import com.nvrl.WayPoint;

/**
 * @author Michael
 *
 */
public class WayPointDelete implements Command {

	private final WayPoint theWayPoint;

	/**
	 * @param wp
	 */
	public WayPointDelete(WayPoint wp) {
		theWayPoint = wp;
	}

	@Override
	public boolean execute() {
		theWayPoint.getHost().getWayPoints().remove(theWayPoint);
		WayPoint.getAllWayPoints().remove(theWayPoint);
		return true;
	}

	@Override
	public boolean unexecute() {
		theWayPoint.getHost().getWayPoints().add(theWayPoint);
		WayPoint.getAllWayPoints().add(theWayPoint);
		return true;
	}
}
