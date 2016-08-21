package com.nvrl.NeuralNetCommands;

import com.nvrl.*;
import java.util.*;

/**
 *
 * @author Michael
 */
public class AddCluster implements Command {

	private final Cluster theCluster;
	private final ArrayList<CommonNeuronExtension> aln;

	/**
	 * @param theCluster
	 */
	public AddCluster(Cluster theCluster) {
		this.theCluster = theCluster;
		aln = new ArrayList<CommonNeuronExtension>(Neuron.getSelection());
	}

	@Override
	public boolean unexecute() {
		for (CommonNeuronExtension n : aln) {
			n.setPos(n.getAbsolutePos().copy());
			n.setCluster(null);
		}
		theCluster.Detach();
		theCluster.nn.getNeurons().addAll(aln);
		return true;
	}

	@Override
	public boolean execute() {
		for (CommonNeuronExtension n : aln) {
			n.setPos(n.getAbsolutePos().sub(theCluster.getPos()));
			n.setCluster(theCluster);
		}
		return theCluster.Attach();
	}
}
