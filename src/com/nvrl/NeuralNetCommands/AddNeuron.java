package com.nvrl.NeuralNetCommands;

import com.nvrl.*;

/**
 * @author Michael Camacho
 */
public class AddNeuron implements Command {

	private CommonNeuronExtension theNeuron;
	private Point thePoint = null;
	private boolean prog = false;

	/**
	 * @param theNeuron
	 */
	public AddNeuron(CommonNeuronExtension theNeuron, boolean k) {
		this.theNeuron = theNeuron;
		prog = k;
	}
	
	public AddNeuron(CommonNeuronExtension theNeuron) {
		this.theNeuron = theNeuron;
	
	}

	/**
	 * @param theNeuron
	 * @param thePoint
	 */
	public AddNeuron(CommonNeuronExtension theNeuron, Point thePoint, boolean k) {
		this.theNeuron = theNeuron;
		this.thePoint = thePoint.copy();
		prog = k;
	}

	@Override
	public boolean execute() {
		CommonNeuronExtension.addNeuronToMap(theNeuron);
		return NeuralNet.getSelected().addneuron(theNeuron, thePoint,prog);
		
	}

	@Override
	public boolean unexecute() {
		CommonNeuronExtension.removeNeuronFromMap(theNeuron);
		return NeuralNet.getSelected().removeneuron(theNeuron);
	}
}
