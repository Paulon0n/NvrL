package com.nvrl.NeuronCommands;

import com.nvrl.Command;
import com.nvrl.CommonNeuronExtension;
import com.nvrl.FileIO;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michael
 *
 */
public class NeuronSelectionSetThreshold implements Command {

	private final ArrayList<CommonNeuronExtension> theNeurons;
	private final ArrayList<Double> oldD = new ArrayList<Double>();
	;
	private final double newD;
	private final boolean rnd;

	/**
	 * @param theNeurons
	 * @param newFloat
	 */
	public NeuronSelectionSetThreshold(ArrayList<CommonNeuronExtension> theNeurons,
									   double newFloat) {
		this.theNeurons = new ArrayList<CommonNeuronExtension>(theNeurons);
		rnd = false;
		for (CommonNeuronExtension n : theNeurons) {
			oldD.add(n.getThreshold());
		}
		this.newD = newFloat;
	}

	/**
	 * @param theNeurons
	 */
	public NeuronSelectionSetThreshold(ArrayList<CommonNeuronExtension> theNeurons) {
		this.theNeurons = new ArrayList<CommonNeuronExtension>(theNeurons);
		rnd = true;
		for (CommonNeuronExtension n : theNeurons) {
			oldD.add(n.getThreshold());
		}
		this.newD = 0f;
	}

	@Override
	public boolean unexecute() {
		Iterator<CommonNeuronExtension> i1 = theNeurons.iterator();
		Iterator<Double> i2 = oldD.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			i1.next().setThreshold(i2.next());
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (CommonNeuronExtension n : theNeurons) {
			if (rnd) {
				n.setThreshold(FileIO.Rand());
			} else {
				n.setThreshold(newD);
			}
		}
		return true;
	}

}
