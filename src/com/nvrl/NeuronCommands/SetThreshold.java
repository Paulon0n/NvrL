package com.nvrl.NeuronCommands;

import com.nvrl.*;

/**
 * @author Michael
 *
 */
public class SetThreshold implements Command {

	private final CommonNeuronExtension theNeuron;
	private final double oldFloat;
	private final double newFloat;

	/**
	 * @param theNeuron
	 * @param newFloat
	 */
	public SetThreshold(CommonNeuronExtension theNeuron, double newFloat) {
		this.theNeuron = theNeuron;
		this.oldFloat = theNeuron.getThreshold();
		this.newFloat = newFloat;
	}

	@Override
	public boolean unexecute() {
		theNeuron.setThreshold(oldFloat);
		return true;
	}

	@Override
	public boolean execute() {
		theNeuron.setThreshold(newFloat);
		return true;
	}

}
