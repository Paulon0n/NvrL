package com.nvrl;

import com.nvrl.CommonCommands.AddGroup;
import java.util.ArrayList;

/**
 * A group is a collection of Neurons and Connections. The function of
 * a group is to save and place and is quicker then manually
 * constructing it or search and copy paste
 *
 * @author Michael
 */
public class Group extends CommonExtensions {

	private ArrayList<CommonNeuronExtension> neurons;
	private static int count = 0;
	private boolean protect = false;

	/**
	 * A protected Group cannot be deleted/removed from the Group
	 * selector
	 *
	 * @return boolea
	 */
	public boolean isProtected() {
		return protect;
	}

	/**
	 * Construct a Group
	 *
	 * @param name
	 * @param sn
	 */
	public Group(String name, ArrayList<CommonNeuronExtension> sn) {
		count++;
		if (name.contentEquals("")) {
			name = "Group#: " + count;
		}
		this.name = name;
		neurons = new ArrayList<CommonNeuronExtension>();
		// get top left neuron position
		Point topleft = new Point(9999999, 9999999, 9999999);
		Point bottomright = new Point(-9999999, -9999999, -9999999);
		for (CommonNeuronExtension n : sn) {
			if (n.getAbsolutePos().X < topleft.X) {
				topleft.X = n.getAbsolutePos().X;
			}
			if (n.getAbsolutePos().Y < topleft.Y) {
				topleft.Y = n.getAbsolutePos().Y;
			}
			if (n.getAbsolutePos().Z < topleft.Z) {
				topleft.Z = n.getAbsolutePos().Z;
			}
			if (n.getAbsolutePos().X > bottomright.X) {
				bottomright.X = n.getAbsolutePos().X;
			}
			if (n.getAbsolutePos().Y > bottomright.Y) {
				bottomright.Y = n.getAbsolutePos().Y;
			}
			if (n.getAbsolutePos().Z > bottomright.Z) {
				bottomright.Z = n.getAbsolutePos().Z;
			}
		}

		Size = bottomright.sub(topleft);

		// copy selected neurons and contained connections
		for (CommonNeuronExtension n : sn) {
			CommonNeuronExtension n2 = n.copy();
			n2.setPos(n.getAbsolutePos().sub(topleft));
			neurons.add(n2);
		}
		for (CommonNeuronExtension n : sn) {
			// copy contained connections
			for (Connection c : n.getInputs()) {
				if (sn.indexOf(c.getOutput()) != -1
					&& sn.indexOf(c.getInput()) != -1) {
					CommonNeuronExtension dst = neurons.get(sn.indexOf(c.getOutput()));
					CommonNeuronExtension src = neurons.get(sn.indexOf(c.getInput()));
					Connection t = new Connection(src, dst, c.getWeight(),
						c.getType());
					t.connect();
					if (!c.getWayPoints().isEmpty()) {
						for (WayPoint wp : c.getWayPoints()) {
							wp.copy(t,topleft.inv());
						}
					}
				}
			}
		}

	}

	/**
	 * Construct a Group
	 */
	public Group() {
		count++;
		name = "Group#: " + count;
		neurons = new ArrayList<CommonNeuronExtension>();
		FileIO.getGrouplist().add(this);
	}

	/**
	 * Construct a temporary Group
	 *
	 * @return Group
	 */
	public static Group tmpGroup() {
		Group g = new Group();
		FileIO.getGrouplist().remove(g);
		count--;
		g.name = "";
		return g;
	}

	/**
	 * Get the size of the Group in a data string
	 *
	 * @return String
	 */
	public String getStringSize() {
		return "[ " + ((int) Size.X + 1) + " x " + ((int) Size.Y + 1) + " x "
			+ ((int) Size.Z + 1) + " ]";
	}

	/**
	 * Dumps all Groups into a data string
	 *
	 * @return String
	 */
	public static String dump() {
		String d = ""+NvrL.getBUILD();
		for (Group g : FileIO.getGrouplist()) {
			d += "[+]" + g.toString() + "[-]\n";
		}
		return d;
	}

	/**
	 * Get a data string of the Group's properties
	 *
	 * @return data String
	 */
	@Override
	public String toString() {
		String s = name + "&" + protect + "&" + Size.X + "&" + Size.Y + "&"
			+ Size.Z + "&\n";
		for (CommonNeuronExtension n : neurons) {
			s += n.toString();
		}
		for (CommonNeuronExtension n : neurons) {
			for (Connection c : n.getInputs()) {
				s += c.toString(neurons);
			}
		}
		return s;
	}

	/**
	 * Construct a Group from a data string
	 *
	 * @param data
	 * @return boolean
	 */
	public static boolean load(String data) {

		final String gn = "[N]", gc = "[c]";
		Group g = new Group();

		g.name = FileIO.getPart(data, 1);
		g.protect = Boolean.parseBoolean(FileIO.getPart(data, 2));
		float x = Float.parseFloat(FileIO.getPart(data, 3));
		float y = Float.parseFloat(FileIO.getPart(data, 4));
		float z = Float.parseFloat(FileIO.getPart(data, 5));

		g.Size = new Point(x, y, z);
		while (!(data = FileIO.readLine()).contentEquals("[-]")) {
			if (data.startsWith(gn)) {
				data = data.substring(gn.length());
				g.neurons.add(new CommonNeuronExtension(data));
			}
			if (data.startsWith(gc)) {
				data = data.substring(gc.length());
				new Connection(data, g.neurons).connect();
			}
		}
		return true;
	}

	/**
	 * Attempt to place a Group at point p into the active project nn
	 *
	 * @param p
	 * @return boolean success
	 */
	public boolean addGroupToNeuralNet(Point p) {

		if (!NeuralNet.testPlaceableNeurons(neurons, p)) {
			return false;
		}

		ArrayList<CommonNeuronExtension> taln = new ArrayList<CommonNeuronExtension>();
		for (CommonNeuronExtension n : neurons) {
			taln.add(n.copy(n.getPos().add(p)));
		}
		Connection.CloneAll(neurons, taln,p);
		Command cmd = new AddGroup(taln);
		NeuralNet.addCommand(cmd);
		return true;
	}
}
