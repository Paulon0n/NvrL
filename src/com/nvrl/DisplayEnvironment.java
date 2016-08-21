package com.nvrl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

/**
 * The environment that controls visualization
 *
 * @author Michael
 * @version 1.0
 */
public class DisplayEnvironment {
	private static String ProjectFilename = "";
	private static float zoom = 1.2f;
	private static int fontSize = 20;
	private static Point ViewOffset = new Point();
	private static Grid g;
	private static Point ScreenCenter;
	private static final float neuronRad = 50;
	private static boolean drawClusters = true;
	private static float selectedPulse;
	private static float lastSelectedPulse;
	private static boolean pulseUp;
	private static float pulseSpeed;
	private static float pulseSize;
	public static TouchTimer ut;
	private static final boolean disableLayerViewBelow = false,
		disableLayerViewAbove = false;
	private static int activeLayer = 0;
	public static final ArrayList<CommonNeuronExtension> drawableNeurons = new ArrayList<CommonNeuronExtension>();
	
	/**
	 * @return String
	 */
	public static String toString_() {
		return "[E]" + NvrL.getBUILD() + "&"
			+ FileIO.SaveFilename + "&" + zoom + "&" + ViewOffset.X + "&"
			+ ViewOffset.Y + "&" + ViewOffset.Z + "&" + EditorUi.isRunnet()
			+ "&" + EditorUi.getSpeed() + "&" + "\n";
	}

	/**
	 * @param in
	 */
	public static void load(String in) {
		int i = 1;
		double build = Float.parseFloat(FileIO.getPart(in, i++));
		if (NvrL.getBUILD()>(build)) {
			// verify file structure and make changes where required
			NvrL.setUpdatedBuild(true);
			NvrL.setLoadBuild(build);
		}
		FileIO.SaveFilename = FileIO.getPart(in, i++);
		NvrL.getContext().setTitle(FileIO.SaveFilename);
		zoom = Float.parseFloat(FileIO.getPart(in, i++));
		ViewOffset.X = Float.parseFloat(FileIO.getPart(in, i++));
		ViewOffset.Y = Float.parseFloat(FileIO.getPart(in, i++));
		ViewOffset.Z = Float.parseFloat(FileIO.getPart(in, i++));
		EditorUi.setRunnet(Boolean.parseBoolean(FileIO.getPart(in, i++)));
		EditorUi.setSpeed(Integer.parseInt(FileIO.getPart(in, i++)));
	}

	/**
	 * Enable / disable the drawing of Clusters
	 *
	 * @param drawClusters
	 */
	public static void setDrawClusters(boolean drawClusters) {
		DisplayEnvironment.drawClusters = drawClusters;
	}

	/**
	 * Is the drawing of Clusters enabled
	 *
	 * @return boolean
	 */
	public static boolean isDrawClusters() {
		return drawClusters;
	}

	/**
	 * @param activeLayer
	 */
	public static void setActiveLayer(int activeLayer) {
		DisplayEnvironment.activeLayer = activeLayer;
	}

	/**
	 * @return int - the active layer
	 */
	public static int getActiveLayer() {
		return activeLayer;
	}

	/**
	 * Set the center point of the screen
	 *
	 * @param screenCenter
	 */
	public static void setScreenCenter(Point screenCenter) {
		ScreenCenter = screenCenter;
	}

	/**
	 * Get the center point of the screen
	 *
	 * @return Point - the screen center
	 */
	public static Point getScreenCenter() {
		return ScreenCenter;
	}

	/**
	 * Set the projects filename
	 *
	 * @param projectFilename
	 */
	public static void setProjectFilename(String projectFilename) {
		ProjectFilename = projectFilename;
	}

	/**
	 * Get the projects filename
	 *
	 * @return ProjectFilename String
	 */
	public static String getProjectFilename() {
		return ProjectFilename;
	}

	/**
	 * Reset screen PROPERTIES
	 */
	public static void screenChanged() {
		ScreenCenter = new Point(NvrLView.screenWidth / 2,
			NvrLView.screenHeight / 2);
		g = new Grid();
	}

	/**
	 * Set default value
	 */
	public void resetEdit() {
		screenChanged();
		pulseSpeed = 1.5f;
		pulseSize = 22;
		selectedPulse = 0;
		lastSelectedPulse = pulseSize;
		activeLayer = 0;

		pulseUp = true;
		Neuron.setSelected(null);
		Neuron.setPreviousSelected(null);
	}
	
	public static boolean ERRORS=false;

	/**
	 * Draw the environment
	 *
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint) {
		paint.setStrokeWidth(1);
		if(!ERRORS)
			canvas.drawColor(Color.BLACK);
		else
			canvas.drawARGB(255,100,0,0);
		Point vp = Grid.snap(ViewOffset);

		g.drawGrid(canvas, paint);

//		if (ut == null) {
//			ut = new TouchTimer(EditorUi.getSpeed());
//		}
//		if (EditorUi.isRunnet() && ut.CheckTimer(TouchTimer.GreaterThan)) {
//
//			NeuralNet.getSelected().StepUpdate();
//			ut.setDuration(EditorUi.getSpeed());
//			ut.reset();
//		}
		if (NeuralNet.getSelected() != null) {
			if (drawClusters) {
				drawClusters(canvas, paint);
			}
			drawNeurons(canvas, paint);
			drawConnections(canvas, paint);
		}
		if (NvrLView.isDrawselect()) {
			paint.setStrokeWidth(5);
			paint.setStyle(Paint.Style.STROKE);
			paint.setARGB(255, 255, 0, 0);
			Point ts = NvrLView.getSelectStart(), te = NvrLView
				.getSelectEnd();

			if (ts.Z <= activeLayer && te.Z >= activeLayer) {
				paint.setStyle(Paint.Style.FILL);
				if (activeLayer == ts.Z || activeLayer == te.Z) {
					paint.setAlpha(75);
				} else {
					paint.setAlpha(50);
				}

				Grid.GridToScreen(NvrLView.getSelectStart()).drawRect(
					canvas,
					Grid.GridToScreen(NvrLView.getSelectEnd()), paint);
				paint.setAlpha(255);

				paint.setStyle(Paint.Style.STROKE);
				Grid.GridToScreen(NvrLView.getSelectStart()).drawRect(
					canvas,
					Grid.GridToScreen(NvrLView.getSelectEnd()), paint);
			}
			paint.setStyle(Paint.Style.FILL);
			paint.setARGB(255, 0, 255, 0);
			Grid.GridToScreen(te).drawCross(canvas, (int) (40 / zoom), paint);
		}
		if (NvrLView.isSelecting()) {
			paint.setARGB(255, 0, 255, 0);
			Grid.GridToScreen(NvrLView.getSelectStart()).drawCross(canvas,
				(int) (40 / zoom), paint);
		}
		paint.setStrokeWidth(2);
		// grid center
		paint.setARGB(100, 255, 0, 0);
		ScreenCenter.sub(vp).drawRect(canvas, (int) (30 / zoom), paint);

		// grid point touched
		paint.setARGB(255, 255, 255, 255);
		Grid.snap(NvrLView.getTouch().sub(ScreenCenter)).add(ScreenCenter)
			.drawX(canvas, (int) (40 / zoom), paint);

		// draw status'es
		paint.setARGB(125, 0, 0, 0);
		canvas.drawRect(0, 0, 200, fontSize * 8, paint);
		paint.setARGB(255, 255, 255, 255);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(-2, -2, 200, fontSize * 8, paint);
		paint.setStyle(Paint.Style.FILL);
		int i = 1;
		// canvas.drawText("" ,fontSize,fontSize*i++,paint);
		vp = Grid.ScreenToGrid(ViewOffset);
		canvas.drawText("view x: " + vp.X, fontSize, fontSize * i++, paint);
		canvas.drawText("view y: " + vp.Y, fontSize, fontSize * i++, paint);
		canvas.drawText("touch x: " + (Grid.ScreenToGridX(getTouchX()) + vp.X),
			fontSize, fontSize * i++, paint);
		canvas.drawText("touch y: " + (Grid.ScreenToGridY(getTouchY()) + vp.Y),
			fontSize, fontSize * i++, paint);
		canvas.drawText("layer: " + activeLayer, fontSize, fontSize * i++,
			paint);

		if (Neuron.getPreviousSelected() != null) {
			canvas.drawText("Pre: " + Neuron.getPreviousSelected().getName(),
				fontSize, fontSize * i++, paint);
		} else {
			canvas.drawText("Pre: None", fontSize, fontSize * i++, paint);
		}
		if (Neuron.getSelected() != null) {
			canvas.drawText("Cur: " + Neuron.getSelected().getID(), fontSize,
				fontSize * i++, paint);
		} else {
			canvas.drawText("Cur: None", fontSize, fontSize * i++, paint);
		}

	}

	/**
	 * Draw Neurons
	 *
	 * @param c
	 * @param p
	 */
	public void drawNeurons(Canvas c, Paint p) {

		float r = neuronRad / zoom;
		if (pulseUp) {
			selectedPulse += pulseSpeed;
			lastSelectedPulse -= pulseSpeed;
		} else {
			selectedPulse -= pulseSpeed;
			lastSelectedPulse += pulseSpeed;
		}
		if (selectedPulse > pulseSize) {
			pulseUp = false;
		}
		if (selectedPulse < 0) {
			pulseUp = true;
		}

		
		if (Neuron.getSelected() == Neuron.getPreviousSelected()
			&& Neuron.getSelected() != null) {
			Point tPos = Neuron.getSelected().getAbsolutePos();
			if (tPos.Z == activeLayer) {

				// nx = g.GridToScreenX(tPos.X);
				// ny = g.GridToScreenY(tPos.Y);
				Point np = Grid.GridToScreen(tPos);
				p.setStyle(Paint.Style.FILL);
				p.setARGB(127, 255, 0, 0);
				np.drawRect(c, r + selectedPulse / zoom, p);
				p.setARGB(127, 0, 0, 255);
				np.drawRect(c, r + lastSelectedPulse / zoom, p);
				p.setARGB(255, 255, 255, 255);
				p.setStyle(Paint.Style.STROKE);
				np.drawRect(c, r + lastSelectedPulse / zoom, p);
				np.drawRect(c, r + selectedPulse / zoom, p);
			}
		} else {
			if (Neuron.getPreviousSelected() != null) {
				Point tPos = Neuron.getPreviousSelected().getAbsolutePos();
				if (tPos.Z == activeLayer) {
					// nx = g.GridToScreenX(tPos.X);
					// ny = g.GridToScreenY(tPos.Y);
					Point np = Grid.GridToScreen(tPos);

					p.setStyle(Paint.Style.FILL);
					p.setARGB(127, 0, 0, 255);
					np.drawRect(c, r + lastSelectedPulse / zoom, p);
					p.setARGB(255, 255, 255, 255);
					p.setStyle(Paint.Style.STROKE);
					np.drawRect(c, r + lastSelectedPulse / zoom, p);
				}
			}
			if (Neuron.getSelected() != null) {
				Point tPos = Neuron.getSelected().getAbsolutePos();
				if (tPos.Z == activeLayer) {
					// nx = g.GridToScreenX(tPos.X);
					// ny = g.GridToScreenY(tPos.Y);
					Point np = Grid.GridToScreen(tPos);

					p.setStyle(Paint.Style.FILL);
					p.setARGB(127, 255, 0, 0);
					np.drawRect(c, r + selectedPulse / zoom, p);
					p.setARGB(255, 255, 255, 255);
					p.setStyle(Paint.Style.STROKE);
					np.drawRect(c, r + selectedPulse / zoom, p);
				}
			}
		}
		List<CommonNeuronExtension> copy ;
		if(EditorUi.isRunnet()){
			copy =new ArrayList<CommonNeuronExtension>(DisplayEnvironment.drawableNeurons);
			
		}else{
			copy = new ArrayList<CommonNeuronExtension>(NeuralNet.getSelected().getNeurons());
		}
		
		int a;// = 255;
		// layer below
		if (!disableLayerViewBelow) {
			for (CommonNeuronExtension n : copy) {
				if (n.getCluster() == null || n.getCluster().drawNeurns) {
					Point tPos = n.getAbsolutePos();
					if (tPos.Z == activeLayer - 1) {
						a = 64;
						// nx = g.GridToScreenX(tPos.X);
						// ny = g.GridToScreenY(tPos.Y);
						Point np = Grid.GridToScreen(tPos);

						p.setStyle(Paint.Style.FILL);
						if (n.getEnergy() > n.getThreshold()) {
							p.setARGB(a, 255, a, a);
						} else {
							p.setARGB(a, a, a, 255);
						}

						np.drawRect(c, (int) (r + 15 / zoom), p);

					} else if (tPos.Z < activeLayer - 1) {
						a = 30;
						// nx = g.GridToScreenX(tPos.X);
						// ny = g.GridToScreenY(tPos.Y);
						Point np = Grid.GridToScreen(tPos);

						p.setStyle(Paint.Style.FILL);
						if (n.getEnergy() > n.getThreshold()) {
							p.setARGB(a, 255, a, a);
						} else {
							p.setARGB(a, a, a, 255);
						}

						np.drawRect(c, (int) (r + 15 / zoom), p);

					}
				}
			}
		}
		// layer
		for (CommonNeuronExtension n : copy) {
			if (n.getCluster() == null || n.getCluster().drawNeurns) {
				Point tPos = n.getAbsolutePos();
				a = 255;
				if (tPos.Z == activeLayer) {
					// nx = g.GridToScreenX(tPos.X);
					// ny = g.GridToScreenY(tPos.Y);
					Point np = Grid.GridToScreen(tPos);

					p.setStyle(Paint.Style.FILL);
					if (n.getEnergy() > n.getThreshold()) {
						p.setARGB(a, 255, 0, 0);
					} else {
						p.setARGB(a, 0, 0, 255);
					}

					np.drawRect(c, r, p);
					p.setARGB(255, 255, 255, 255);
					
					c.drawText(n.getName(), np.X, np.Y, p);
					if(n instanceof Text_Input){
						np.drawCross(c,r,p);
					}else if(n instanceof Text_Output){
						p.setARGB(255,255,0,0);
						np.drawCross(c,r,p);
					}
				}
			}
		}
		// layer above
		if (!disableLayerViewAbove) {
			for (CommonNeuronExtension n : copy) {
				if (n.getCluster() == null || n.getCluster().drawNeurns) {
					Point tPos = n.getAbsolutePos();
					if (tPos.Z == activeLayer + 1) {
						a = 170;
						// nx = g.GridToScreenX(tPos.X);
						// ny = g.GridToScreenY(tPos.Y);
						Point np = Grid.GridToScreen(tPos);

						p.setStyle(Paint.Style.FILL);
						if (n.getEnergy() > n.getThreshold()) {
							p.setARGB(a, 255, a, a);
						} else {
							p.setARGB(a, a, a, 255);
						}

						np.drawRect(c, (int) (r - 15 / zoom), p);
					} else if (tPos.Z > activeLayer + 1) {
						a = 100;
						// nx = g.GridToScreenX(tPos.X);
						// ny = g.GridToScreenY(tPos.Y);
						Point np = Grid.GridToScreen(tPos);

						p.setStyle(Paint.Style.FILL);
						if (n.getEnergy() > n.getThreshold()) {
							p.setARGB(a, 255, a, a);
						} else {
							p.setARGB(a, a, a, 255);
						}

						np.drawRect(c, (int) (r - 15 / zoom), p);
					}
				}
			}
		}
	}

	// needs work done to disable layer views above and below.
	/**
	 * Draw Connections
	 *
	 * @param c
	 * @param p
	 */
	public void drawConnections(Canvas c, Paint p) {
		p.setARGB(255, 0, 255, 0);
		p.setStyle(Paint.Style.STROKE);
		List<CommonNeuronExtension> copy = new ArrayList<CommonNeuronExtension>(NeuralNet.getSelected().getNeurons());
		
		for (CommonNeuronExtension n : copy) {
			Point outputPos = n.getAbsolutePos();
			for (int i = 0; i < n.getInputs().size(); i++) {
				Connection co = n.getInputs().get(i);
				Point inputPos = co.getInput().getAbsolutePos();
				if ((inputPos.Z >= activeLayer - 1 && inputPos.Z <= activeLayer + 1)
					|| outputPos.Z >= activeLayer - 1
					&& outputPos.Z <= activeLayer + 1) {

					switch (co.getType()) {
						case 1:
							p.setARGB(255, 255, 255, 0);
							break;
						case 2:
							p.setARGB(255, 0, 255, 255);
							break;
						case 3:
							p.setARGB(255, 255, 0, 255);
							break;
						case 4:
							p.setARGB(255, 0, 0, 255);
							break;
						case 5:
							p.setARGB(255, 255, 0, 0);
							break;
						case 6:
							p.setARGB(255, 255, 255, 255);
							break;
						case 9:
							p.setARGB(255,200,50,50);
							break;
						default:
							p.setARGB(255, 0, 255, 0);
					}

					if (outputPos.Z != activeLayer ^ inputPos.Z != activeLayer) {
						p.setAlpha(128);
					}
					if (inputPos.Z == activeLayer - 1
						&& outputPos.Z == activeLayer - 1
						&& !disableLayerViewBelow) {
						p.setAlpha(64);
					}
					if (inputPos.Z == activeLayer + 1
						&& outputPos.Z == activeLayer + 1
						&& !disableLayerViewAbove) {
						p.setAlpha(64);
					}
					p.setStrokeWidth(5);
					float az = 25 / zoom;
					float ang = 20;
					if (co == Connection.SELECTION.getSelected()) {
						p.setAlpha((int) (lastSelectedPulse / pulseSize * 155 + 100));
						p.setStrokeWidth((5 + selectedPulse / pulseSize * 10) / zoom);
						az = (25 + selectedPulse / pulseSize * 25) / zoom;
					}
					if (co.getInput() != co.getOutput()) {
						// convert grid space to screen space
						Point pI = Grid.GridToScreen(inputPos);
						Point pO = Grid.GridToScreen(outputPos);
						
	// DRAW WAY POINTS		// do way points here and draw them
						Point prev = null;
						if (co.getWayPoints() != null
							&& !co.getWayPoints().isEmpty()) {
							WayPoint enter = co.getWayPoints().get(0);
							for (WayPoint wp : co.getWayPoints()) {

								Point gts = Grid.GridToScreen(wp.Pos);//.add(inputPos));
								p.setStyle(Paint.Style.FILL);
								gts.drawSquare(c, 10, p);
								if (wp == WayPoint.getSelected()) {
									p.setStyle(Paint.Style.STROKE);
									gts.drawSquare(c, 20, p);
								}
								if (wp == enter) {
									pI.drawLine(c, gts, p);
								} else {
									if (prev != null) {
										prev.drawLine(c, gts, p);
									}
								}
								prev = gts;
							}
						}
						p.setStyle(Paint.Style.STROKE);

						pI = (prev != null ? prev : pI);
						// get the angle and distance for the arrow.
						float a = pI.getAngle(pO);
						float d = pI.getDistanceTo(pO);
						Point tp1 = pI.getVector(a, (d - neuronRad / zoom));
						// draw arrow rod.
						pI.drawLine(c, tp1, p);
						// draw arrow point.
						tp1.getVector((a + 180) - ang, az).drawLine(c, tp1, p);
						tp1.getVector((a + 180) + ang, az).drawLine(c, tp1, p);
					} else {
						Grid.GridToScreen(inputPos).drawRect(c,
							(neuronRad + 2) / zoom, p);
					}
				}
			}
		}
		p.setStyle(Paint.Style.FILL);
	}

	/**
	 * Draw Clusters
	 *
	 * @param c
	 * @param paint
	 */
	public void drawClusters(Canvas c, Paint paint) {
		
		for (Cluster cl : NeuralNet.getSelected().clusters) {
			if (cl.Pos.Z == activeLayer) {
				paint.setARGB(255, 255, 255, 255);
				if (cl.oneStep) {
					paint.setARGB(255, 0, 255, 0);
				}
				cl.getPaintFill().setStyle(Paint.Style.FILL);
				c.drawText("( " + cl.oneStepCount + " ): " + cl.name,
					Grid.GridToScreenX(cl.Pos.X),
					Grid.GridToScreenY(cl.Pos.Y) - 25 / zoom, paint);
				Grid.GridToScreen(cl.Pos).drawRect(c,
					Grid.GridToScreen(cl.Pos.add(cl.Size)),
					cl.getPaintFill());
				cl.getPaintFill().setStyle(Paint.Style.STROKE);
				Grid.GridToScreen(cl.Pos).drawRect(c,
					Grid.GridToScreen(cl.Pos.add(cl.Size)), cl.paintBorder);
			}
		}
	}

	

	/**
	 * Get the point a user touched
	 *
	 * @return Point
	 */
	public static Point getTouch() {
		return NvrLView.getTouch().sub(ScreenCenter);
	}

	/**
	 * Get the X-axis the user touched
	 *
	 * @return float
	 */
	public static float getTouchX() {
		return NvrLView.getTouch().X - ScreenCenter.X;
	}

	/**
	 * Get the Y-axis the user touched
	 *
	 * @return float
	 */
	public static float getTouchY() {
		return NvrLView.getTouch().Y - ScreenCenter.Y;
	}

	/**
	 * Get the angle between 2 x and y points
	 *
	 * @param x
	 * @param y
	 * @param tx
	 * @param ty
	 * @return float
	 */
	public static float getAngle(float x, float y, float tx, float ty) {
		return (float) (Math.atan2(tx - x, ty - y));
	}

	/**
	 * Set font size on screen
	 *
	 * @param fontSize
	 */
	public static void setFontSize(int fontSize) {
		DisplayEnvironment.fontSize = fontSize;
	}

	/**
	 * Get font size on screen
	 *
	 * @return int
	 */
	public static int getFontSize() {
		return fontSize;
	}

	/**
	 * set the current zoom
	 *
	 * @param zoom
	 */
	public static void setZoom(float zoom) {
		DisplayEnvironment.zoom = zoom;
	}

	/**
	 * Get the current zoom
	 *
	 * @return float
	 */
	public static float getZoom() {
		return zoom;
	}

	/**
	 * Set the view offset to point p
	 *
	 * @param p
	 */
	public static void setViewOffset(Point p) {
		ViewOffset = p;
	}

	public static void setViewOffset(float x, float y, float z) {
		ViewOffset.setXYZ(x,y,z);
	}
	public static void setViewOffset(int x, int y, int z) {
		ViewOffset.setXYZ(x*Grid.gridSpaceX(),y*Grid.gridSpaceX(),z);
	}
	/**
	 * Get the view offset
	 *
	 * @return Point
	 */
	public static Point getViewOffset() {
		return ViewOffset;
	}

}
