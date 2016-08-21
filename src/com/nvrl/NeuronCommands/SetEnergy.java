package com.nvrl.NeuronCommands;

import com.nvrl.*;

/**
 * @author Michael
 *
 */
public class SetEnergy implements Command {

	private final CommonNeuronExtension theNeuron;
	private final double oldD;
	private final double newD;

	/**
	 * @param theNeuron
	 * @param newFloat
	 */
	public SetEnergy(CommonNeuronExtension theNeuron, double newFloat) {
		this.theNeuron = theNeuron;
		this.oldD = theNeuron.getEnergy();
		this.newD = newFloat;
	}

	@Override
	public boolean unexecute() {
		theNeuron.setEnergy(oldD);
		return true;
	}

	@Override
	public boolean execute() {
		theNeuron.setEnergy(newD);
		return true;
	}

}
