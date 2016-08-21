package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;

/**
 * @author Michael
 *
 */
public class AddConnection implements Command {

	private final Connection theConnection;

	/**
	 * @param theConnection
	 */
	public AddConnection(Connection theConnection) {
		this.theConnection = theConnection;
	}

	@Override
	public boolean unexecute() {
		theConnection.remove();
		return true;
	}

	@Override
	public boolean execute() {
		theConnection.connect();
		return true;
	}

}
