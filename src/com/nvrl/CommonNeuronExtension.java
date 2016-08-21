package com.nvrl;

import com.nvrl.CommonCommands.SetPos;
import java.util.ArrayList;
import java.util.HashMap;

public class CommonNeuronExtension extends CommonExtensions{
	public static final ArrayList<String> errorlog= new ArrayList<String>();
	public static final ArrayList<CommonNeuronExtension> erroredNeurons= new ArrayList<CommonNeuronExtension>();
	
	private static final HashMap<Integer,CommonNeuronExtension> NEURON_TABLE = new HashMap<Integer,CommonNeuronExtension>();

	public static final void addNeuronToMap(CommonNeuronExtension n){
		NEURON_TABLE.put(n.getID(),n);
	}
	public static final CommonNeuronExtension getNeuronFromMap(int id){
		return NEURON_TABLE.get(id);
	}
	public static final void removeNeuronFromMap(int id){
		NEURON_TABLE.remove(id);
	}
	public static final void removeNeuronFromMap(CommonNeuronExtension n){
		NEURON_TABLE.remove(n.getID());
	}
	/**
	 * Selection manager for non cluster neurons
	 */
	public static final SelectionManager<CommonNeuronExtension> SELECTION = new SelectionManager<CommonNeuronExtension>();

	/**
	 * Selection manager for all neurons
	 */
	public static final SelectionManager<CommonNeuronExtension> ABSOLUTE_SELECTION = new SelectionManager<CommonNeuronExtension>();

	/**
	 * trigger to edit input or output connection order for neuron
	 */
	public static boolean outs = false;

	public double thresholdHigh = 0;
	public double thresholdLow=0;
	public double energy = 0;
	public Cluster cluster = null;
	public boolean clamp = true;
	public float minClamp = 0;
	public float maxClamp = 1;
	private int id;

	/**
	 *  Input connections for this neuron
	 */
	public ArrayList<Connection> inputs;

	/**
	 * Output connections for this neuron
	 */
	public ArrayList<Connection> outputs;
	
	public void doInputs(){
		Connection.doWeightsInList(inputs);
		//energy=0;
	}


	/**
	 * set the cluster of the neuron
	 *
	 * @param cluster
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	/**
	 * Get the cluster the neuron is attached to
	 *
	 * @return Cluster
	 */
	public Cluster getCluster() {
		return cluster;
	}

	/**
	 * is the neuron in a cluster
	 *
	 * @return boolean
	 */
	public boolean isInCluster() {
		return (cluster != null);
	}
	public void setMaxClamp(float maxClamp) {
		this.maxClamp = maxClamp;
	}

	public float getMaxClamp() {
		return maxClamp;
	}

	public void setMinClamp(float minClamp) {
		this.minClamp = minClamp;
	}

	public float getMinClamp() {
		return minClamp;
	}

	public void setClamp(boolean clamp) {
		this.clamp = clamp;
	}

	public boolean isClamp() {
		return clamp;
	}

	
	public int getID(){
		return id;
	}
	/**
	 * set the neurons threshold
	 *
	 * @param threshold
	 */
	public void setThreshold(double threshold) {
		this.thresholdHigh = threshold;
	}

	/**
	 * get the neurons threshold
	 *
	 * @return float
	 */
	public double getThreshold() {
		return thresholdHigh;
	}

	/**
	 * set the neurons energy
	 *
	 * @param Energy
	 * @return Neuron
	 */
	public CommonNeuronExtension setEnergy(double Energy) {
		energy = Energy;
		return this;
	}

	/**
	 * get the neurons energy
	 *
	 * @return float
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * get energy only from threshold
	 *
	 * @return float
	 */
	public double getEnergyt() {
		return (energy > thresholdHigh) ? energy : 0;
	}

	/**
	 * is neuron in a fire state
	 *
	 * @return boolean
	 */
	public boolean isFiring() {
		return (energy > thresholdHigh);
	}

	/**
	 * remove/delete neuron
	 */
	public void remove() {
		if (this == CommonNeuronExtension.getSelected()) {
			CommonNeuronExtension.setSelected(null);
		}
		if (this == CommonNeuronExtension.getPreviousSelected()) {
			CommonNeuronExtension.setPreviousSelected(null);
		}
		while (!inputs.isEmpty()) {
			inputs.get(0).remove();
		}
		while (!outputs.isEmpty()) {
			outputs.get(0).remove();
		}
		NeuralNet.getSelected().neurons.remove(this);
		NEURON_TABLE.remove(id);
	}

	/**
	 * copy neuron n
	 *
	 * @param n
	 * @return Neuron
	 */
	public static CommonNeuronExtension copy(CommonNeuronExtension n) {
		return new CommonNeuronExtension(n.getThreshold(), n.getName());
	}

	/**
	 * copy the neuron
	 *
	 * @return Neuron
	 */
	public CommonNeuronExtension copy() {

		return (new CommonNeuronExtension(name, thresholdHigh, energy, Pos));// , scripts));
	}

	/**
	 * copy the neuron to a new point
	 *
	 * @param p
	 * @return Neuron
	 */
	public CommonNeuronExtension copy(Point p) {
		return new CommonNeuronExtension(name, thresholdHigh, energy, p);// , scripts);
	}

	
	/**
	 * a method that houses common actions on the construction of a neuron 
	 */
	private void onCreate() {
		id=hashCode();
		inputs = new ArrayList<Connection>();
		outputs = new ArrayList<Connection>();
	}

	/**
	 * Get output connections
	 *
	 * @return ArrayList of Connections
	 */
	public ArrayList<Connection> getOutputs() {
		return outputs;
	}

	/**
	 * Update neuron position
	 */
	public void updatePos() {
		Point p = null;
		if (cluster.Pos.X > getAbsolutePos().X) {
			p = new Point(0, Pos.Y, Pos.Z);
		}
		if (cluster.Pos.Y > getAbsolutePos().Y) {
			p = new Point(Pos.X, 0, Pos.Z);
		}
		if (p != null) {
			Command cmd = new SetPos<CommonNeuronExtension>(this, p);
			NeuralNet.addCommand(cmd);
		}
	}

	/**
	 * is the Neuron in absolute selection
	 *
	 * @param absolutSelection
	 * @return boolean
	 */
	public boolean inAbsolutSelection(ArrayList<CommonNeuronExtension> absolutSelection) {
		return Neuron.ABSOLUTE_SELECTION.getSelection().contains(this);
	}

	/**
	 * get the absolute Neuron selection
	 *
	 * @return ArrayList<Neuron>
	 */
	public static ArrayList<CommonNeuronExtension> getAbsoluteSelection() {
		return ABSOLUTE_SELECTION.getSelection();
	}

	/**
	 * get the Neuron selection
	 *
	 * @return ArrayList<Neuron>
	 */
	public static ArrayList<CommonNeuronExtension> getSelection() {
		return SELECTION.getSelection();
	}

	/**
	 * is the neuron in the selection
	 *
	 * @return boolean
	 */
	public boolean inSelection() {
		return CommonNeuronExtension.SELECTION.getSelection().contains(this);
	}

	/**
	 * set the previous selected neuron
	 *
	 * @param previousSelected
	 */
	public static void setPreviousSelected(CommonNeuronExtension previousSelected) {
		CommonNeuronExtension.SELECTION.setPreviouslySelected(previousSelected);
	}

	/**
	 * get the previously selected neuron
	 *
	 * @return Neuron
	 */
	public static CommonNeuronExtension getPreviousSelected() {
		return SELECTION.getPreviouslySelected();
	}

	/**
	 * set the selected neuron
	 *
	 * @param selected
	 */
	public static void setSelected(CommonNeuronExtension selected) {
		if(selected instanceof Text_Input){
			NvrL.enableTextDrawer();
		}else{
			NvrL.disableTextDrawer();
		}
		Neuron.SELECTION.setSelected(selected);
	}

	/**
	 * get the selected neuron
	 *
	 * @return CommonNeuronExtension
	 */
	public static CommonNeuronExtension getSelected() {
		return SELECTION.getSelected();
	}

	/**
	 * construct a neuron from a data string (to load)
	 *
	 * @param load
	 */
	public CommonNeuronExtension(String load) {
		int i = 1;//so = FileIO.oCount(load);
		onCreate();
		if(!NvrL.isUpdatedBuild()){}
		id = Integer.parseInt(FileIO.getPart(load,i++));
		name = FileIO.getPart(load, i++);
		name = name.contentEquals("no name") ? "" : name;
		thresholdHigh = Double.parseDouble(FileIO.getPart(load, i++));
		energy = Double.parseDouble(FileIO.getPart(load, i++));

		Pos.X = Float.parseFloat(FileIO.getPart(load, i++));
		Pos.Y = Float.parseFloat(FileIO.getPart(load, i++));
		Pos.Z = Float.parseFloat(FileIO.getPart(load, i++));

		clamp = Boolean.parseBoolean(FileIO.getPart(load, i++));
		minClamp = Float.parseFloat(FileIO.getPart(load, i++));
		maxClamp = Float.parseFloat(FileIO.getPart(load, i++));
		
		addNeuronToMap(this);
	}
	public static void clearErrors(){
		errorlog.clear();
		erroredNeurons.clear();
		DisplayEnvironment.ERRORS=false;
	}
	//report error
	public void sendError(String ident,String msg){
		EditorUi.setRunnet(false);
		DisplayEnvironment.ERRORS=true;
		errorlog.add("Error: ["+ident+" @ "+Pos.toString()+"] "+msg);
		erroredNeurons.add(this);
		//NvrL.enableErrorDrawer();
	}
	/**
	 * convert a neuron into a data string (for loading)
	 *
	 * @return String - data string
	 */
	@Override
	public String toString() {
		String s = id+"&"+(name.equals("") ? "no name" : name) + "&"
			+ thresholdHigh + "&" + energy + "&" + Pos.X + "&" + Pos.Y + "&"
			+ Pos.Z + "&" + clamp + "&" + minClamp + "&" + maxClamp + "&";

		return s + "\n";
	}

	/**
	 * construct a neuron
	 *
	 * @param name
	 * @param threshold
	 * @param x
	 * @param y
	 * @param z
	 */
	public CommonNeuronExtension(String name, double threshold, float x, float y, float z) {
		onCreate();
		this.name = name;
		this.thresholdHigh = threshold;
		this.Pos = new Point(x, y, z);
		addNeuronToMap(this);
	}

	/**
	 * construct a neuron
	 *
	 * @param threshold - Threshold of the neuron.
	 * @param p - Point to create the neuron
	 */
	public CommonNeuronExtension(float x, float y, float z,double energy,double threshold) {
		onCreate();
		this.energy = energy;
		this.thresholdHigh = threshold;
		this.Pos = new Point(x, y, z);
		addNeuronToMap(this);
	}

	/**
	 * construct a neuron
	 *
	 * @param name
	 * @param threshold
	 * @param p
	 */
	public CommonNeuronExtension(String name, double threshold, Point p) {
		onCreate();
		this.name = name;
		this.thresholdHigh = threshold;
		this.Pos = p.copy();
		addNeuronToMap(this);
	}

	/**
	 * construct a neuron
	 *
	 * @param name
	 * @param threshold
	 * @param energy
	 * @param p
	 */
	public CommonNeuronExtension(String name, double threshold, double energy, Point p) {
		onCreate();
		this.name = name;
		this.thresholdHigh = threshold;
		this.energy = energy;
		this.Pos = p.copy();
		addNeuronToMap(this);
	}

	/**
	 * construct a neuron
	 *
	 * @param threshold
	 * @param name
	 */
	public CommonNeuronExtension(double threshold, String name) {
		onCreate();
		this.name = name;
		this.thresholdHigh = threshold;
		this.energy = 0.0;
		addNeuronToMap(this);
	}

	/**
	 * construct a neuron
	 */
	public CommonNeuronExtension() {
		onCreate();
		addNeuronToMap(this);
	}

	/**
	 * set neuron's x position
	 *
	 * @param x
	 */
	public void setX(int x) {
		this.Pos.X = x;
	}

	/**
	 * get the neuron's x position
	 *
	 * @return float
	 */
	public float getX() {
		return Pos.X;
	}

	/**
	 * set the neuron's y position
	 *
	 * @param y
	 */
	public void setY(int y) {
		this.Pos.Y = y;
	}

	/**
	 * get the neuron's y position
	 *
	 * @return float
	 */
	public float getY() {
		return Pos.Y;
	}

	public void setZ(int z) {
		this.Pos.Z = z;
	}

	/**
	 * get the neuron's y position
	 *
	 * @return float
	 */
	public float getZ() {
		return Pos.Z;
	}
	/**
	 * set the neuron's position
	 *
	 * @param X
	 * @param Y
	 */
	public void setPos(int X, int Y) {
		Pos.X = X;
		Pos.Y = Y;
	}

	/**
	 * get the neuron's absolute grid position if in cluster
	 *
	 * @return Point
	 */
	@Override
	public Point getAbsolutePos() {
		return (isInCluster()) ? Pos.add(cluster.Pos) : Pos.copy();
	}

	@Override
	public void setAbsolutePos(Point p) {
		Pos = isInCluster() ? p.sub(cluster.Pos) : p.copy();
	}

	/**
	 * get the list of inputs the neuron has
	 *
	 * @return ArrayList<Connection>
	 */
	public ArrayList<Connection> getInputs() {
		return inputs;
	}

	/**
	 * get input at index
	 *
	 * @param index
	 * @return Connection
	 */
	public Connection getInput(int index) {
		return inputs.get(index);
	}
	/**
	 * add an input connection to the neuron
	 *
	 * @param c
	 */
	public void addInput(Connection c) {
		inputs.add(c);
	}

	/**
	 * remove connection c from the neuron's input list
	 *
	 * @param c
	 */
	public void removeInput(Connection c) {
		inputs.remove(c);
	}

	/**
	 * add connection c to the neuron's output list
	 *
	 * @param c
	 */
	public void addOutput(Connection c) {
		outputs.add(c);
	}

	/**
	 * remove connection c from the neuron's output list
	 *
	 * @param c
	 */
	public void removeOutput(Connection c) {
		outputs.remove(c);
	}

	/**
	 * get the connection from the neuron a to this neuron
	 *
	 * @param source
	 * @return Connection
	 */
	public Connection getConnectionFrom(CommonNeuronExtension source) {
		for (Connection c : inputs) {
			if (c.getInput() == source && c.getOutput() == this) {
				return c;
			}
		}
		return null;
	}

	/**
	 * @param dest
	 * @return Connection
	 */
	public Connection getConnectionTo(CommonNeuronExtension dest) {
		for (Connection c : outputs) {
			if (c.getInput() == this && c.getOutput() == dest) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * transfer energy to neurons
	 */
	public void doTransfer() {
		// this is where neuron types take some effect
		Connection.doTransferInList(inputs);
	}
	
}
