package com.nvrl.ClusterCommands;

import com.nvrl.Cluster;
import com.nvrl.Command;

/**
 * @author Michael
 *
 */
public class ClusterSetOnestepCount implements Command {

	private final Cluster theCluster;
	private final int before, after;

	/**
	 * @param theCluster
	 * @param after
	 */
	public ClusterSetOnestepCount(Cluster theCluster, int after) {
		this.theCluster = theCluster;
		this.before = theCluster.oneStepCount;
		this.after = after;
	}

	@Override
	public boolean unexecute() {
		theCluster.oneStepCount = before;
		return true;
	}

	@Override
	public boolean execute() {
		theCluster.oneStepCount = after;
		return true;
	}

}
