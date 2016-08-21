package com.nvrl;

import android.graphics.Paint;
import com.nvrl.ClusterCommands.ClusterOnestep;
import com.nvrl.ClusterCommands.ClusterSetOnestepCount;
import com.nvrl.CommonCommands.SetName;
import java.util.ArrayList;

/**
 * Used as a Container for neurons with many options
 *
 * @author Michael Camacho
 * @version 1.0
 */
public class Cluster extends CommonExtensions {

	/**
	 * Used to identify all Clusters that are in SELECTION
	 */
	public static final SelectionManager<Cluster> SELECTION = new SelectionManager<Cluster>();

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int NAME = 0;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int ONE_STEP = 1;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int ONE_STEP_COUNT = 2;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int SUB_PARENT = 3;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int SUB_CLUSTER = 4;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BACKGROUND_ALPHA = 5;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BACKGROUND_RED = 6;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BACKGROUND_GREEN = 7;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BACKGROUND_BLUE = 8;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BORDER_ALPHA = 9;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BORDER_RED = 10;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BORDER_GREEN = 11;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BORDER_BLUE = 12;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BOUNDRY_MIN_X = 13;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BOUNDRY_MIN_Y = 14;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BOUNDRY_SIZE_X = 15;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int BOUNDRY_SIZE_Y = 16;

	/**
	 * Used to identify a property of a Cluster
	 */
	public final static int DRAW_NEURONS = 17;

	/**
	 * Used to define a Clusters property Name
	 */
	public final static String[] PROPERTIES = {"Name", "One Step",
		"One Step Count", "sub Parent", "sub Cluster", "Alpha", "Red",
		"Green", "Blue", "Border Alpha", "Border Red", "Border Green",
		"Border Blue", "TopLeft X", "TopLeft Y", "BottomRight X",
		"BottomRight Y", "Draw Neurons"};

	/**
	 * Used to define a TIP in editing Cluster PROPERTIES
	 */
	public final static String[] TIP = {"", "", "", "", "", "", "", "", "",
		"", "", "", "", "   [ Grid Location X ]", "   [ Grid Location Y ]",
		"   [ Grid Location X ]", "   [ Grid Location Y ]", ""};

	/**
	 * Used to identify the data type that a dialog must use to edit
	 * properly.
	 */
	public final static int[] DATA_TYPE = {EditorUi.Str, EditorUi.NotEditable,
		EditorUi.Num, EditorUi.NotEditable, EditorUi.NotEditable,
		EditorUi.Num, EditorUi.Num, EditorUi.Num, EditorUi.Num,
		EditorUi.Num, EditorUi.Num, EditorUi.Num, EditorUi.Num,
		EditorUi.Neg, EditorUi.Neg, EditorUi.Neg, EditorUi.Neg,
		EditorUi.NotEditable,};

	

	public static ArrayList<CommonNeuronExtension> getAllNeuronsCloned() {
		return allNeuronsCloned;
	}

	public static ArrayList<CommonNeuronExtension> getAllNeuronsSource() {
		return allNeuronsSource;
	}

	/**
	 * Used to get the Clusters in the SELECTION
	 *
	 * @return the selection of Clusters
	 */
	public static ArrayList<Cluster> getSelection() {
		return SELECTION.getSelection();
	}

	/**
	 * Used to reference a selected Cluster
	 *
	 * @param selected
	 */
	public static void setSelected(Cluster selected) {
		Cluster.SELECTION.setSelected(selected);
	}

	/**
	 * Used to retrieve the selected Cluster
	 *
	 * @return the selected Cluster
	 */
	public static Cluster getSelected() {
		return Cluster.SELECTION.getSelected();
	}

	/* These are the core variables for a Cluster */
	/**
	 * Used to reference the parent of the Cluster
	 */
	public Cluster parent;

	/**
	 * Used to reference the Project array position for the Cluster
	 */
	public int Id;// the clusters array position within NeuralNet
	public int clusterID;
	/**
	 * Used to reference the child of the Cluster
	 */
	public Cluster child;

	/**
	 * Used to reference the parent in a nested Cluster
	 */
	public Cluster SubParent = null;

	/**
	 * Used to reference the children in a nested Cluster
	 */
	public ArrayList<Cluster> SubCluster;
	
	public ArrayList<Connection> subClusterInterconnection;

	/**
	 * Used to activate one step processing for the Cluster
	 */
	public boolean oneStep;

	/**
	 * Used to identify the number of steps the Cluster will make
	 * before passing control
	 */
	public int oneStepCount;

	/**
	 * Used to identify the Clusters color
	 */
	public int a = 50;

	/**
	 * Used to identify the Clusters color
	 */
	public int r = 255;

	/**
	 * Used to identify the Clusters color
	 */
	public int g = 255;

	/**
	 * Used to identify the Clusters color
	 */
	public int b = 255;

	/**
	 * Used to identify the Clusters Boarder color
	 */
	public int ba = 50;

	/**
	 * Used to identify the Clusters Boarder color
	 */
	public int br = 55;

	/**
	 * Used to identify the Clusters Boarder color
	 */
	public int bg = 55;

	/**
	 * Used to identify the Clusters Boarder color
	 */
	public int bb = 255; // used to give customized visual of a cluster

	/**
	 * Used to identify the Neural Net associated with the Cluster
	 */
	public NeuralNet nn; // the neural network the cluster belongs to.

	private ArrayList<CommonNeuronExtension> neurons;

	/**
	 * @return neurons of this Cluster
	 */
	public ArrayList<CommonNeuronExtension> getNeurons() {
		return neurons;
	}

	/**
	 * Option to draw or hide Neurons associated with the neuron
	 */
	public boolean drawNeurns = true;

	/**
	 * Used to store the Paint property
	 */
	private Paint paintFill;

	/**
	 * @return the Paint for this Cluster
	 */
	public Paint getPaintFill() {
		return paintFill;
	}

	/**
	 * Used to store the Paint property
	 */
	public Paint paintBorder = new Paint();

	private void onCreate() {
		clusterID = hashCode();
		neurons = new ArrayList<CommonNeuronExtension>();
		paintFill = new Paint();
		Size = new Point();
		Pos = new Point();
		SubCluster = new ArrayList<Cluster>();
		subClusterInterconnection = new ArrayList<Connection>();
	}

	/**
	 * Create an empty Cluster
	 */
	public Cluster() {
		onCreate();
	}

	/**
	 * Create a cluster
	 *
	 * @param Name
	 * @param pos
	 * @param size
	 */
	public Cluster(String Name, Point pos, Point size) {
		onCreate();
		name = Name;
		nn = NeuralNet.getSelected();
		Id = nn.getClusterIndex(this);
		paintFill = new Paint();
		Pos = pos.copy();
		Size = size.copy();
		neurons.addAll(Neuron.getSelection());
		for (CommonNeuronExtension n : neurons) {
			n.setCluster(this);
			n.Pos.setXYZ(n.Pos.sub(Pos));
		}
		updatePaint();
	}

	/**
	 * Create a cluster
	 *
	 * @param Name The name of the cluster
	 * @param pos The (x,y,layer)position the cluster begins at
	 * @param size The (x,y,layer)size of the cluster
	 * @param subP The parent clusters if created in other clusters
	 */
	public Cluster(String Name, Point pos, Point size, ArrayList<Cluster> subP) {
		onCreate();
		name = Name;
		nn = NeuralNet.getSelected();
		Id = nn.getClusterIndex(this);
		paintFill = new Paint();
		Pos = pos.copy();
		Size = size.copy();
		neurons.addAll(Neuron.getSelection());
		if (subP != null && subP.size() >= 1) {
			for (Cluster c : subP) {
				if (c != this && c.SubParent == null) {
					c.SubParent = this;
					SubCluster.add(c);
				}
			}
		}
		for (CommonNeuronExtension n : neurons) {
			if (n.getCluster() == null) {
				n.setCluster(this);
				n.Pos.setXYZ(n.Pos.sub(Pos));
			}
		}
		updatePaint();
	}

	/**
	 * Used to identify if the Cluster is in the SELECTION
	 *
	 * @return true if this Cluster is in the SELECTION
	 */
	public boolean isInSelection() {
		return SELECTION.getSelection().contains((this));
	}

	/**
	 * Used to retrieve a property associated with an ID
	 *
	 * @param propertyID
	 * @return the String form of the property
	 */
	public String getProperty(int propertyID) {
		switch (propertyID) {
			case Cluster.NAME:
				return name;
			case ONE_STEP:
				return oneStep ? "Enabled" : "Disabled";
			case ONE_STEP_COUNT:
				return oneStepCount + "";
			case SUB_PARENT:
				return SubParent != null ? SubParent.getProperty(0) : "null";
			case SUB_CLUSTER:
				String s = "";
				if (SubCluster.isEmpty()) {
					return "Empty";
				}
				for (Cluster SubCluster1 : SubCluster) {
					s += SubCluster1.name
						+ "@"
						+ NeuralNet.getSelected().getClusters()
						.indexOf(SubCluster1) + " & ";
				}
				return s;
			case BACKGROUND_ALPHA:
				return a + "";
			case BACKGROUND_RED:
				return r + "";
			case BACKGROUND_GREEN:
				return g + "";
			case BACKGROUND_BLUE:
				return b + "";
			case BORDER_ALPHA:
				return ba + "";
			case BORDER_RED:
				return br + "";
			case BORDER_GREEN:
				return bg + "";
			case BORDER_BLUE:
				return bb + "";
			case BOUNDRY_MIN_X:
				return (int) Pos.getX() + "";
			case BOUNDRY_MIN_Y:
				return (int) Pos.getY() + "";
			case BOUNDRY_SIZE_X:
				return (int) (Size.getX() + Pos.getX()) + "";
			case BOUNDRY_SIZE_Y:
				return (int) (Size.getY() + Pos.getY()) + "";
			case DRAW_NEURONS:
				return drawNeurns ? "Enabled" : "Disabled";

			default:
				return "";
		}
	}

	/**
	 * Used to set a property of the Cluster
	 *
	 * @param propertyID
	 * @param value
	 * @return true if successful
	 */
	public boolean setProperty(int propertyID, String value) {
		Command cmd = null;
		switch (propertyID) {
			case Cluster.NAME:
				cmd = new SetName<Cluster>(this, value);
				break;
			case ONE_STEP:
				cmd = new ClusterOnestep(this, !oneStep);
				break;
			case ONE_STEP_COUNT:
				cmd = new ClusterSetOnestepCount(this, Integer.parseInt(value));
				break;
			case SUB_PARENT:
				if (Integer.parseInt(value) == 0) {
					if (SubParent != null) {
						SubParent.SubCluster.remove(this);
						SubParent = null;
					}
					return true;
				}
				if (!TestForCircularReference(this, NeuralNet.getSelected()
					.getClusters().get(Integer.parseInt(value) - 1))) {

					SubParent = NeuralNet.getSelected().getClusters()
						.get(Integer.parseInt(value) - 1);

					NeuralNet.getSelected().getClusters()
						.get(Integer.parseInt(value) - 1).SubCluster.add(this);
				} else {
					return false;
				}
				break;
			case SUB_CLUSTER:
				break;
			case BACKGROUND_ALPHA:
				a = Integer.parseInt(value);
				break;
			case BACKGROUND_RED:
				r = Integer.parseInt(value);
				break;
			case BACKGROUND_GREEN:
				g = Integer.parseInt(value);
				break;
			case BACKGROUND_BLUE:
				b = Integer.parseInt(value);
				break;
			case BORDER_ALPHA:
				ba = Integer.parseInt(value);
				break;
			case BORDER_RED:
				br = Integer.parseInt(value);
				break;
			case BORDER_GREEN:
				bg = Integer.parseInt(value);
				break;
			case BORDER_BLUE:
				bb = Integer.parseInt(value);
				break;
			case BOUNDRY_MIN_X:
				Size.setX(Size.getX() + Pos.getX());
				Pos.setX(Integer.parseInt(value));
				Size.setX(Size.getX() - Pos.getX());
				break;
			case BOUNDRY_MIN_Y:
				Size.setY(Size.getY() + Pos.getY());
				Pos.setY(Integer.parseInt(value));
				Size.setY(Size.getY() + Pos.getY());
				break;
			case BOUNDRY_SIZE_X:
				Size.setX(Integer.parseInt(value) - Pos.getX());
				break;
			case BOUNDRY_SIZE_Y:
				Size.setY(Integer.parseInt(value) - Pos.getY());
				break;
			case DRAW_NEURONS:
				drawNeurns = !drawNeurns;
				break;

		}
		updatePaint();
		if (cmd != null) {
			NeuralNet.addCommand(cmd);
		}
		return true;
	}

	private static boolean TestForCircularReference(Cluster c, Cluster parent) {
		if (c == parent) {
			return true;
		}
		ArrayList<Cluster> ref = new ArrayList<Cluster>();
		ref.add(parent);
		ref.add(c);
		c = parent;
		while (c != null) {
			c = c.SubParent;
			if (!ref.contains(c)) {
				ref.add(c);
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Used to copy the Clusters container ( do not need this?)
	 *
	 * @param p
	 * @return true if there is a positional conflict with another
	 * Neuron
	 */
	public boolean isNeuronConflict(Point p) {
		// test for neuron position conflict.
		for (CommonNeuronExtension ne : neurons) {
			for (CommonNeuronExtension n : NeuralNet.getSelected().getNeurons()) {
				if (ne != n && n.getAbsolutePos().equal(p)) {
					NvrL
						.toast("Unable to paste cluster: Neuron on neuron conflict");
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Used to do a copy of Cluster without sub-clusters - copy of
	 * cluster without sub-parent attachment or sub-clusters
	 *
	 * @param p
	 * @return null if conflict or new Cluster
	 */
	 
	private static ArrayList<CommonNeuronExtension> allNeuronsSource = new ArrayList<CommonNeuronExtension>();
	private static ArrayList<CommonNeuronExtension> allNeuronsCloned = new ArrayList<CommonNeuronExtension>();
	
	private static boolean inCopy = false;
	public Cluster copy(Point p){
		if(inCopy)return null;
		Cluster c;
		inCopy= true;
		allNeuronsCloned.clear();
		allNeuronsSource.clear();
		c = copy2(p);
		
		/*
			at this point all neurons associated 
		with this cluster or cluster group have
		been both coppied and stored.  now to
		establish connections.
		
		*/
		Connection.CloneAll(allNeuronsSource, allNeuronsCloned,p.add(Pos.inv()));
		
		inCopy=false;
		return c;
	}
	public Cluster copy_noConnections(Point p){
		if(inCopy)return null;
		Cluster c;
		inCopy= true;
		allNeuronsCloned.clear();
		allNeuronsSource.clear();
		c = copy2(p);

		/*
		 at this point all neurons associated 
		 with this cluster or cluster group have
		 been both coppied and stored.  
		 */
		//Connection.CloneAll(allNeuronsSource, allNeuronsCloned,p.add(Pos.inv()));

		inCopy=false;
		return c;
	}
	public Cluster copy2(Point p) {
		
		//Point oldPos = getAbsolutePos();
		//Point newPos = p;
		if (isNeuronConflict(p)) {
			return null;
		}
		Cluster c = new Cluster();
		c.name = name;
		c.oneStep = oneStep;
		c.oneStepCount = oneStepCount;
		c.a = a;
		c.r = r;
		c.g = g;
		c.b = b;
		c.ba = ba;
		c.br = br;
		c.bg = bg;
		c.bb = bb;
		c.Pos = p.copy();
		c.Size = Size.copy();
		c.nn = NeuralNet.getSelected();
		
		// copy neurons
		for (CommonNeuronExtension n : neurons) {
			CommonNeuronExtension cn = n.copy();
			allNeuronsSource.add(n);
			allNeuronsCloned.add(cn);
			cn.setCluster(c);
			c.neurons.add(cn);
		}
		// copy sub clusters
		for (Cluster cl : SubCluster){
			c.SubCluster.add(cl.copy2(p.add(cl.Pos.sub(Pos))));
		}
		
		c.updatePaint();
		return c;
	}

	/**
	 * Detach this Cluster from the Project
	 *
	 * @return true
	 */
	public boolean Detach() {
		if (Cluster.getSelected() == this) {
			Cluster.setSelected(null);
		}
		if (neurons != null) {
			NeuralNet.getSelected().neurons.removeAll(neurons);
			for (CommonNeuronExtension n : neurons) {
				n.setCluster(null);
			}
		}
		if (SubParent != null) {
			SubParent.SubCluster.remove(this);
		}

		if (SubCluster != null && !SubCluster.isEmpty()) {
			for (Cluster c : SubCluster) {
				c.SubParent = null;
				c.Detach();
				nn.clusters.remove(c);
			}
		}
		if (nn != null) {
			nn.clusters.remove(this);
		}
		return true;
	}

	/**
	 * Attach this Cluster to the Project
	 *
	 * @return true
	 */
	public boolean Attach() {
		Cluster.setSelected(this);
		if (neurons != null) {
			for (CommonNeuronExtension n : neurons) {
				n.setCluster(this);
				if (!nn.getNeurons().contains(n)) {
					nn.getNeurons().add(n);
				}
			}

		}
		if (SubParent != null) {
			SubParent.SubCluster.add(this);
		}

		if (SubCluster != null && !SubCluster.isEmpty()) {
			for (Cluster c : SubCluster) {
				c.Attach();
				c.SubParent = this;
			}
		}
		if (nn != null) {
			nn.clusters.add(this);
		}
		return true;
	}

	/**
	 * Used to remove the Cluster and reuse Neurons
	 */
	public void delete() {
		if (neurons != null) {
			for (CommonNeuronExtension n : neurons) {
				n.Pos.set(n.Pos.add(Pos));
				n.setCluster(null);
			}
		}
		if (SubParent != null) {
			SubParent.SubCluster.remove(this);
		}
		if (SubCluster != null && !SubCluster.isEmpty()) {
			for (Cluster c : SubCluster) {
				c.SubParent = null;
			}
		}
	}

	/**
	 * Used for complete removal of all elements associated with the
	 * Cluster (this could be stripped down, Garbage collection does
	 * most of this anyway)
	 */
	public void fullDelete() {
		if (Cluster.getSelected() == this) {
			Cluster.setSelected(null);
		}
		if (neurons != null) {
			for (CommonNeuronExtension n : neurons) {
				n.remove();
			}
			neurons.clear();
		}
		if (SubParent != null) {
			SubParent.SubCluster.remove(this);
		}

		if (SubCluster != null && !SubCluster.isEmpty()) {
			for (Cluster c : SubCluster) {
				c.fullDelete();
			}
			SubCluster.clear();
		}
		if (nn != null) {
			nn.clusters.remove(this);
		}

	}

	/**
	 * Used to update the paint PROPERTIES for the Cluster
	 */
	private void updatePaint() {
		paintFill.setARGB(a, r, g, b);
		paintFill.setStyle(Paint.Style.FILL);
		paintBorder.setARGB(ba, br, bg, bb);
		paintBorder.setStrokeWidth(5);
		paintBorder.setStyle(Paint.Style.STROKE);
		paintBorder.setTextSize(20);
	}

	/**
	 * Used to create a clone of the cluster on a layer ( not working
	 * )
	 *
	 * @return Cluster
	 */
	public Cluster CreateChildCluster() {
		Cluster c = new Cluster();
		c.name = "<" + this.name;
		c.nn = this.nn;
		c.nn.addCluster(this);
		c.Id = nn.getClusterIndex(this);
		c.paintFill = new Paint();
		c.Pos = this.Pos.copy().add(new Point(0, 0, 1));
		c.Size = this.Size.copy();
		for (CommonNeuronExtension n : c.neurons) {
			n.setCluster(c);
		}
		c.updatePaint();
		return c;
	}

	/**
	 * @param load
	 * @param neuronList
	 */
	public Cluster(String load, ArrayList<CommonNeuronExtension> neuronList) {
		onCreate();
		int i = 1;
		if(!NvrL.isUpdatedBuild()){}
		clusterID = Integer.parseInt(FileIO.getPart(load, i++));
		name = FileIO.getPart(load, i++);
		oneStep = Boolean.parseBoolean(FileIO.getPart(load, i++));
		oneStepCount = Integer.parseInt(FileIO.getPart(load, i++));
		a = Integer.parseInt(FileIO.getPart(load, i++));
		r = Integer.parseInt(FileIO.getPart(load, i++));
		g = Integer.parseInt(FileIO.getPart(load, i++));
		b = Integer.parseInt(FileIO.getPart(load, i++));
		ba = Integer.parseInt(FileIO.getPart(load, i++));
		br = Integer.parseInt(FileIO.getPart(load, i++));
		bg = Integer.parseInt(FileIO.getPart(load, i++));
		bb = Integer.parseInt(FileIO.getPart(load, i++));
		float x = Float.parseFloat(FileIO.getPart(load, i++));
		float y = Float.parseFloat(FileIO.getPart(load, i++));
		float z = Float.parseFloat(FileIO.getPart(load, i++));
		float sx = Float.parseFloat(FileIO.getPart(load, i++));
		float sy = Float.parseFloat(FileIO.getPart(load, i++));
		float sz = Float.parseFloat(FileIO.getPart(load, i++));
		Pos.setXYZ(x, y, z);
		Size.setXYZ(sx, sy, sz);
		int o = FileIO.oCount(load);
		for (; i <= o;) {
			CommonNeuronExtension n = neuronList.get(Integer.parseInt(FileIO
				.getPart(load, i++)));
			neurons.add(n);
			n.setCluster(this);
		}
		updatePaint();
	}

	/**
	 * @param neuronList
	 * @return data string of Cluster attributes and neurons
	 */
	public String toString(ArrayList<CommonNeuronExtension> neuronList) {
		if(name.contentEquals("or")){
			
		}
		String s = "[C]" + clusterID + "&" + name + "&" + oneStep + "&" + oneStepCount
			+ "&" + a + "&" + r + "&" + g + "&" + b + "&" + ba + "&" + br
			+ "&" + bg + "&" + bb + "&" + Pos.X + "&" + Pos.Y + "&" + Pos.Z
			+ "&" + Size.X + "&" + Size.Y + "&" + Size.Z + "&";
		for (CommonNeuronExtension n : neurons) {
			s += neuronList.indexOf(n) + "&";
		}
		return s + "\n";
	}

	/**
	 * @param clusterList
	 * @return data string of sub clusters
	 */
	public String toSubsString(ArrayList<Cluster> clusterList) {
		String s = "[S]";
		s += clusterList.indexOf(this) + "&";
		s += clusterList.indexOf(SubParent) + "&";
		return s + "\n";
	}

	/**
	 * @param in
	 * @param clusterList
	 */
	public static void assignSubs(final String in,
		final ArrayList<Cluster> clusterList) {
		
		Cluster c = clusterList.get(Integer.parseInt(FileIO.getPart(in, 1)));

		int q = Integer.parseInt(FileIO.getPart(in, 2));
		c.SubParent = (q == -1 ? null : clusterList.get(q));

		if (c.SubParent != null && !c.SubParent.SubCluster.contains(c)) {
			c.SubParent.SubCluster.add(c);
		}
	}

	/**
	 * Test if specified point p is in the Cluster
	 *
	 * @param p
	 * @return true if point is in rectangle
	 */
	public boolean isPointInCluster(Point p) {
		return p.isInRect(Pos, Pos.add(Size));
	}

	/**
	 * Test specified point p is a neuron associated with the Cluster
	 *
	 * @param p
	 * @return true if point is a neuron
	 */
	public boolean isPointaNeuron(Point p) {
		for (CommonNeuronExtension n : neurons) {
			if (p.equal(Pos.add(n.Pos))) {
				return true;
			}
		}
		return false;
	}
	
	// exclude waypoints outside of cluster for movement
	public ArrayList<WayPoint> getWayPoints(){
		ArrayList<WayPoint> waylist = new ArrayList<WayPoint>();
		for(CommonNeuronExtension n: neurons){
			for(Connection c:n.inputs){
				for(WayPoint w:c.getWayPoints()){
					if(w.getPos().X>=Pos.X && w.getPos().Y>=Pos.Y &&
					   w.getPos().X<=Pos.X+getSize().X && w.getPos().Y<=Pos.Y+getSize().Y){
						   waylist.add(w);
					   }
				}
				//waylist.addAll(c.getWayPoints());
			}
		}
		return waylist;
	}

	/**
	 * Test if Cluster is in a rectangle defined by Ra and Rb
	 *
	 * @param Ra
	 * @param Rb
	 * @return true if Cluster is in or touches defined rectangle
	 */
	public boolean isClusterInRect(Point Ra, Point Rb) {
		return Point.RectIntersect(Pos, Pos.add(Size), Ra, Rb);
	}

	/**
	 * Do a simulation step of the Cluster
	 */
	public void SimStep() {
		for (CommonNeuronExtension n : neurons) {
			n.doInputs();
		}
		if (!SubCluster.isEmpty()) {
			for (Cluster c : SubCluster) {
				if (c.oneStep) {
					for (int i = 0; i < c.oneStepCount; i++) {
						c.SimStep();
					}
				} else {
					c.SimStep();
				}
			}
		}

		for (CommonNeuronExtension n : neurons) {
			n.setEnergy(0);
		}
		for (CommonNeuronExtension n : neurons) {
			n.doTransfer();
		}
	}

	/**
	 * Used to convert absolute positions to point and size
	 *
	 * @param a
	 * @param b
	 */
	public void updateAbsolutePos(Point a, Point b) {
		Size.set(b.sub(a));
		Pos.set(a);
	}

	@Override
	public Point getAbsolutePos() {
		return getPos(); 
	}
	
	
	/**
	 * @return grid point of lower right side of cluster
	 */
	public Point getAbsoluteSize() {
		return Pos.add(Size);
	}
	
	public int getID(){
		return clusterID;
	}
}
