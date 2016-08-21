package com.nvrl;

import java.util.Stack;

/**
 * @author Michael
 *
 */
public class UndoManager {

	private UndoManager() {
		undoActions = new Stack<UserAction>();
		redoActions = new Stack<UserAction>();
	}

	/**
	 * @return boolean
	 */
	public boolean canUndo() {
		return undoActions.size() > 0;
	}

	/**
	 * @return boolean
	 */
	public boolean canRedo() {
		return redoActions.size() > 0;
	}

	/**
	 *
	 */
	public void undo() {
		if (canUndo()) {
			UserAction act = this.undoActions.pop();
			act.undo();
			redoActions.push(act);
		}
	}

	/**
	 *
	 */
	public void redo() {
		if (canRedo()) {
			UserAction act = this.redoActions.pop();
			act.perform();
			undoActions.add(act);
		}
	}

	/**
	 * @param action
	 */
	public void add(UserAction action) {
		this.undoActions.push(action);
		this.redoActions.clear();
	}

	private Stack<UserAction> undoActions;
	private Stack<UserAction> redoActions;
}
