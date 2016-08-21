package com.nvrl.NeuronCommands;
import com.nvrl.*;

public class SetClampMax implements Command
{

	private final CommonNeuronExtension theNeuron;
	private final float oldMax;
	private final float newMax;
	
	public SetClampMax( CommonNeuronExtension theNeuron , float newMax){
		this.theNeuron = theNeuron;
		this.oldMax = theNeuron.getMaxClamp();
		this.newMax = newMax;
		
	}
	@Override
	public boolean unexecute() {
		theNeuron.setMaxClamp(oldMax);
		return true;
	}

	@Override
	public boolean execute() {
		theNeuron.setMaxClamp(newMax);
		return true;
	}
	
	
}
