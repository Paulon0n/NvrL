package com.nvrl.ClusterCommands;

import com.nvrl.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class ClusterResize implements Command {

	private final Cluster theCluster;
	private final Point newp, news;
	private final Point oldp, olds;
	private final ArrayList<CommonNeuronExtension> aln;

	/**
	 * @param theCluster
	 * @param newp
	 * @param news
	 */
	public ClusterResize(Cluster theCluster, Point newp, Point news) {
		this.theCluster = theCluster;
		this.newp = newp.copy();
		this.news = news.copy();
		olds = theCluster.getSize().copy();
		oldp = theCluster.getPos().copy();
		aln = new ArrayList< CommonNeuronExtension >(Neuron.getSelection());
	}

	@Override
	public boolean unexecute() {
		Point p = oldp.sub(newp);
		this.theCluster.getNeurons().removeAll(aln);
		for (CommonNeuronExtension n : aln) {
			n.setPos(n.getAbsolutePos().copy());
			n.setCluster(null);
		}

		for (CommonNeuronExtension n : theCluster.getNeurons()) {
			n.setCluster(theCluster);
			n.setPos(n.getPos().sub(p));
		}
		theCluster.setPos(oldp.copy());
		theCluster.setSize(olds.copy());
		return true;
	}

	@Override
	public boolean execute() {
		Point p = oldp.sub(newp);
		this.theCluster.getNeurons().addAll(aln);
		for (CommonNeuronExtension n : aln) {
			n.setPos(n.getAbsolutePos().sub(theCluster.getPos()));
			n.setCluster(theCluster);
		}
		for (CommonNeuronExtension n : theCluster.getNeurons()) {
			n.setCluster(theCluster);
			n.setPos(n.getPos().add(p));
		}
		theCluster.setPos(newp.copy());
		theCluster.setSize(news.copy());
		return true;
	}

}
