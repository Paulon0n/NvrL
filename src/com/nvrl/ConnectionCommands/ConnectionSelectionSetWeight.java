package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;
import com.nvrl.FileIO;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michael
 *
 */
public class ConnectionSelectionSetWeight implements Command {

	private final ArrayList<Connection> theConnections;
	private final ArrayList<Double> oldFloat = new ArrayList<Double>();
	private final double newDouble;
	private final boolean rnd;

	/**
	 * @param theConnections
	 * @param newFloat
	 */
	public ConnectionSelectionSetWeight(ArrayList<Connection> theConnections,
										double newFloat) {
		this.theConnections = new ArrayList<Connection>(theConnections);
		rnd = false;
		for (Connection n : theConnections) {
			oldFloat.add(n.getWeight());
		}
		this.newDouble = newFloat;
	}

	/**
	 * @param theConnections
	 */
	public ConnectionSelectionSetWeight(ArrayList<Connection> theConnections) {
		this.theConnections = new ArrayList<Connection>(theConnections);
		rnd = true;
		for (Connection n : theConnections) {
			oldFloat.add(n.getWeight());
		}
		this.newDouble = 0f;
	}

	@Override
	public boolean unexecute() {
		Iterator<Connection> i1 = theConnections.iterator();
		Iterator<Double> i2 = oldFloat.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			i1.next().setWeight(i2.next());
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (Connection n : theConnections) {
			if (rnd) {
				n.setWeight(FileIO.Rand());
			} else {
				n.setWeight(newDouble);
			}
		}
		return true;
	}

}
