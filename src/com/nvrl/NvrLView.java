package com.nvrl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import static com.nvrl.DisplayEnvironment.getActiveLayer;

//--_00-----8758866ygy7yhy7yu890oui0-909id0yzs7ydtsududux6s7dudifududuxusududud8dududid7duxicidiciducududixkcic8cickcic7cuckcjc8c8v8cic8 8 ici u hx66vjclof
/**
 *
 * @author Michael
 */
public class NvrLView extends View {

	private static boolean touched = false, selecting = false,
		drawselect = false;
	private static Point selectStart, selectEnd, touch = new Point(), Ltouch,
		GridTouched = new Point();

	private static TouchTimer lt, dt, ut;
	private static boolean longtouch;
	private static boolean doubleTap;
	private static boolean scale=false;

	/**
	 *
	 */
	public static int screenWidth;

	/**
	 *
	 */
	public static int screenHeight;
	private static Paint paint;
	private static boolean active;
	private Thread myThread;
	private static DisplayEnvironment EE = NvrL.getDE();

	private final ScaleGestureDetector mScaleDetector;
	

	/**
	 *
	 */
	public static void screenChange() {

	}

	/**
	 *
	 * @param context
	 */
	public NvrLView(Context context) {
		super(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(-1);
		paint.setStyle(android.graphics.Paint.Style.FILL);
		paint.setStrokeWidth(1);
		paint.setTextSize(DisplayEnvironment.getFontSize());
		paint.setAlpha(128);

		active = true;

		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}

	/**
	 *
	 * @return Point
	 */
	public static Point getGridTouched() {
		return GridTouched;
	}

	/**
	 *
	 * @param touched
	 */
	public static void setTouched(boolean touched) {
		NvrLView.touched = touched;
	}

	/**
	 *
	 * @return boolean
	 */
	public static boolean isTouched() {
		return touched;
	}

	@Override
	public void onDraw(Canvas canvas) {
		EE.draw(canvas, paint);
	}

	/**
	 *
	 */
	public void update() {
		// NvrL.out = "";
		// NvrL.out = ( NvrL.os.toString());
		// NvrL.getContext().text.setText(NvrL.out);
		//
		// EE.Update();
		// update the UI using the handler and the runnable
		// EditNeuralNetActivity.getMyHandler().post(EditNeuralNetActivity.getUpdateRunnable());
		if (ut == null) {
			ut = new TouchTimer(EditorUi.getSpeed());
		}
		if ( FileIO.isProjectLoaded() && EditorUi.isRunnet() && ut.CheckTimer(TouchTimer.GreaterThan)) {
			CommonNeuronExtension.clearErrors();
			NeuralNet.getSelected().StepUpdate();
			ut.setDuration(EditorUi.getSpeed());
			ut.reset();
		}else{
			NeuralNet.getSelected().finddrawables();
		}
		postInvalidate();
	}

	/**
	 *
	 */
	public void startMyLogicThread() {
		myThread = new Thread() {
			@Override
			public void run() {

				while (active) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
					update();

				}
			}
		};
		myThread.start();
	}

	/**
	 *
	 * @param ev
	 * @return boolean
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mScaleDetector.onTouchEvent(ev);
		Point tv = new Point(DisplayEnvironment.getViewOffset());
		int i = ev.getAction();
		switch (i) {

			case MotionEvent.ACTION_DOWN:
				touched = true;
				touch = new Point(ev.getX(), ev.getY(), getActiveLayer());
				GridTouched = touch.toGrid();
				Ltouch = new Point(touch);

				if (dt != null) {
					doubleTap = dt.CheckTimer(ev, TouchTimer.LessThan_NoMovement);
					if (!doubleTap) {
						dt = null;
					}
				}
				if (dt == null && !doubleTap) {
					dt = new TouchTimer(ev, 300, 100);
				}
				if (doubleTap) {
					dt = null;
					doubleTap = false;
					if (drawselect) {
						drawselect = false;
						EditorUi.UIaction(null, EditorUi.Selection_Deselect);
					} else if (!selecting) {
						EditorUi.UIaction(null, 16);
						selecting = true;
						selectStart = touch.toGrid();
						drawselect = false;
					} else {
						selecting = false;
						selectEnd = touch.toGrid();
						selectStart.sort(selectEnd);
						drawselect = true;
						SelectionManager.getSelectedFromSelection(selectStart,
							selectEnd);
					}
				}
				lt = new TouchTimer(ev, 750, 75);
				longtouch = lt.CheckTimer(TouchTimer.GreaterThan_WithMovementReset);
				postInvalidate();
				break;

			case MotionEvent.ACTION_MOVE:
				touch = new Point(ev.getX(), ev.getY(),
					DisplayEnvironment.getActiveLayer());
				DisplayEnvironment.setViewOffset(tv.add(Ltouch.sub(touch)).setZ(
					DisplayEnvironment.getActiveLayer()));
				Ltouch = new Point(touch);

				lt.CheckTimer(ev, TouchTimer.GreaterThan_WithMovementReset);
				postInvalidate();
				break;

			case MotionEvent.ACTION_UP:
				touch = new Point(ev.getX(), ev.getY(),
					DisplayEnvironment.getActiveLayer());

				touched = false;
				setActive(true);
				longtouch = lt.CheckTimer(ev,
					TouchTimer.GreaterThan_WithMovementReset);
				lt = null;
				Selector();
				if (longtouch && !scale) {
					Cluster.SELECTION.selector(NeuralNet.getSelected().clusters);
					if (drawselect) {
						SelectionManager.getSelectedFromSelection(selectStart,
							selectEnd);
					}
					NvrL.getUi().createEditNeuralNetMenu();
					longtouch = false;
				} else {
					Neuron.SELECTION.setSelected(Neuron.SELECTION
						.selector(NeuralNet.getSelected().neurons));
					Neuron.setSelected(Neuron.SELECTION.getSelected());
					scale=false;
				}
			
				break;
			default:
				break;

		}
		NvrL.getContext().invalidateOptionsMenu();

		return true;
	}

	private static void Selector() {
		Cluster.SELECTION.touchInSelector(NeuralNet.getSelected().clusters);
		Neuron.SELECTION.Prev_selector(NeuralNet.getSelected().neurons);
		WayPoint.SELECTION.Prev_selector(WayPoint.getAllWayPoints());
		if (Neuron.SELECTION.getSelected() != null
			&& Neuron.SELECTION.getPreviouslySelected() == null) {
			Connection.SELECTION.setSelected(null);
		}
		if (Neuron.SELECTION.getSelected() != null
			&& Neuron.SELECTION.getPreviouslySelected() != null) {
			Connection.SELECTION.setSelected(null);
			for (Connection c : Neuron.SELECTION.getSelected().getInputs()) {
				if (c.getInput() == Neuron.getPreviousSelected()) {
					Connection.SELECTION.setSelected(c);
					break;
				}
			}
		}
	}

	private class ScaleListener extends
		ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float z = DisplayEnvironment.getZoom();
			z /= detector.getScaleFactor();
			scale=true;
			// Don't let the object get too small or too large.
			z = Math.max(0.25f, Math.min(z, 3.0f));
			DisplayEnvironment.setZoom(z);
			return true;
		}
	}

	/**
	 *
	 * @param flag
	 */
	public static void setActive(boolean flag) {
		active = true;
	}

	/**
	 *
	 * @return boolean
	 */
	public static boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param oEE
	 */
	public static void setOEE(DisplayEnvironment oEE) {
		EE = oEE;
	}

	/**
	 *
	 * @return DrawEnviroment
	 */
	public static DisplayEnvironment getOEE() {
		return EE;
	}

	/**
	 *
	 * @param p
	 */
	public static void setTouch(Point p) {
		touch = p;
	}

	/**
	 *
	 * @return Point
	 */
	public static Point getTouch() {
		return touch;
	}

	/**
	 *
	 * @param longtouch
	 */
	public static void setLongtouch(boolean longtouch) {
		NvrLView.longtouch = longtouch;
	}

	/**
	 *
	 * @return boolean
	 */
	public static boolean isLongtouch() {
		return longtouch;
	}

	/**
	 *
	 * @return Point
	 */
	public static Point getSelectStart() {
		return selectStart;
	}
	public static void setSelectStart(Point selectStart) {
		NvrLView.selectStart = selectStart;
	}

	public static void setSelectEnd(Point selectEnd) {
		NvrLView.selectEnd = selectEnd;
	}
	/**
	 *
	 * @return Point
	 */
	public static Point getSelectEnd() {
		return selectEnd;
	}

	/**
	 *
	 * @param selecting
	 */
	public static void setSelecting(boolean selecting) {
		NvrLView.selecting = selecting;
	}

	/**
	 *
	 * @return boolean
	 */
	public static boolean isSelecting() {
		return selecting;
	}

	/**
	 *
	 * @param drawselect
	 */
	public static void setDrawselect(boolean drawselect) {
		NvrLView.drawselect = drawselect;
	}

	/**
	 *
	 * @return boolean
	 */
	public static boolean isDrawselect() {
		return drawselect;
	}

}
