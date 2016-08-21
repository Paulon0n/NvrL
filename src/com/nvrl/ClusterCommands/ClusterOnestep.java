package com.nvrl.ClusterCommands;

import com.nvrl.Cluster;
import com.nvrl.Command;

/**
 * @author Michael
 *
 */
public class ClusterOnestep implements Command {

	private final Cluster theCluster;
	private final boolean boo;

	/**
	 * @param theCluster
	 * @param boo
	 */
	public ClusterOnestep(Cluster theCluster, boolean boo) {
		this.theCluster = theCluster;
		this.boo = boo;
	}

	@Override
	public boolean unexecute() {
		theCluster.oneStep = !boo;
		return true;
	}

	@Override
	public boolean execute() {
		theCluster.oneStep = boo;
		return true;
	}

}
