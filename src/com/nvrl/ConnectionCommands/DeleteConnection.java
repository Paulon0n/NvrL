package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;
import com.nvrl.WayPoint;

/**
 * @author Michael
 *
 */
public class DeleteConnection implements Command {

	private final Connection theConnection;
	private final int in, out;

	/**
	 * @param theConnection
	 */
	public DeleteConnection(Connection theConnection) {
		this.theConnection = theConnection;
		in = theConnection.getInput().getOutputs().indexOf(theConnection);
		out = theConnection.getOutput().getInputs().indexOf(theConnection);
	}

	@Override
	public boolean unexecute() {
		theConnection.getOutput().inputs.add(out, theConnection);
		theConnection.getInput().outputs.add(in, theConnection);
		WayPoint.getAllWayPoints().addAll(theConnection.getWayPoints());
		return true;
	}

	@Override
	public boolean execute() {
		theConnection.remove();
		return true;
	}

}
