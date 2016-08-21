package com.nvrl.CommonCommands;

import com.nvrl.Command;
import com.nvrl.CommonExtensions;

/**
 * @author Michael
 *
 * @param <T>
 */
public class SetName<T extends CommonExtensions> implements Command {

	private T t;
	private final T theObject;
	private final String oldName;
	private final String newName;

	/**
	 * @param theObject
	 * @param newName
	 */
	public SetName(T theObject, String newName) {
		this.theObject = theObject;
		this.oldName = theObject.getName();
		this.newName = newName;
	}

	@Override
	public boolean unexecute() {
		theObject.setName(oldName);
		return true;
	}

	@Override
	public boolean execute() {
		theObject.setName(newName);
		return true;
	}

	/**
	 * @return T
	 */
	public T get() {
		return this.t;
	}

	/**
	 * @param t1
	 */
	public void set(T t1) {
		this.t = t1;
	}

}
