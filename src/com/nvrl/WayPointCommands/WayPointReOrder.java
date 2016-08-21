package com.nvrl.WayPointCommands;

import com.nvrl.Command;
import com.nvrl.Connection;
import com.nvrl.WayPoint;
import java.util.ArrayList;

/**
 * @author Michael
 *
 */
public class WayPointReOrder implements Command {

	private final Connection host;
	private final ArrayList<WayPoint> before, after;

	/**
	 * @param h
	 * @param before
	 */
	public WayPointReOrder(Connection h, ArrayList<WayPoint> before) {
		this.before = new ArrayList<WayPoint>(before);
		this.after = new ArrayList<WayPoint>(h.getWayPoints());
		host = h;
	}

	@Override
	public boolean unexecute() {
		host.getWayPoints().clear();
		host.getWayPoints().addAll(before);
		return true;
	}

	@Override
	public boolean execute() {
		host.getWayPoints().clear();
		host.getWayPoints().addAll(after);
		return true;
	}

}
