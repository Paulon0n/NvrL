package com.nvrl.NeuronCommands;

import com.nvrl.*;

public class SetClampMin implements Command 
{

	private final CommonNeuronExtension theNeuron;
	private final float oldMin;
	private final float newMin;
	
	public SetClampMin( CommonNeuronExtension theNeuron , float newMin){
		this.theNeuron = theNeuron;
		this.oldMin = theNeuron.getMinClamp();
		this.newMin = newMin;

	}
	@Override
	public boolean unexecute() {
		theNeuron.setMinClamp(oldMin);
		return true;
	}

	@Override
	public boolean execute() {
		theNeuron.setMinClamp(newMin);
		return true;
	}
	
}
