package com.nvrl.NeuralNetCommands;

import com.nvrl.*;
import java.util.*;

/**
 *
 * @author Michael
 */
public class RemoveCluster implements Command {

	private final Cluster theCluster;
	private final ArrayList<Connection> alcio = new ArrayList<Connection>();

	/**
	 * @param theCluster
	 */
	public RemoveCluster(Cluster theCluster) {
		this.theCluster = theCluster;
		for (CommonNeuronExtension n : this.theCluster.getNeurons()) {
			alcio.addAll(n.getInputs());
			alcio.addAll(n.getOutputs());
		}
	}

	@Override
	public boolean unexecute() {
		for (Connection c : alcio) {
			c.connect();
		}
		return theCluster.Attach();
	}

	@Override
	public boolean execute() {
		for (Connection c : alcio) {
			c.disconnect();
		}
		return theCluster.Detach();
	}
}
