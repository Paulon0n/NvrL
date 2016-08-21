package com.nvrl;

import java.util.ArrayList;

/**
 * WayPoint is a point which a Connection path must pass through
 *
 * @author Michael
 */
public class WayPoint extends CommonExtensions {

	/**
	 * The selection manager for way points
	 */
	public static final SelectionManager<WayPoint> SELECTION = new SelectionManager<WayPoint>();
	private Connection Host;
	private static final ArrayList<WayPoint> ALL_WAY_POINTS = new ArrayList<WayPoint>();

	/**
	 * Construct a WayPoint
	 * (Warning) does not assign to connection or all way points
	 * @param c
	 * @param p
	 */
	public WayPoint(Connection c, Point p) {
		Pos = p.copy();
		Host = c;
	}

	/**
	 * Construct a WayPoint
	 *
	 * @param c
	 */
	public WayPoint(Connection c) {
		this.Host = c;
		WayPoint t = this;
		this.Host.AddWayPoint(t);
		WayPoint.ALL_WAY_POINTS.add(t);
	}

	/**
	 * Create a WayPoint at Point p
	 *
	 * @param p
	 */
	public WayPoint(Point p) {
		Pos = p.copy();
	}

	/**
	 * Get a list of all WayPoints
	 *
	 * @return ArrayList<WayPoint>
	 */
	public static ArrayList<WayPoint> getAllWayPoints() {
		return ALL_WAY_POINTS;
	}

	/**
	 * Get the absolute position of the WayPoint
	 *
	 * @return The absolute grid position of the WayPoint
	 */
	@Override
	public Point getAbsolutePos() {
		return Pos;
	}

	/**
	 * Get the Connection the WayPoint is attached to
	 *
	 * @return Connection
	 */
	public Connection getHost() {
		return Host;
	}

	/**
	 * Construct a copy of a WayPoint
	 *
	 * @param c
	 * @return WayPoint
	 */
	public WayPoint copy(Connection c) {
		WayPoint w = new WayPoint(c);
		w.Pos = getAbsolutePos();
		return w;
	}
	public WayPoint copy(Connection c,Point offset) {
		WayPoint w = new WayPoint(c);
		w.Pos = getAbsolutePos().add(offset);
		return w;
	}
	/**
	 * Get the WayPoints within a selection
	 *
	 * @return ArrayList<WayPoint>
	 */
	public static ArrayList<WayPoint> getSelection() {
		return SELECTION.getSelection();
	}

	/**
	 * Set the active WayPoint that is selected
	 *
	 * @param selected
	 */
	public static void setSelected(WayPoint selected) {
		WayPoint.SELECTION.setSelected(selected);
	}

	/**
	 * Get the actively selected WayPoint
	 *
	 * @return WayPoint
	 */
	public static WayPoint getSelected() {
		return SELECTION.getSelected();
	}

	/**
	 * Get a data string of the WayPoints properties
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return Pos.X + "&" + Pos.Y + "&" + Pos.Z + "&";
	}
}
