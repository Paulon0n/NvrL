package com.nvrl.CommonCommands;

import com.nvrl.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class AddGroup implements Command {

	private final ArrayList<CommonNeuronExtension> aln;
	private final NeuralNet nn;

	/**
	 * @param aln
	 */
	public AddGroup(ArrayList<CommonNeuronExtension> aln) {
		this.aln = aln;
		nn = NeuralNet.getSelected();
	}

	@Override
	public boolean unexecute() {
		return nn.getNeurons().removeAll(aln);
	}

	@Override
	public boolean execute() {
		return nn.getNeurons().addAll(aln);
	}

}
