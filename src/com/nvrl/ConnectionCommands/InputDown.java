package com.nvrl.ConnectionCommands;

import com.nvrl.Command;
import com.nvrl.Connection;
import java.util.Collections;

/**
 * @author Michael
 *
 */
public class InputDown implements Command {

	private final Connection theConnection;
	private final int index;

	/**
	 * @param theConnection
	 * @param index
	 */
	public InputDown(Connection theConnection, int index) {
		this.theConnection = theConnection;
		this.index = index;
	}

	@Override
	public boolean unexecute() {
		Collections.swap(theConnection.getInput().outputs, index, index + 1);
		return true;
	}

	@Override
	public boolean execute() {
		Collections.swap(theConnection.getInput().outputs, index, index + 1);
		return true;
	}

}
