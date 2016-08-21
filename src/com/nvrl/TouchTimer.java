package com.nvrl;

import android.view.MotionEvent;

/**
 * a timer for events, more specifically touch events
 *
 * @author Michael
 */
public class TouchTimer {

	private long StartTime;
	private long Duration;
	private float TouchedX, TouchedY;
	private float wobble = 0;

	/**
	 * Identify a trigger ID
	 */
	public static final int GreaterThan = 0;

	/**
	 * Identify a trigger ID
	 */
	public static final int LessThan = 1;

	/**
	 * Identify a trigger ID
	 */
	public static final int GreaterThan_NoMovement = 2;

	/**
	 * Identify a trigger ID
	 */
	public static final int LessThan_NoMovement = 3;
	/**
	 * Identify a trigger ID
	 */
	public static final int GreaterThan_WithMovement = 4;
	/**
	 * Identify a trigger ID
	 */
	public static final int LessThan_WithMovement = 5;
	/**
	 * Identify a trigger ID
	 */
	public static final int GreaterThan_WithMovementReset = 6;
	/**
	 * Identify a trigger ID
	 */
	public static final int LessThan_WithMovementReset = 7;

	/**
	 * set the length of activity
	 *
	 * @param duration
	 */
	public void setDuration(long duration) {
		Duration = duration;
	}

	/**
	 * get the duration of activity
	 *
	 * @return long
	 */
	public long getDuration() {
		return Duration;
	}

	/**
	 * reset the start time
	 */
	public void reset() {
		StartTime = System.currentTimeMillis();
	}

	/**
	 * construct a TouchTimer
	 *
	 * @param duration
	 */
	public TouchTimer(long duration) {
		this.StartTime = System.currentTimeMillis();
		this.Duration = duration;
	}

	/**
	 * Construct a TouchTimer
	 *
	 * @param event
	 * @param duration
	 */
	public TouchTimer(MotionEvent event, long duration) {
		this.StartTime = System.currentTimeMillis();
		this.Duration = duration;
		TouchedX = event.getX();
		TouchedY = event.getY();
	}

	/**
	 * Construct a TouchTimer
	 *
	 * @param event
	 * @param duration
	 * @param fingerwabble
	 */
	public TouchTimer(MotionEvent event, long duration, float fingerwabble) {
		this.StartTime = System.currentTimeMillis();
		this.Duration = duration;
		TouchedX = event.getX();
		TouchedY = event.getY();
		wobble = fingerwabble;
	}

	/**
	 * set a start time
	 *
	 * @param start
	 */
	public void setStart(int start) {
		this.StartTime = start;
	}

	/**
	 * get the time left
	 *
	 * @return Long
	 */
	public Long getCurrentDelta() {
		return System.currentTimeMillis() - StartTime;
	}

	/**
	 * test if movement in touch is acceptable
	 *
	 * @param event
	 * @return boolean
	 */
	public boolean Movement(MotionEvent event) {
		if (wobble == 0) {
			return (TouchedX != event.getX() || TouchedY != event.getY()) ? true
				: false;
		}
		float dx = event.getX() - TouchedX;
		float dy = event.getY() - TouchedY;
		float dist = dx * dx + dy * dy;
		return (dist < wobble * wobble) ? false : true;
	}

	/**
	 * check the status of timer
	 *
	 * @param event
	 * @param check
	 * @return boolean
	 */
	public boolean CheckTimer(MotionEvent event, int check) {
		switch (check) {
			case GreaterThan:
				if (getCurrentDelta() > Duration) {
					return true;
				}
				break;
			case LessThan:
				if (getCurrentDelta() < Duration) {
					return true;
				}
				break;
			case GreaterThan_NoMovement:
				if (getCurrentDelta() > Duration && !Movement(event)) {
					return true;
				}
				break;
			case LessThan_NoMovement:
				if (getCurrentDelta() < Duration && !Movement(event)) {
					return true;
				}
				break;
			case GreaterThan_WithMovement:
				if (getCurrentDelta() > Duration && Movement(event)) {
					return true;
				}
				break;
			case GreaterThan_WithMovementReset:
				if (getCurrentDelta() > Duration && !Movement(event)) {
					return true;
				} else if (Movement(event)) {
					TouchedX = event.getX();
					TouchedY = event.getY();
					StartTime = System.currentTimeMillis();
					break;
				}
		}
		return false;
	}

	/**
	 * check the status of timer
	 *
	 * @param check
	 * @return boolean
	 */
	public boolean CheckTimer(int check) {
		switch (check) {
			case GreaterThan:
				if (getCurrentDelta() > Duration) {
					return true;
				}
				break;
			case LessThan:
				if (getCurrentDelta() < Duration) {
					return true;
				}
				break;
		}
		return false;
	}

}
