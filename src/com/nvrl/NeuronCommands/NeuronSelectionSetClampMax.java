package com.nvrl.NeuronCommands;
import com.nvrl.*;
import java.util.*;

public class NeuronSelectionSetClampMax implements Command
{
	private final ArrayList<CommonNeuronExtension> theNeurons;
	private final ArrayList<Float> oldFloat = new ArrayList<Float>();
	private final float newFloat;

	/**
	 * @param theNeurons
	 * @param newFloat
	 */
	public NeuronSelectionSetClampMax(ArrayList<CommonNeuronExtension> theNeurons, float newFloat) {
		this.theNeurons = new ArrayList<CommonNeuronExtension>(theNeurons);

		for (CommonNeuronExtension n : theNeurons) {
			oldFloat.add(n.getMaxClamp());
		}
		this.newFloat = newFloat;
	}

	@Override
	public boolean unexecute() {
		Iterator<CommonNeuronExtension> i1 = theNeurons.iterator();
		Iterator<Float> i2 = oldFloat.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			i1.next().setMaxClamp(i2.next());
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (CommonNeuronExtension n : theNeurons) {
			n.setMaxClamp(newFloat);
		}
		return true;
	}
}
