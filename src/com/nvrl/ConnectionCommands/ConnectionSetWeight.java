package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;

/**
 * @author Michael
 *
 */
public class ConnectionSetWeight implements Command {

	private final Connection theConnection;
	private final double oldWeight;
	private final double newWeight;

	/**
	 * @param theConnection
	 * @param new_Weight
	 */
	public ConnectionSetWeight(Connection theConnection, double new_Weight) {
		this.theConnection = theConnection;
		this.oldWeight = theConnection.getEnergy();
		this.newWeight = new_Weight;
	}

	@Override
	public boolean unexecute() {
		theConnection.setWeight(oldWeight);
		return true;
	}

	@Override
	public boolean execute() {
		theConnection.setWeight(newWeight);
		return true;
	}

}
