package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michael
 *
 */
public class ConnectionSelectionSetType implements Command {

	private final ArrayList<Connection> theConnections;
	private final ArrayList<Integer> oldInt = new ArrayList<Integer>();
	;
	private final int newInt;

	/**
	 * @param theConnections
	 * @param newInt
	 */
	public ConnectionSelectionSetType(ArrayList<Connection> theConnections,
		int newInt) {
		this.theConnections = new ArrayList<Connection>(theConnections);
		for (Connection n : theConnections) {
			oldInt.add(n.getType());
		}
		this.newInt = newInt;
	}

	@Override
	public boolean unexecute() {
		Iterator<Connection> i1 = theConnections.iterator();
		Iterator<Integer> i2 = oldInt.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			i1.next().setType(i2.next());
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (Connection n : theConnections) {
			n.setType(newInt);
		}
		return true;
	}

}
