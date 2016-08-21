package com.nvrl.SelectionCommands;

import com.nvrl.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class SelectionDelete implements Command {

	private final ArrayList<CommonNeuronExtension> aln;
	private final ArrayList<Cluster> alc;
	private final ArrayList<Connection> alcon;
	private final NeuralNet nn;

	/**
	 * @param aln
	 * @param alc
	 * @param alcon
	 */
	public SelectionDelete(ArrayList<CommonNeuronExtension> aln, ArrayList<Cluster> alc,
		ArrayList<Connection> alcon) {
		this.aln = new ArrayList<CommonNeuronExtension>(aln);
		this.alc = new ArrayList<Cluster>(alc);
		this.alcon = new ArrayList<Connection>(alcon);
		for (Connection c : alcon) {
			if (aln.contains(c.getInput()) && aln.contains(c.getOutput())) {
				this.alcon.remove(c);
			}
		}
		nn = NeuralNet.getSelected();
	}

	@Override
	public boolean unexecute() {
		nn.getNeurons().addAll(aln);
		for (Cluster c : alc) {
			c.Attach();
		}
		for (Connection c : alcon) {
			c.connect();
		}
		return true;
	}

	@Override
	public boolean execute() {
		nn.getNeurons().removeAll(aln);
		for (Cluster c : alc) {
			c.Detach();
		}
		for (Connection c : alcon) {
			c.disconnect();
		}
		return true;
	}

}
