package com.nvrl;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * draw and convert screen positions to/from grid positions
 *
 * @author Michael
 */
public class Grid {

	private static int gridWidth = 100, gridHeight = 100;
	private static int maxGridLines = 0;

	/**
	 * Draw the Grid
	 *
	 * @param c
	 * @param p
	 */
	public void drawGrid(Canvas c, Paint p) {
		int scl = DisplayEnvironment.getScreenCenter().getY() > DisplayEnvironment
			.getScreenCenter().getX() ? (int) DisplayEnvironment
				.getScreenCenter().getY() : (int) DisplayEnvironment
				.getScreenCenter().getX();
		maxGridLines = (int) ((scl * 2)
			/ (gridWidth / DisplayEnvironment.getZoom()) + 1) + 2;

		p.setARGB(100, 255, 255, 255);

		float z = DisplayEnvironment.getZoom();

		int vx = (int) DisplayEnvironment.getViewOffset().getX();
		int vy = (int) DisplayEnvironment.getViewOffset().getY();
		int scx = (int) DisplayEnvironment.getScreenCenter().getX();
		int scy = (int) DisplayEnvironment.getScreenCenter().getY();
		int x = snapx(vx % (gridWidth / z));
		int y = snapy(vy % (gridHeight / z));

		for (int i = -maxGridLines / 2; i <= maxGridLines; i++) {
			int tx = (-x + i * (int) (gridWidth / z)) + scx;
			c.drawLine(tx, 0, tx, scy * 2, p);
		}
		for (int i = -maxGridLines / 2; i <= maxGridLines; i++) {
			int ty = (-y + i * (int) (gridHeight / z)) + scy;
			c.drawLine(0, ty, scx * 2, ty, p);
		}
		
		//draw center
		p.setARGB(100, 255, 25, 25);
		c.drawLine(0,scy,scx*2,scy,p);
		c.drawLine(scx,0,scx,scy*2,p);
		
		// c.drawText(maxGridLines+"",30,90,p);
	}

	/**
	 * snap to grid x-axis
	 *
	 * @param x
	 * @return int
	 */
	public static int snapx(float x) {
		float z = DisplayEnvironment.getZoom();
		int gw = (int) (gridWidth / z);
		return FastMath.round(x / gw) * gw;
	}

	/**
	 * snap to grid y-axis
	 *
	 * @param y
	 * @return int
	 */
	public static int snapy(float y) {
		float z = DisplayEnvironment.getZoom();
		int gh = (int) (gridHeight / z);
		return FastMath.round(y / gh) * gh;
	}

	/**
	 * snap point to both x an y axis
	 *
	 * @param p
	 * @return Point
	 */
	public static Point snap(Point p) {
		Point np = new Point();
		float z = DisplayEnvironment.getZoom();
		int gw = (int) (gridWidth / z);
		np.X = FastMath.round(p.X / gw) * gw;
		int gh = (int) (gridHeight / z);
		np.Y = FastMath.round(p.Y / gh) * gh;
		np.Z = p.Z;
		return np;
	}

	/**
	 * convert grid location to screen location x axis
	 *
	 * @param x
	 * @return int
	 */
	public static int GridToScreenX(float x) {
		float z = DisplayEnvironment.getZoom();
		return (int) (x * (int) (gridWidth / z))
			+ (int) DisplayEnvironment.getScreenCenter().getX()
			- snapx(DisplayEnvironment.getViewOffset().X);
	}

	/**
	 * convert grid location to screen location y axis
	 *
	 * @param y
	 * @return int
	 */
	public static int GridToScreenY(float y) {
		float z = DisplayEnvironment.getZoom();
		return (int) (y * (int) (gridHeight / z))
			+ (int) DisplayEnvironment.getScreenCenter().getY()
			- snapy(DisplayEnvironment.getViewOffset().getY());
	}

	/**
	 * convert grid point to screen point
	 *
	 * @param p
	 * @return Point
	 */
	public static Point GridToScreen(Point p) {
		Point np = new Point();
		np.X = GridToScreenX(p.X);
		np.Y = GridToScreenY(p.Y);
		np.Z = p.Z;
		return np;
	}

	/**
	 * convert screen location to grid location x axis
	 *
	 * @param x
	 * @return int
	 */
	public static int ScreenToGridX(float x) {
		return snapx(x) / gridSpaceX();
	}

	/**
	 * convert screen location to grid y axis location
	 *
	 * @param y
	 * @return int
	 */
	public static int ScreenToGridY(float y) {
		return snapy(y) / gridSpaceY();
	}

	/**
	 * convert screen point to grid point
	 *
	 * @param p
	 * @return Point
	 */
	public static Point ScreenToGrid(Point p) {
		return new Point(snapx(p.X) / gridSpaceX(), snapy(p.Y) / gridSpaceY(),
			p.Z);
	}

	/**
	 * calculate the x axis spacing of the grid based on zoom
	 *
	 * @return int
	 */
	public static int gridSpaceX() {
		return (int) (gridWidth / DisplayEnvironment.getZoom());
	}

	/**
	 * calculate the y axis spacing of the grid based on zoom
	 *
	 * @return int
	 */
	public static int gridSpaceY() {
		return (int) (gridHeight / DisplayEnvironment.getZoom());
	}

	/**
	 * set the width of the grid
	 *
	 * @param gridWidth
	 */
	public static void setGridWidth(int gridWidth) {
		Grid.gridWidth = gridWidth;
	}

	/**
	 * get the width of the grid
	 *
	 * @return int
	 */
	public static int getGridWidth() {
		return gridWidth;
	}

	/**
	 * set grid height
	 *
	 * @param gridHeight
	 */
	public static void setGridHeight(int gridHeight) {
		Grid.gridHeight = gridHeight;
	}

	/**
	 * get grid height
	 *
	 * @return int
	 */
	public static int getGridHeight() {
		return gridHeight;
	}

}
