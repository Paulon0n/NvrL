
package com.nvrl.ClusterCommands;

import com.nvrl.Cluster;
import com.nvrl.Command;
import com.nvrl.Point;
import com.nvrl.WayPoint;

/**
 *
 * @author Michael Camacho
 */
public class ClusterPlace implements Command {
	
	private final Cluster theCluster;
	private final Point oldPos;
	private final Point newPos;
	
	public ClusterPlace(Cluster in,Point pos){
		this.theCluster = in;
		oldPos = theCluster.getAbsolutePos();
		newPos = pos;
	}
	
	public boolean unexecute() {
		theCluster.setPos(oldPos);
		for(WayPoint w:theCluster.getWayPoints()){
			w.setPos(newPos.add(w.getPos()).sub(oldPos));
		}
		return true;
	}

	public boolean execute() {
		theCluster.setPos(newPos);
		for(WayPoint w:theCluster.getWayPoints()){
			w.setPos(w.getPos().sub(oldPos).add(newPos));
		}
		return true;
	}
	
}
