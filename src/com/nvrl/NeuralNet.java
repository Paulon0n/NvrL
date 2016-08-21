package com.nvrl;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The hub of the NeuralNet Language
 *
 * @author Michael
 */
public class NeuralNet {
	
	public static final String SAVE_ID ="[+]";

	
	/**
	 *
	 */
	public final CommandManager comManager = new CommandManager();

	/**
	 * The selected neural net (useful for multi project development)
	 */
	private static NeuralNet selected = null;

	/**
	 * Contains the list of neurons for this Neural Net
	 */
	public ArrayList<CommonNeuronExtension> neurons = new ArrayList<CommonNeuronExtension>();

	/**
	 * Contains the list of Clusters for this Neural Net
	 */
	public ArrayList<Cluster> clusters = new ArrayList<Cluster>();

	public NeuralNet(){
		id = hashCode();
		Collections.synchronizedList(neurons);
	}
	public NeuralNet(String in){
		id = Integer.parseInt(in);
		Collections.synchronizedList(neurons);
	}
	/**
	 * Sets the current NeuralNet that is to be worked on
	 *
	 * @param selected
	 */
	public static void setSelected(NeuralNet selected) {
		NeuralNet.selected = selected;
	}

	/**
	 * @param cmd
	 * @return boolean
	 */
	public static boolean addCommand(Command cmd) {
		NvrL.getContext().invalidateOptionsMenu();
		return getSelected().comManager.addCommand(cmd);
	}

	/**
	 * Get the active NeuralNet
	 *
	 * @return NeuralNet
	 */
	public static NeuralNet getSelected() {
		return selected;
	}

	/**
	 * Get an ArrayList of neurons that are not in a cluster
	 *
	 * @return ArrayList<Neuron> - ArrayList of Neurons
	 */
	public ArrayList<CommonNeuronExtension> getNormalneurons() {

		ArrayList<CommonNeuronExtension> temp = new ArrayList<CommonNeuronExtension>();

		for (CommonNeuronExtension n : neurons) {
			if (!n.isInCluster()) {
				temp.add(n);
			}
		}
		return temp;
	}

	/**
	 * Get an ArrayList of neurons that are in a cluster
	 *
	 * @param c
	 * @return ArrayList<Neuron>
	 */
	public ArrayList<CommonNeuronExtension> getClusterneurons(Cluster c) {
		ArrayList<CommonNeuronExtension> t = new ArrayList<CommonNeuronExtension>();

		for (CommonNeuronExtension n : c.getNeurons()) {
			t.add(n);
		}

		return t;
	}

	// Convert Neural Net into a string for saving
	@Override
	public String toString() {
		String s = "[+]"+id+"&\n";

		for (CommonNeuronExtension n : neurons) {
			s += n.toString();
		}

		for (Cluster c : clusters) {
			s += c.toString(neurons);
		}

		for (CommonNeuronExtension n : neurons) {
			for (Connection c : n.getInputs()) {
				s += c.toString(neurons);
			}
		}

		for (Cluster c : clusters) {
			s += c.toSubsString(clusters);
		}

		return s + "[-]\n";
	}

	/**
	 * Set Neural Net's neurons to another Neuron List
	 *
	 * @param neuronlist - A list of neurons
	 */
	public void setNeuronlist(ArrayList<CommonNeuronExtension> neuronlist) {
		neurons = neuronlist;
	}

	/**
	 * Get the list of Neurons from Neural Net
	 *
	 * @return Neurons - The list of neurons associated with the
	 * NeuralNet
	 */
	public ArrayList<CommonNeuronExtension> getNeurons() {
		return neurons;
	}

	/**
	 * Get the list of Clusters from Neural Net
	 *
	 * @return ArrayList<Cluster>
	 */
	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

	/**
	 * Add a neuron to neurons List
	 *
	 * @param n - The Neuron to add to the NeuralNet's Neuron list
	 */
	/*
	 * public void addneuron(Neuron n) { neurons.add(n); }//
	 */
	/**
	 * Add a neuron to neurons list with positioning information
	 *
	 * @param n
	 * @param p
	 * @return boolean
	 */
	

	public boolean addneuron(CommonNeuronExtension n, Point p,boolean k) {
		if (p != null) {
			Cluster AddInCluster = null;

			for (Cluster c : clusters) {
				if (p.isInRect(c.Pos, c.getAbsoluteSize())) {
					AddInCluster = c;
				}
			}

			for (CommonNeuronExtension n2 : neurons) {
				if (n2.getAbsolutePos().equal(p)) {
					return false;
				}
			}

			n.setPos((AddInCluster != null) ? p.sub(AddInCluster.Pos) : p);
			n.setCluster(AddInCluster);

			if (AddInCluster != null) {
				AddInCluster.getNeurons().add(n);
			}
		}
		if(!k)Neuron.setSelected(n);
		return neurons.add(n);
	}
	/**
	 * Add a cluster to Clusters list
	 *
	 * @param c
	 * @return boolean
	 */
	public boolean addCluster(Cluster c) {
		for (CommonNeuronExtension n : neurons) {
			if (!n.isInCluster()
				&& n.getPos().isInRect(c.Pos, c.getAbsoluteSize())) {
				n.setCluster(c);
				n.Pos = n.Pos.sub(c.Pos);
			}
		}
		return clusters.add(c);
	}

	public boolean addCluster2(Cluster c) {
		return clusters.add(c);
	}
	/**
	 * Get an index of a cluster in Clusters list
	 *
	 * @param c
	 * @return int
	 */
	public int getClusterIndex(Cluster c) {
		return clusters.indexOf(c);
	}

	/**
	 * is a neuron place'able at said point
	 *
	 * @param p
	 * @return boolean
	 */
	public boolean isPlaceableNeuron(Point p) {
		for (CommonNeuronExtension n2 : neurons) {
			if ((n2.isInCluster() ? n2.Pos.add(n2.getCluster().Pos) : n2.Pos)
				.equal(p)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Remove neuron
	 *
	 * @param n
	 * @return boolean
	 */
	public boolean removeneuron(CommonNeuronExtension n) {
		if(n instanceof Text_Input){
			NvrL.disableTextDrawer();
		}
		if (Neuron.getSelected() == n) {
			Neuron.setSelected(null);
		}
		if (Neuron.getPreviousSelected() == n) {
			Neuron.setPreviousSelected(null);
		}
		return neurons.remove(n);

	}

	public void finddrawables(){
		ArrayList<CommonNeuronExtension> no = getNormalneurons();

		Point vo = DisplayEnvironment.getViewOffset();
		//68*68 grid
		Point g = Grid.ScreenToGrid(vo);
		DisplayEnvironment.drawableNeurons.clear();
		
		for (CommonNeuronExtension n : no) {
		
			if((n.Pos.X-g.X<=68 && n.Pos.X-g.X>=-68)&& (n.Pos.Y-g.Y<=68 && n.Pos.Y-g.Y>=-68)){
				DisplayEnvironment.drawableNeurons.add(n);
			}

		}
	}
	/**
	 * Do a simulation step
	 */
	public static ArrayList<CommonNeuronExtension > drawableneurons=new ArrayList<CommonNeuronExtension>();
	public void StepUpdate() {
		ArrayList<CommonNeuronExtension> no = new ArrayList<CommonNeuronExtension>( getNormalneurons());

		Point vo = DisplayEnvironment.getViewOffset();
		//68*68 grid
		Point g = Grid.ScreenToGrid(vo);
		DisplayEnvironment.drawableNeurons.clear();
		for (CommonNeuronExtension n : no) {
			n.doInputs();
			if((n.Pos.X-g.X<=68 && n.Pos.X-g.X>=-68)&& (n.Pos.Y-g.Y<=68 && n.Pos.Y-g.Y>=-68)){
				DisplayEnvironment.drawableNeurons.add(n);
			}
			
		}

		// do cluster processing
		for (Cluster c : clusters) {
			if (c.SubParent == null) {
				if (c.oneStep) {
					for (int i = 0; i < c.oneStepCount; i++) {
						c.SimStep();
					}
				} else {
					c.SimStep();
				}
			}
		}

		for (CommonNeuronExtension n : no) {
			n.setEnergy(0);
		}

		for (CommonNeuronExtension n : no) {
			n.doTransfer();
			
		}
	}

	/**
	 * Do a simulation step for Clusters only
	 */
	public void ClusterStepUpdate() {
		for (Cluster c : clusters) {
			if (c.SubParent == null) {
				c.SimStep();
			}
		}
	}

	/**
	 * Copy the NeuralNet - does not include clusters
	 *
	 * @return NeuralNet
	 */
	public NeuralNet copy() {
		NeuralNet nn = new NeuralNet();

		for (CommonNeuronExtension n : neurons) {
			nn.neurons.add(n.copy());
		}

		for (CommonNeuronExtension n : neurons) {
			if (!n.getInputs().isEmpty()) {
				for (Connection c : n.getInputs()) {
					CommonNeuronExtension srcn;
					CommonNeuronExtension destn;
					int cntgoal = neurons.indexOf(c.getInput());

					srcn = nn.neurons.get(cntgoal);
					cntgoal = neurons.indexOf(c.getOutput());
					destn = nn.neurons.get(cntgoal);
					Connection connection = new Connection(srcn, destn,
						c.getWeight(), c.getType());
					connection.connect();
				}
			}
		}

		return nn;
	}

	// /**
	// * Get inputs from hardware or other
	// *
	// * @return
	// */
	// public ArrayList<Neuron> getGameInputs() {
	// ArrayList<Neuron> gil = new ArrayList<Neuron>();
	//
	// for (Neuron n : neurons) {
	// if (n.getIntTypeData(0) == Neuron.TypeInput) {
	// gil.add(n);
	// }
	// }
	//
	// return gil;
	// }
	// /**
	// * Get outputs to hardware or other
	// *
	// * @return
	// */
	// public ArrayList<Neuron> getGameOutputs() {
	// ArrayList<Neuron> gol = new ArrayList<Neuron>();
	//
	// for (Neuron n : neurons) {
	// if (n.getIntTypeData(0) == Neuron.TypeOutput) {
	// gol.add(n);
	// }
	// }
	//
	// return gol;
	// }
	// /**
	// * Get Growth Neurons
	// *
	// * @return
	// */
	// public ArrayList<Neuron> getGrowthneurons() {
	// ArrayList<Neuron> gol = new ArrayList<Neuron>();
	//
	// for (Neuron n : neurons) {
	// if (n.isTypeCategory(Neuron.TypeGrowth)) {
	// gol.add(n);
	// }
	// }
	//
	// return gol;
	// }
	/**
	 * Get index position of Neuron n
	 *
	 * @param n
	 * @return int
	 */
	public int getNeuronListPosition(Neuron n) {
		return neurons.indexOf(n);
	}

	/**
	 * @param ns
	 * @param p
	 * @return boolean
	 */
	public static boolean testPlaceableNeurons(ArrayList<CommonNeuronExtension> ns, Point p) {
		for (CommonNeuronExtension n : ns) {
			Point tpos = n.getAbsolutePos().add(p);
			for (CommonNeuronExtension n2 : NeuralNet.getSelected().neurons) {
				if (tpos.equal(n2.getAbsolutePos())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 *
	 */
	public static void undo() {
		NeuralNet.getSelected().comManager.undo();
	}

	/**
	 *
	 */
	public static void redo() {
		NeuralNet.getSelected().comManager.redo();
	}
	
	private int id;
	public int getID(){
		return id;
	}
}
