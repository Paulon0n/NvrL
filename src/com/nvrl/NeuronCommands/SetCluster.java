package com.nvrl.NeuronCommands;

import com.nvrl.*;

/**
 * @author Michael
 *
 */
public class SetCluster implements Command {

	private final CommonNeuronExtension theNeuron;
	private final Cluster oldCluster;
	private final Cluster newCluster;
	private final Point oldPoint;
	private final Point newPoint;

	/**
	 * @param theNeuron
	 * @param newCluster
	 * @param newPoint
	 */
	public SetCluster(CommonNeuronExtension theNeuron, Cluster newCluster, Point newPoint) {
		this.theNeuron = theNeuron;
		this.oldCluster = theNeuron.getCluster();
		this.newCluster = newCluster;
		this.oldPoint = theNeuron.getPos().copy();
		this.newPoint = newPoint.copy();
	}

	@Override
	public boolean unexecute() {
		theNeuron.setCluster(oldCluster);
		theNeuron.setPos(oldPoint);
		return true;
	}

	@Override
	public boolean execute() {
		theNeuron.setCluster(newCluster);
		theNeuron.setPos(newPoint);
		return true;
	}

}
