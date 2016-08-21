package com.nvrl;

import java.util.ArrayList;

/**
 * Used to create a Connection between Neurons
 *
 * @author Michael
 * @version 1.0
 * 
 * 
 */
public class Connection extends CommonExtensions {

	private CommonNeuronExtension input;
	private CommonNeuronExtension output;
	private int type = 0;
	private double weight;
	private double energy;
	private int connectionID;
	
	private final ArrayList<WayPoint> waypoints = new ArrayList<WayPoint>();

	/**
	 * The selection manager for connections not inside a cluster
	 */
	public static final SelectionManager<Connection> SELECTION = new SelectionManager<Connection>();

	/**
	 * The Selection manager for all connections
	 */
	public static final SelectionManager<Connection> ABSOLUTE_SELECTION = new SelectionManager<Connection>();

	/**
	 * Connection action ID's 
	 * OUT implies: the connection's output neuron 
	 * IN implies: the connection's input neuron 
	 * CON implies: the connection's
	 */
	
	/**
	 * Default: set OUT energy to CON energy if IN fires
	 */
	public static final int push = 0;

	/**
	 * set OUT energy to 0 if IN fires
	 */
	public static final int inhibiter = 1;

	/**
	 * set OUT energy to threshold if IN fires
	 */
	public static final int e2t = 2;

	/**
	 * set OUT threshold to energy if IN fires
	 */
	public static final int t2e = 3;

	/**
	 * set OUT threshold to increment by CON energy
	 */
	public static final int plusT = 4;

	/**
	 * set OUT threshold to decrease by CON energy
	 */
	public static final int minusT = 5;

	/**
	 * saps IN energy to CON energy if OUT fires
	 */
	public static final int pull = 6;

	/**
	 * I do not remember
	 */
	public static final int plusW = 7;

	/**
	 * I do not remember
	 */
	public static final int minusW = 8;
	
	public static final int invert = 9;
	public static final int passT = 10;
	public static final int passNID = 11;
	public static final int passX = 12;
	public static final int passY = 13;
	public static final int passZ = 14;
	public static final int passCID = 15;
	

	/**
	 * String Array for the names of action ID's
	 */
	public static final String[] typeNames = {"<Push Energy>", "<Inhibiter>",
		"<Energy to Threshold>", "<Threshold to Energy>",
		"<Increase Threshold>", "<Decrease Threshold>", "<Pull Energy>",
		"<Increase Weight>", "<Decrease Weight>","<Invert>","<Pass Threshhold>",
		"<Pass Neuron ID>","<Pass X coord>","<Pass Y coord>", "<Pass Z coord>",
		"<Pass Connection ID>"};

	/**
	 * Used to reference WayPoints used by this connection
	 *
	 * @return ArrayList of WayPoints
	 */
	public ArrayList<WayPoint> getWayPoints() {
		return waypoints;
	}

	/**
	 * @param wayPointList
	 */
	public void setWayPoints(ArrayList<WayPoint> wayPointList) {
		waypoints.addAll(wayPointList);
	}

	/**
	 * Used to reference multiple Connections within a selection
	 *
	 * @return ArrayList of Connections
	 */
	public static ArrayList<Connection> getSelection() {
		return SELECTION.getSelection();
	}

	/**
	 * @return ArrayList<Connection>
	 */
	public static ArrayList<Connection> getAbsoluteSelection() {
		return ABSOLUTE_SELECTION.getSelection();
	}

	/**
	 * Used to identify a selected Connection
	 *
	 * @param set
	 */
	public static void setSelected(Connection set) {
		Connection.SELECTION.setSelected(set);
	}

	/**
	 * Used to retrieve the selected Connection
	 *
	 * @return Connection
	 */
	public static Connection getSelected() {
		return Connection.SELECTION.getSelected();
	}

	/**
	 * Produces a data string of Connection data for a list of Neurons
	 * ( for saving )
	 *
	 * @param n
	 * @return String
	 */
	public String toString(ArrayList<CommonNeuronExtension> n) {
		String s = "[c]" +connectionID +"&"+ n.indexOf(input) + "&" + n.indexOf(output)
			+ "&" + weight + "&" + energy + "&" + type + "&" ;

		for (WayPoint p : waypoints) {
			s += p.toString();
		}
		return s + "\n";
	}

	/**
	 * Produces a Connection based on a data string ( for loading )
	 *
	 * @param load
	 * @param n
	 */
	public Connection(String load, ArrayList<CommonNeuronExtension> n) {
		int i = 1, so = FileIO.oCount(load);
		connectionID = hashCode();
		if(!NvrL.isUpdatedBuild()){}
		
		connectionID = Integer.parseInt(FileIO.getPart(load, i++));
		int in = Integer.parseInt(FileIO.getPart(load, i++));
		int out = Integer.parseInt(FileIO.getPart(load, i++));
		input = in > -1 ? n.get(in) : null;
		output = out > -1 ? n.get(out) : null;
		weight = Double.parseDouble(FileIO.getPart(load, i++));
		energy = Double.parseDouble(FileIO.getPart(load, i++));
		type = Integer.parseInt(FileIO.getPart(load, i++));
		
		for (; i <= so;) {
			float x, y, z;
			x = Float.parseFloat(FileIO.getPart(load, i++));
			y = Float.parseFloat(FileIO.getPart(load, i++));
			z = Float.parseFloat(FileIO.getPart(load, i++));
			WayPoint w = new WayPoint(this, new Point(x, y, z));
			waypoints.add(w);
			WayPoint.getAllWayPoints().add(w);
		}
	}

	/**
	 * Used to construct an empty Connection
	 */
	public Connection() {
		connectionID = hashCode();
	}

	/**
	 * Used to textually describe the Connections flow
	 *
	 * @return String
	 */
	@Override
	public String getName() {
		return input.getName() + " >> " + output.getName();
	}

	/**
	 * Construct a Connection
	 *
	 * @param input
	 * @param output
	 * @param weight
	 */
	public Connection(CommonNeuronExtension input, CommonNeuronExtension output, double weight) {
		connectionID = hashCode();
		this.input = input;
		this.output = output;
		this.weight = weight;
		this.energy = 0.0f;
	}

	/**
	 * Construct a Connection
	 *
	 * @param input
	 * @param output
	 * @param weight
	 * @param type
	 */
	public Connection(CommonNeuronExtension input, CommonNeuronExtension output, double weight, int type) {
		connectionID = hashCode();
		this.input = input;
		this.output = output;
		this.weight = weight;
		this.energy = 0.0f;
		this.type = type;
	}

	/**
	 * Clone Connection's ( used as part of inserting a Group into a
	 * NeuralNet )
	 *
	 * @param origin
	 * @param copied
	 */
	public static void CloneAll(ArrayList<CommonNeuronExtension> origin,
								ArrayList<CommonNeuronExtension> copied) {

		for (CommonNeuronExtension n : origin) {
			if (!n.getInputs().isEmpty()) {
				for (Connection oc : n.getInputs()) {
					int na = origin.indexOf(oc.getOutput());
					int nb = origin.indexOf(oc.getInput());
					CommonNeuronExtension cdst = na>-1?copied.get(na):null;
					CommonNeuronExtension csrc = nb>-1?copied.get(nb):null;

					if(cdst != null && csrc != null){
						if (cdst.getConnectionFrom(csrc) == null) {
							Connection c = new Connection(csrc, cdst,
							oc.getWeight(), oc.getType());

							c.connect();
							if (!oc.getWayPoints().isEmpty()) {
								for (WayPoint wp : oc.getWayPoints()) {
									wp.copy(c);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void CloneAll(ArrayList<CommonNeuronExtension> origin, ArrayList<CommonNeuronExtension> copied,Point p) {

		for (CommonNeuronExtension n : origin) {
			if (!n.getInputs().isEmpty()) {
				for (Connection oc : n.getInputs()) {
					// this could be the problem
					// need protection from connections not in selection
					int id1 = origin.indexOf(oc.getOutput());
					int id2 = origin.indexOf(oc.getInput());
					if(id1 == -1 || id2 == -1)continue;
					CommonNeuronExtension cdst = copied.get(origin.indexOf(oc.getOutput()));
					
					CommonNeuronExtension csrc = copied.get(origin.indexOf(oc.getInput()));
					
					if (cdst.getConnectionFrom(csrc) == null) {
						Connection c = new Connection(csrc, cdst,
							oc.getWeight(), oc.getType());

						c.connect();
						if (!oc.getWayPoints().isEmpty()) {
							for (WayPoint wp : oc.getWayPoints()) {
								wp.copy(c,p);
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Connects this connection to Input/Output neurons
	 */
	public void connect() {
		if (!input.outputs.contains(this)) {
			input.outputs.add(this);
		}
		if (!output.inputs.contains(this)) {
			output.inputs.add(this);
		}
	}

	/**
	 * detach this connection from the Input/Output neurons
	 */
	public void disconnect() {
		if (input.outputs.contains(this)) {
			input.outputs.remove(this);
		}
		if (output.inputs.contains(this)) {
			output.inputs.remove(this);
		}
	}

	/**
	 * Get the input Neuron for the Connection
	 *
	 * @return Neuron
	 */
	public CommonNeuronExtension getInput() {
		return input;
	}

	/**
	 * Get the output Neuron for the Connection
	 *
	 * @return Neuron
	 */
	public CommonNeuronExtension getOutput() {
		return output;
	}

	/**
	 * Set the weight of the Connection
	 *
	 * @param Weight
	 */
	public void setWeight(double Weight) {
		weight = Weight;
	}

	/**
	 * Get the weight of the Connection
	 *
	 * @return float
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Set the energy of the Connection ( not used ?)
	 *
	 * @param energy
	 */
	public void setEnergy(double energy) {
		this.energy = energy;
	}

	/**
	 * get the energy of the Connection ( not used ?)
	 *
	 * @return float
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * Removes the Connection
	 */
	public void remove() {

		if (Connection.SELECTION.getSelected() == this) {
			Connection.SELECTION.setSelected(null);
		}
		disconnect();
		if (waypoints != null) {
			for (WayPoint w : waypoints) {
				WayPoint.getAllWayPoints().remove(w);
			}
		}
	}

	/**
	 * Calculate the energy of the Connection
	 */
	public void doWeight() {
		energy = input.getEnergyt() * weight;
	}

	/**
	 * Transfer calculated energy and/or apply Connection behavior
	 *
	 * TODO: add option to run script as an option can also make
	 * cleaner with better oop
	 */
	public void doTransfer() {
		double Energy = output.clamp ? FastMath.clamp(output.getEnergy() + energy, output.minClamp, output.maxClamp):output.getEnergy() + energy;
		switch (type) {
			
			/* set output neuron's energy to 0 */
			case inhibiter:
				if (energy != 0) {
					output.setEnergy(0);
				}
				break;
			
			/* transfers previous accumulated energy to threshold */
			case e2t:
				
				output.setThreshold(Energy);
				break;
		
			/* replaces all accumulated energy with threshold */
			case t2e:
				output.setEnergy(input.getThreshold());
				break;
			
			/* increases threshold by connection energy amount */
			case plusT:
				output.setThreshold(output.clamp ? Math.min(output.maxClamp, output.getThreshold() + energy):output.getThreshold() + energy);
				break;
			
			/* decreases threshold by connections energy amount */
			case minusT:
				output.setThreshold(output.clamp ? Math.max(output.minClamp, output.getThreshold() - energy): output.getThreshold() - energy);
				break;
			
			/* takes the accumulated energy from the reciever */
			case pull: 
				input.setEnergy(Energy + input.getEnergy());
				output.setEnergy(input.getEnergy() + output.getEnergy());
				input.setEnergy(0);
				break;
			
			/* increases the weight of all output connections */
			case plusW:
				for (Connection c : output.getOutputs()) {
					c.setWeight(output.clamp ? Math.min(1, c.getWeight() + energy): output.getEnergy() + energy);
				}
				break;
				
			/* decreases the weight of all output connections */
			case minusW:
				for (Connection c : output.getOutputs()) {
					c.setWeight(output.clamp ? Math.max(output.minClamp, c.getWeight() - energy):output.getEnergy() + energy );
				}
				break;
			case invert:
				double out = Energy;
				
				if (output.clamp == true ) {
					if(out>=output.maxClamp){
						out=output.minClamp;
					}else if(out<=output.minClamp){
						out = output.maxClamp;
					}
				}else
					out=-out;
				output.setEnergy(out);
				break;
			case passT:
				if(Energy>0)
					output.setThreshold(input.getThreshold());
				break;
				
			case passNID:
				output.setThreshold(input.getID());break;
			case passX:
				if(Energy>0)
					output.setThreshold(input.getX());
				break;
			case passY:
				if(Energy>0)
					output.setThreshold(input.getY());
				break;
			case passZ:
				if(Energy>0)
					output.setThreshold(input.getZ());
				break;
			case passCID:
				output.setThreshold(input.getCluster().getID());break;
			
			/* set receiving neurons energy */
			default:
				output.setEnergy(Energy);
				break;
		}
		//energy = 0;
	}

	/**
	 * Calculate the energy of a list of Connections
	 *
	 * @param cl
	 */
	public static void doWeightsInList(ArrayList<Connection> cl) {
		for (Connection c : cl) {
			c.doWeight();
		}
	}

	/**
	 * Transfer energy and/or behavior to a list of connections
	 *
	 * @param cl
	 */
	public static void doTransferInList(ArrayList<Connection> cl) {
		for (Connection c : cl) {
			c.doTransfer();
		}
	}

	/**
	 * Set the Connection type ( behavior )
	 *
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Get the Connection type
	 *
	 * @return integer - the integer type of connection
	 */
	public int getType() {
		return type;
	}

	/**
	 * Get the Connection type description
	 *
	 * @return String - name of the connection type
	 */
	public String getTypeDescription() {
		return typeNames[type];
	}

	/**
	 * Add a WayPoint to the Connection
	 *
	 * @param wayPoint
	 */
	public void AddWayPoint(WayPoint wayPoint) {
		waypoints.add(wayPoint);
		//waypoints.add(wayPoint);
		WayPoint.getAllWayPoints().add(wayPoint);

	}

}
