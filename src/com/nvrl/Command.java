package com.nvrl;

/**
 * An interface for undo and redo actions
 *
 * @author Michael
 * @version 1.0
 */
public interface Command {

	/**
	 * Undo an action
	 *
	 * @return success
	 */
	public boolean unexecute();

	/**
	 * Repeat an action
	 *
	 * @return success
	 */
	public boolean execute();
}
