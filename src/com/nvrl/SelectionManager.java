package com.nvrl;

import static com.nvrl.DisplayEnvironment.getActiveLayer;
import static com.nvrl.NvrLView.getTouch;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 *
 * @param <T>
 */
public class SelectionManager<T extends CommonExtensions> {

	private T t;
	private T Selected = null;
	private T PreviouslySelected = null;
	private final ArrayList<T> m;

	/**
	 * @return T
	 */
	public T getSelected() {
		return Selected;
	}

	/**
	 * @param Selected
	 */
	public void setSelected(T Selected) {
		this.Selected = Selected;
	}

	/**
	 *
	 */
	public SelectionManager() {
		m = new ArrayList<T>();
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

	/**
	 * @param obj
	 */
	public void add(T obj) {
		m.add(obj);
	}

	/**
	 *
	 */
	public void clear() {
		m.clear();
	}

	/**
	 * @return ArrayList<T>
	 */
	public ArrayList<T> getSelection() {
		return m;
	}

	/**
	 * @param list
	 * @return ArrayList<T>
	 */
	public ArrayList<T> touchInSelector(ArrayList<T> list) {
		selector(list);
		m.clear();
		for (T c : list) {
			if (getTouch().toGrid().setZ(getActiveLayer())
				.isInRect(c.Pos, c.Pos.add(c.Size))) {
				m.add(c);
			}
		}
		return m;
	}

	/**
	 * @param list
	 * @return T
	 */
	public T selector(ArrayList<T> list) {
		Selected = null;
		List<T> copy = new ArrayList<T>(list);
		for (T c : copy) {
			Point tp = getTouch().toGrid().setZ(getActiveLayer());
			Point tt = c.getAbsolutePos();
			if (tp.equal(tt)) {
				Selected = c;
				return c;
			}
		}
		return null;
	}

	/**
	 * @param list
	 */
	public void Prev_selector(ArrayList<T> list) {
		T pre = PreviouslySelected;
		PreviouslySelected = Selected;
		T thisSel = selector(list);

		if (PreviouslySelected != null && thisSel != null) {
			Selected = thisSel;
		} else if (thisSel != null && PreviouslySelected == null) {
			Selected = thisSel;
			PreviouslySelected = pre;
		} else if (thisSel == null && PreviouslySelected != null) {
			Selected = null;
		} else if (thisSel == null && PreviouslySelected == null) {
			PreviouslySelected = pre;
		}
	}

	/**
	 * Identifies and references a selection of Clusters, Neurons,
	 * Connections, and WayPoints
	 *
	 * @param a
	 * @param b
	 */
	public static void getSelectedFromSelection(Point a, Point b) {
		Connection.getSelection().clear();
		Connection.getAbsoluteSelection().clear();
		WayPoint.getSelection().clear();
		Neuron.getSelection().clear();
		Cluster.getSelection().clear();
		Neuron.getAbsoluteSelection().clear();
		for (CommonNeuronExtension n : NeuralNet.getSelected().getNeurons()) {
			if (!n.isInCluster()) {
				if (n.getAbsolutePos().isInRect(a, b)) {
					Neuron.getSelection().add(n);
					Neuron.getAbsoluteSelection().add(n);
				}
			} else if (n.getAbsolutePos().isInRect(a, b)) {
				Neuron.getAbsoluteSelection().add(n);
			}
		}
		for (Cluster c : NeuralNet.getSelected().getClusters()) {
			if (c.Pos.isInRect(a, b) && (c.Pos.add(c.Size)).isInRect(a, b)
				|| Point.RectIntersect(c.Pos, c.Pos.add(c.Size), a, b)) {
				Cluster.getSelection().add(c);
			}
		}
		for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
			Connection.getAbsoluteSelection().addAll(n.getInputs());
			Connection.getAbsoluteSelection().addAll(n.getOutputs());

			for (Connection c : n.getInputs()) {
				Connection.getAbsoluteSelection().add(c);
				if (Neuron.getAbsoluteSelection().contains(c.getInput())) {
					Connection.getSelection().add(c);
				}
				if (!c.getWayPoints().isEmpty()) {
					for (WayPoint wp : c.getWayPoints()) {
						if (wp.getAbsolutePos().isInRect(a, b)) {
							WayPoint.getSelection().add(wp);
						}
					}
				}
			}
		}
		if (Connection.getSelection().size() == 1) {
			Connection.setSelected(Connection.getSelection().get(0));
		}
	}

	/**
	 * @return T
	 */
	public T getPreviouslySelected() {
		return PreviouslySelected;
	}

	/**
	 * @param PreviouslySelected
	 */
	public void setPreviouslySelected(T PreviouslySelected) {
		this.PreviouslySelected = PreviouslySelected;
	}

}
