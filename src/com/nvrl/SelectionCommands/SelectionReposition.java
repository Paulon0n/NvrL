package com.nvrl.SelectionCommands;

import com.nvrl.*;
import java.util.*;

/**
 * @author Michael
 *
 */
public class SelectionReposition implements Command {

	private final ArrayList<CommonNeuronExtension> aln;
	private final ArrayList<Cluster> alc;
	private final ArrayList<WayPoint> alw;
	private final ArrayList<Point> alnpNew = new ArrayList<Point>();
	private final ArrayList<Point> alcpNew = new ArrayList<Point>();
	private final ArrayList<Point> alcpNewSize = new ArrayList<Point>();
	private final ArrayList<Point> alwpNew = new ArrayList<Point>();
	private final ArrayList<Point> alnpOld = new ArrayList<Point>();
	private final ArrayList<Point> alcpOld = new ArrayList<Point>();
	private final ArrayList<Point> alcpOldSize = new ArrayList<Point>();
	private final ArrayList<Point> alwpOld = new ArrayList<Point>();

	/**
	 * @param aln
	 * @param alc
	 * @param alw
	 */
	public SelectionReposition(final ArrayList<CommonNeuronExtension> aln,
		final ArrayList<Cluster> alc, final ArrayList<WayPoint> alw) {
		this.aln = new ArrayList<  CommonNeuronExtension >(aln);
		this.alc = new ArrayList<Cluster>(alc);
		this.alw = new ArrayList<WayPoint>(alw);
		setOld();
	}

	/**
	 *
	 */
	public void setOld() {
		for (final CommonNeuronExtension n : aln) {
			alnpOld.add(n.getPos().copy());
		}
		for (final Cluster c : alc) {
			alcpOld.add(c.getPos().copy());
			alcpOldSize.add(c.getSize().copy());
		}
		for (final WayPoint w : alw) {
			alwpOld.add(w.getPos().copy());
		}
	}

	/**
	 * @param sr
	 */
	public static void setNew(final SelectionReposition sr) {
		for (final CommonNeuronExtension n : sr.aln) {
			sr.alnpNew.add(n.getPos().copy());
		}
		for (final Cluster c : sr.alc) {
			sr.alcpNew.add(c.getPos().copy());
			sr.alcpNewSize.add( c.getSize().copy());
		}
		for (final WayPoint w : sr.alw) {
			sr.alwpNew.add(w.getPos().copy());
		}
	}

	@Override
	public boolean unexecute() {
		Iterator<?> i1 = aln.iterator(), i2 = alnpOld.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final CommonNeuronExtension n = (CommonNeuronExtension) i1.next();
			final Point p = (Point) i2.next();
			
			n.setPos(p.copy());
		}
		i1 = alc.iterator();
		i2 = alcpOld.iterator();
		Iterator<?> i3 = alcpOldSize.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final Cluster n = (Cluster) i1.next();
			final Point p = (Point) i2.next();
			final Point s = (Point) i3.next();
			n.setPos(p.copy());
			n.setSize(s.copy());
		}
		i1 = alw.iterator();
		i2 = alwpOld.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final WayPoint n = (WayPoint) i1.next();
			final Point p = (Point) i2.next();
			n.setPos(p.copy());
		}
		return true;
	}

	@Override
	public boolean execute() {
		Iterator<?> i1 = aln.iterator(), i2 = alnpNew.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final CommonNeuronExtension n = (CommonNeuronExtension) i1.next();
			final Point p = (Point) i2.next();
			n.setPos(p.copy());
		}
		i1 = alc.iterator();
		i2 = alcpNew.iterator();
		Iterator<?> i3 = alcpNewSize.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final Cluster n = (Cluster) i1.next();
			final Point p = (Point) i2.next();
			final Point s = (Point) i3.next();
			n.setPos(p.copy());
			n.setSize(s.copy());
		}
		i1 = alw.iterator();
		i2 = alwpNew.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			final WayPoint n = (WayPoint) i1.next();
			final Point p = (Point) i2.next();
			n.setPos(p.copy());
		}
		return true;
	}

}
