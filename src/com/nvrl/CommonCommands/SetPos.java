package com.nvrl.CommonCommands;

import com.nvrl.Command;
import com.nvrl.CommonExtensions;
import com.nvrl.Point;

/**
 * @author Michael
 *
 * @param <T>
 */
public class SetPos<T extends CommonExtensions> implements Command {

	private T t;
	private final T theObject;
	private final Point oldPoint;
	private final Point newPoint;

	/**
	 * @param theObject
	 * @param newPoint
	 */
	public SetPos(T theObject, Point newPoint) {
		this.theObject = theObject;
		this.oldPoint = theObject.getPos().copy();
		this.newPoint = newPoint.copy();
	}

	@Override
	public boolean unexecute() {
		theObject.setPos(oldPoint);
		return true;
	}

	@Override
	public boolean execute() {
		theObject.setPos(newPoint);
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
