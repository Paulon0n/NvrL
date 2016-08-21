package com.nvrl.ClusterCommands;

import com.nvrl.Cluster;
import com.nvrl.Command;

/**
 * @author Michael
 *
 */
public class ClusterPaste implements Command {

	private final Cluster theCluster;

	// private final ArrayList<Neuron> aln;
	/**
	 * @param theCluster
	 */
	public ClusterPaste(Cluster theCluster) {
		this.theCluster = theCluster;
		// aln = new ArrayList<Neuron>(this.theCluster.getNeurons());
	}

	@Override
	public boolean unexecute() {
		theCluster.Detach();
		// if(!aln.isEmpty())theCluster.nn.getNeurons().addAll(aln);
		return true;
	}

	@Override
	public boolean execute() {
		theCluster.Attach();
		return true;
	}

}
