package com.nvrl.SelectionCommands;

import com.nvrl.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class SelectionPaste implements Command {

	private final ArrayList<CommonNeuronExtension> aln;
	private final ArrayList<Cluster> alc;

	/**
	 * @param aln1
	 * @param alc1
	 */
	public SelectionPaste(ArrayList<CommonNeuronExtension> aln1, ArrayList<Cluster> alc1) {
		this.aln = new ArrayList<CommonNeuronExtension>(aln1);
		this.alc = new ArrayList<Cluster>(alc1);
	}

	@Override
	public boolean unexecute() {
		NeuralNet.getSelected().getNeurons().removeAll(aln);
		// NeuralNet.getSelected().getClusters().removeAll(alc);
		for (Cluster c : alc) {
			c.Detach();
		}
		return true;
	}

	@Override
	public boolean execute() {
		NeuralNet.getSelected().getNeurons().addAll(aln);
		// NeuralNet.getSelected().getClusters().addAll(alc);
		for (Cluster c : alc) {
			c.Attach();
		}
		return true;
	}

}
