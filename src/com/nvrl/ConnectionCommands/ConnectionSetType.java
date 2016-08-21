package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;

/**
 * @author Michael
 *
 */
public class ConnectionSetType implements Command {

	private final Connection theConnection;
	private final int o, n;

	/**
	 * @param theConnection
	 * @param newt
	 */
	public ConnectionSetType(Connection theConnection, int newt) {
		this.theConnection = theConnection;
		n = newt;
		o = theConnection.getType();
	}

	@Override
	public boolean unexecute() {
		theConnection.setType(o);
		return true;
	}

	@Override
	public boolean execute() {
		theConnection.setType(n);
		return true;
	}

}
