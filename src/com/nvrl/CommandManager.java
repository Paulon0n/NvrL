package com.nvrl;

/**
 * Manages a Queue of Commands to perform undo and redo operations.
 *
 * @author Michael Camacho
 * @version 1.0
 */
public class CommandManager {

	// the current index node
	private Node currentIndex = null;

	// the parent node far left node.
	private Node parentNode = new Node();

	/**
	 * Creates a new CommandManager object which begins empty.
	 */
	public CommandManager() {
		currentIndex = parentNode;
	}

	/**
	 * Create a new CommandManager which is a duplicate of the
	 * parameter in both contents and current index.
	 *
	 * @param manager
	 */
	public CommandManager(CommandManager manager) {
		this();
		currentIndex = manager.currentIndex;
	}

	/**
	 * Clears all Commands contained in this manager.
	 */
	public void clear() {
		currentIndex = parentNode;
		if (parentNode.right != null) {
			parentNode.right.left = null;
		}
		parentNode.right = null;
	}

	/**
	 * Adds a Command to manage.
	 *
	 * @param command
	 * @return success
	 */
	public boolean addCommand(Command command) {
		// execute the command
		if (!command.execute()) {
			return false; // fail
		}
		Node node = new Node(command);
		if (currentIndex.right != null) {
			currentIndex.right.left = null;
		}
		currentIndex.right = node;
		node.left = currentIndex;
		currentIndex = node;

		return true; // success
	}

	/**
	 * Determines if an undo can be performed.
	 *
	 * @return true if can unexecute
	 */
	public boolean canUnexecute() {
		return currentIndex != parentNode;
	}

	/**
	 * Determines if a redo can be performed.
	 *
	 * @return true if can execute
	 */
	public boolean canExecute() {
		return currentIndex.right != null;
	}

	/**
	 * Undoes the Command at the current index.
	 *
	 * @return true if unexecuted
	 */
	public boolean undo() {
		// validate
		if (canUnexecute()) {
			// undo
			currentIndex.command.unexecute();
		}
		return moveLeft();
	}

	/**
	 * Moves internal pointer of the backed linked list to the left.
	 *
	 * @return
	 */
	private boolean moveLeft() {
		if (currentIndex.left == null) {
			return false;
		}
		currentIndex = currentIndex.left;
		return true;
	}

	/**
	 * Moves internal pointer of the backed linked list to the right.
	 *
	 * @return
	 */
	private boolean moveRight() {
		if (currentIndex.right == null) {
			return false;
		}
		currentIndex = currentIndex.right;
		return true;
	}

	/**
	 * Redo the Command at the current index.
	 *
	 * @return success
	 */
	public boolean redo() {
		// validate
		if ((!canExecute() || !moveRight())) {
			return false;
		}

		// redo
		currentIndex.command.execute();
		return true;
	}

	/**
	 * Inner class is doubly linked list for the queue of Commands.
	 *
	 * @author Michael Camacho
	 *
	 */
	private class Node {

		private Node left = null;
		private Node right = null;

		private final Command command;

		public Node(Command c) {
			command = c;
		}

		public Node() {
			command = null;
		}
	}
}
