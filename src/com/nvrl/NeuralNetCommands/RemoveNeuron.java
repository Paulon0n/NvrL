package com.nvrl.NeuralNetCommands;

import com.nvrl.*;
import java.util.*;

/**
 *
 * @author Michael
 */
public class RemoveNeuron implements Command {

	private final CommonNeuronExtension theNeuron;
	private final ArrayList<Connection> alcO;
	private final ArrayList<Connection> alcI;

	/**
	 * @param theNeuron
	 */
	public RemoveNeuron(CommonNeuronExtension theNeuron) {
		this.theNeuron = theNeuron;
		alcO = new ArrayList<Connection>(theNeuron.getOutputs());
		alcI = new ArrayList<Connection>(theNeuron.getInputs());
	}

	@Override
	public boolean execute() {
		for (Connection c : alcI) {
			c.disconnect();
		}
		for (Connection c : alcO) {
			c.disconnect();
		}
		CommonNeuronExtension.removeNeuronFromMap(theNeuron.getID());
		return NeuralNet.getSelected().removeneuron(theNeuron);
	}

	@Override
	public boolean unexecute() {
		for (Connection c : alcI) {
			c.connect();
		}
		for (Connection c : alcO) {
			c.connect();
		}
		CommonNeuronExtension.addNeuronToMap(theNeuron);
		return NeuralNet.getSelected().neurons.add(theNeuron);
	}
}
