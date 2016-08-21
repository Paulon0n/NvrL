package com.nvrl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * the point class is for position and math
 *
 * @author Michael
 */
public class Point {

	/**
	 * X axis position holder
	 */
	public float X;
	/**
	 * Y axis position holder
	 */
	public float Y;
	/**
	 * Z axis position holder
	 */
	public float Z;

	/**
	 * construct a point
	 */
	public Point() {
		X = 0;
		Y = 0;
		Z = 0;
	}

	/**
	 * construct a point
	 *
	 * @param x
	 * @param y
	 */
	public Point(float x, float y) {
		X = x;
		Y = y;
	}

	/**
	 * construct a point
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(float x, float y, float z) {
		X = x;
		Y = y;
		Z = z;
	}

	/**
	 * construct a point
	 *
	 * @param src
	 */
	public Point(Point src) {
		X = src.X;
		Y = src.Y;
		Z = src.Z;
	}

	/**
	 * add (x,y)
	 *
	 * @param p0
	 * @param p1
	 * @return new point(X + p0, Y + p1);
	 */
	public Point add(float p0, float p1) {
		return new Point(X + p0, Y + p1);
	}

	/**
	 * convert point to data string
	 *
	 * @return "X :" + X + " Y :" + Y + " Z :" + Z
	 */
	@Override
	public String toString() {
		return "X:" + (int)X + " Y:" + (int)Y + " Z:" + (int)Z;
	}

	/**
	 * copy point
	 *
	 * @return new point(this)
	 */
	public Point copy() {
		return new Point(this);
	}

	/**
	 * set point to location p (x,y)
	 *
	 * @param p
	 */
	public void setXY(Point p) {
		X = p.X;
		Y = p.Y;
	}

	/**
	 * set point to location x, y
	 *
	 * @param x
	 * @param y
	 */
	public void setXY(float x, float y) {
		X = x;
		Y = y;
	}

	/**
	 * set point to location p (x,y,z)
	 *
	 * @param p
	 */
	public void setXYZ(Point p) {
		X = p.X;
		Y = p.Y;
		Z = p.Z;
	}

	/**
	 * set point to location x, y, z
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setXYZ(float x, float y, float z) {
		X = x;
		Y = y;
		Z = z;
	}

	/**
	 * set point to location p (x,y,z)
	 *
	 * @param p
	 */
	public void set(Point p) {
		X = p.X;
		Y = p.Y;
		Z = p.Z;
	}

	/**
	 * set point X axis to x (x)
	 *
	 * @param x
	 * @return Point
	 */
	public Point setX(float x) {
		X = x;
		return this;
	}

	/**
	 * get the points x position
	 *
	 * @return float
	 */
	public float getX() {
		return X;
	}

	/**
	 * set the points y position
	 *
	 * @param y
	 * @return Point
	 */
	public Point setY(float y) {
		Y = y;
		return this;
	}

	/**
	 * get the points y position
	 *
	 * @return float
	 */
	public float getY() {
		return Y;
	}

	/**
	 * set the points z position
	 *
	 * @param z
	 * @return Point
	 */
	public Point setZ(float z) {
		Z = z;
		return this;
	}

	/**
	 * get the points z position
	 *
	 * @return float
	 */
	public float getZ() {
		return Z;
	}

	/**
	 * is the points x,y equal to x,y (x,y)
	 *
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean equal(float x, float y) {
		return (X == x && Y == y);
	}

	/**
	 * is the points x,y,z equal to x,y,z
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return boolean
	 */
	public boolean equal(float x, float y, float z) {
		return (X == x && Y == y && Z == z);
	}

	/**
	 * is the point equal to point p
	 *
	 * @param p
	 * @return boolean
	 */
	public boolean equal(Point p) {
		return (X == p.X && Y == p.Y && Z == p.Z);
	}

	/**
	 * delta x
	 *
	 * @param x
	 * @return float
	 */
	public float getDeltaX(float x) {
		return X - x;
	}

	/**
	 * delta Y
	 *
	 * @param y
	 * @return float
	 */
	public float getDeltaY(float y) {
		return Y - y;
	}

	/**
	 * delta Z
	 *
	 * @param z
	 * @return float
	 */
	public float getDeltaZ(float z) {
		return Z - z;
	}

	/**
	 * delta
	 *
	 * @param p
	 * @return Point
	 */
	public Point getDelta(Point p) {
		return new Point(X - p.X, Y - p.Y, Z - p.Z);
	}

	/**
	 * distance
	 *
	 * @param p
	 * @return float
	 */
	public float getDistanceTo(Point p) {
		Point t = getDelta(p);
		return (float) Math.sqrt(t.X * t.X + t.Y * t.Y + t.Z * t.Z);
	}

	/**
	 * fast distance
	 *
	 * @param p
	 * @return float
	 */
	public float getDistNoRoot(Point p) {
		Point t = getDelta(p);
		return t.X * t.X + t.Y * t.Y + t.Z * t.Z;
	}

	/**
	 * vector
	 *
	 * @return float
	 */
	public float distance() {
		return (float) Math.sqrt(X * X + Y * Y + Z * Z);
	}

	/**
	 * distance
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return float
	 */
	public static float distance2D(float x1, float y1, float x2, float y2) {
		float dx = x1 - x2, dy = y1 - y2;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * distance
	 *
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return float
	 */
	public static float distance3D(float x1, float y1, float z1, float x2,
		float y2, float z2) {
		float dx = x1 - x2, dy = y1 - y2, dz = z1 - z2;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * get angle
	 *
	 * @param tx
	 * @param ty
	 * @return float
	 */
	public float getAngle(final float tx, final float ty) {
		float angle = (float) Math.toDegrees(Math.atan2(tx - X, ty - Y));
		return angle < 0 ? angle += 360 : angle;
	}

	/**
	 * get angle
	 *
	 * @param x
	 * @param y
	 * @param tx
	 * @param ty
	 * @return float
	 */
	public static float getAngle(final float x, final float y, final float tx,
		final float ty) {
		float angle = (float) Math.toDegrees(Math.atan2(tx - x, ty - y));
		return angle < 0 ? angle += 360 : angle;
	}

	/**
	 * get angle
	 *
	 * @param p
	 * @return float
	 */
	public float getAngle(Point p) {
		float angle = (float) Math.toDegrees(Math.atan2(p.X - X, p.Y - Y));
		return angle < 0f ? angle += 360f : angle;
	}

	/**
	 * wrap y
	 *
	 * @param min
	 * @param max
	 */
	public void wrapY(float min, float max) {
		Y = Y < min ? max : Y;
		Y = Y > max ? min : Y;
	}

	/**
	 * wrap x
	 *
	 * @param min
	 * @param max
	 */
	public void wrapX(float min, float max) {
		X = X < min ? max : X;
		X = X > max ? min : X;
	}

	/**
	 * clamp x
	 *
	 * @param min
	 * @param max
	 * @return float
	 */
	public float getClampX(float min, float max) {
		return Math.max(min, Math.min(max, X));
	}

	/**
	 * clamp y
	 *
	 * @param min
	 * @param max
	 * @return float
	 */
	public float getClampY(float min, float max) {
		return Math.max(min, Math.min(max, Y));
	}

	/**
	 * clamp x
	 *
	 * @param min
	 * @param max
	 */
	public void clampX(float min, float max) {
		X = Math.max(min, Math.min(max, X));
	}

	/**
	 * clamp y
	 *
	 * @param min
	 * @param max
	 */
	public void clampY(float min, float max) {
		Y = Math.max(min, Math.min(max, Y));
	}

	/**
	 * clamp new point (x,y)
	 *
	 * @param min
	 * @param max
	 * @return Point
	 */
	public Point getClamp(float min, float max) {
		return new Point(getClampX(min, max), getClampY(min, max));
	}

	/**
	 * clamp point (x,y)
	 *
	 * @param min
	 * @param max
	 */
	public void clamp(float min, float max) {
		clampX(min, max);
		clampY(min, max);
	}

	/**
	 * directional move (x,y)
	 *
	 * @param angleInDegrees
	 * @param distance
	 * @return (x,y)+= (sin,cos)(angleInDegrees)*distance
	 */
	public Point vectorMove(float angleInDegrees, float distance) {
		X += Sin(angleInDegrees) * distance;
		Y += Cos(angleInDegrees) * distance;
		return this.copy();
	}

	/**
	 * vector x in degrees
	 *
	 * @param degree
	 * @param distance
	 * @return X+Sin(angle)*distance
	 */
	public float getVectorX(float degree, float distance) {
		return X + Sin(degree) * distance;
	}

	/**
	 * vector y in degrees
	 *
	 * @param degree
	 * @param distance
	 * @return Y + Cos(angle) * distance
	 */
	public float getVectorY(float degree, float distance) {
		return Y + Cos(degree) * distance;
	}

	/**
	 * vector
	 *
	 * @param angle
	 * @param distance
	 * @return Point
	 */
	public Point getVector(float angle, float distance) {
		return new Point(getVectorX(angle, distance), getVectorY(angle,
			distance));
	}

	/**
	 * vector in radians
	 *
	 * @param angle
	 * @param distance
	 * @return Point
	 */
	public Point VectorInRad(float angle, float distance) {
		return new Point((float) (X + Math.sin(angle) * distance),
			(float) (Y + Math.cos(angle) * distance));
	}

	/**
	 * sin in degrees
	 *
	 * @param degrees
	 * @return float
	 */
	public static float Sin(final float degrees) {
		return (float) Math.sin(Math.toRadians(degrees));
	}

	/**
	 * cos in degrees
	 *
	 * @param degrees
	 * @return float
	 */
	public static float Cos(final float degrees) {
		return (float) Math.cos(Math.toRadians(degrees));
	}

	/**
	 * add
	 *
	 * @param a
	 * @param b
	 * @return Point
	 */
	public static Point add(Point a, Point b) {
		return new Point(a.X + b.X, a.Y + b.Y, a.Z + b.Z);
	}

	/**
	 * add
	 *
	 * @param b
	 * @return new point(X + b.X, Y + b.Y, Z + b.Z)
	 */
	public Point add(Point b) {
		return new Point(X + b.X, Y + b.Y, Z + b.Z);
	}

	/**
	 * sub
	 *
	 * @param a
	 * @param b
	 * @return new point(a.X - b.X, a.Y - b.Y, a.Z - b.Z)
	 */
	public static Point sub(Point a, Point b) {
		return new Point(a.X - b.X, a.Y - b.Y, a.Z - b.Z);
	}

	/**
	 * sub
	 *
	 * @param b
	 * @return new point(X - b.X, Y - b.Y, Z - b.Z)
	 */
	public Point sub(Point b) {
		return new Point(X - b.X, Y - b.Y, Z - b.Z);
	}

	/**
	 * sub (x,y)
	 *
	 * @param p0
	 * @param p1
	 * @return new point(X - p0, Y - p1)
	 */
	public Point sub(float p0, float p1) {
		return new Point(X - p0, Y - p1);
	}
	
	public Point div(int in ){
		return new Point(X/in,Y/in);
	}
	
	public static Point average(Point ... in ){
		Point avg = new Point();
		for(int i =0;i<in.length;i++){
			avg =avg.add(in[i]);
		}
		return avg.div(in.length);
	}
	
	public Point inv(){
		return new Point(-X,-Y,Z);
	}
	/**
	 * scale (x,y)
	 *
	 * @param b
	 * @return new point(X * b, Y * b)
	 */
	public Point scale(float b) {
		return new Point(X * b, Y * b);
	}

	/**
	 * multiply (x,y)
	 *
	 * @param x
	 * @param y
	 * @return new point(X * x, Y * y)
	 */
	public Point scale(float x, float y) {
		return new Point(X * x, Y * y);
	}

	/**
	 * draw circle
	 *
	 * @param canvas
	 * @param r
	 * @param pa
	 */
	public void drawRect(Canvas canvas, float r, Paint pa) {
		// canvas.drawCircle(X, Y, r, pa);
		canvas.drawRect(X - r / 2, Y - r / 2, X + r / 2, Y + r / 2, pa);
	}

	/**
	 * draw cross
	 *
	 * @param canvas
	 * @param r
	 * @param pa
	 */
	public void drawCross(Canvas canvas, float r, Paint pa) {
		canvas.drawLine(X - r, Y, X + r, Y, pa);
		canvas.drawLine(X, Y - r, X, Y + r, pa);
	}

	/**
	 * draw x
	 *
	 * @param canvas
	 * @param r
	 * @param pa
	 */
	public void drawX(Canvas canvas, float r, Paint pa) {
		float a1 = r * FastMath.Sin(45);
		float a2 = r * FastMath.Cos(45);
		float a3 = r * FastMath.Sin(-45);
		float a4 = r * FastMath.Cos(-45);
		canvas.drawLine(X - a1, Y - a2, X + a1, Y + a2, pa);
		canvas.drawLine(X - a3, Y - a4, X + a3, Y + a4, pa);
	}

	/**
	 * swap x
	 *
	 * @param b
	 */
	public void swapX(Point b) {
		float t = X;
		X = b.X;
		b.X = t;
	}

	/**
	 * swap y
	 *
	 * @param b
	 */
	public void swapY(Point b) {
		float t = Y;
		Y = b.Y;
		b.Y = t;
	}

	/**
	 * swap z
	 *
	 * @param b
	 */
	public void swapZ(Point b) {
		float t = Z;
		Z = b.Z;
		b.Z = t;
	}

	/**
	 * sort
	 *
	 * @param b
	 */
	public void sort(Point b) {
		if (X > b.X) {
			swapX(b);
		}
		if (Y > b.Y) {
			swapY(b);
		}
		if (Z > b.Z) {
			swapZ(b);
		}
	}

	/**
	 * draw line
	 *
	 * @param canvas
	 * @param p
	 * @param pa
	 */
	public void drawLine(Canvas canvas, Point p, Paint pa) {
		canvas.drawLine(X, Y, p.X, p.Y, pa);
	}

	/**
	 * draw square
	 *
	 * @param canvas
	 * @param size
	 * @param pa
	 */
	public void drawSquare(Canvas canvas, float size, Paint pa) {
		size /= 2;
		canvas.drawRect(X - size, Y - size, X + size, Y + size, pa);
	}

	/**
	 * draw rectangle
	 *
	 * @param canvas
	 * @param p
	 * @param pa
	 */
	public void drawRect(Canvas canvas, Point p, Paint pa) {
		canvas.drawRect(X, Y, p.X, p.Y, pa);
	}

	/**
	 * draw rectangle
	 *
	 * @param canvas
	 * @param p
	 * @param pa
	 */
	public void drawRect_noSort(Canvas canvas, Point p, Paint pa) {
		RectF r = new RectF(X, Y, p.X, p.Y);
		r.sort();
		canvas.drawRect(r, pa);
	}

	/**
	 * point in rectangle
	 *
	 * @param RectPointA
	 * @param RectPointB
	 * @param c
	 * @return boolean
	 */
	public static boolean pointInRect(Point RectPointA, Point RectPointB,
		Point c) {
		Point ta = RectPointA.copy(), tb = RectPointB.copy();
		ta.sort(tb);
		return c.X >= ta.X && c.X <= tb.X && c.Y >= ta.Y && c.Y <= tb.Y
			&& c.Z >= ta.Z && c.Z <= tb.Z;
	}

	/**
	 * is point in rectangle
	 *
	 * @param RectPointA
	 * @param RectPointB
	 * @return boolean
	 */
	public boolean isInRect(Point RectPointA, Point RectPointB) {
		Point ta = RectPointA.copy(), tb = RectPointB.copy();
		ta.sort(tb);
		return X >= ta.X && X <= tb.X && Y >= ta.Y && Y <= tb.Y && Z >= ta.Z
			&& Z <= tb.Z;
	}

	/**
	 * to rectF
	 *
	 * @param a
	 * @param b
	 * @return RectF
	 */
	public static RectF toRectF(Point a, Point b) {
		return new RectF(a.X, a.Y, b.X, b.Y);
	}

	/**
	 * rectangle intersect rectangle
	 *
	 * @param r1a
	 * @param r1b
	 * @param r2a
	 * @param r2b
	 * @return boolean
	 */
	public static boolean RectIntersect(Point r1a, Point r1b, Point r2a,
		Point r2b) {
		RectF r1 = Point.toRectF(r1a, r1b);
		RectF r2 = Point.toRectF(r2a, r2b);
		return RectF.intersects(r1, r2);
	}

	/**
	 * convert point to grid
	 *
	 * @return Point
	 */
	public Point toGrid() {
		return Grid.ScreenToGrid(this.sub(DisplayEnvironment.getScreenCenter()))
			.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
	}

	/**
	 * @return Point
	 */
	public Point snap() {
		return Grid.snap(this);
	}
}
