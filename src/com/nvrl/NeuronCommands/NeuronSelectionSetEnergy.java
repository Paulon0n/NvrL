package com.nvrl.NeuronCommands;

import com.nvrl.Command;
import com.nvrl.CommonNeuronExtension;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michael
 *
 */
public class NeuronSelectionSetEnergy implements Command {

	private final ArrayList<CommonNeuronExtension> theNeurons;
	private final ArrayList<Double> oldD = new ArrayList<Double>();
	;
	private final double newD;

	/**
	 * @param theNeurons
	 * @param newFloat
	 */
	public NeuronSelectionSetEnergy(ArrayList<CommonNeuronExtension> theNeurons, double newFloat) {
		this.theNeurons = new ArrayList<CommonNeuronExtension>(theNeurons);
		for (CommonNeuronExtension n : theNeurons) {
			oldD.add(n.getEnergy());
		}
		this.newD = newFloat;
	}

	@Override
	public boolean unexecute() {
		Iterator<CommonNeuronExtension> i1 = theNeurons.iterator();
		Iterator<Double> i2 = oldD.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			i1.next().setEnergy(i2.next());
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (CommonNeuronExtension n : theNeurons) {
			n.setEnergy(newD);
		}
		return true;
	}

}
