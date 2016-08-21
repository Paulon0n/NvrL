package com.nvrl;

import com.nvrl.CommonCommands.AddClusterContainer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Used to contain a cluster for easy save and input into project
 *
 * @author Michael
 * @version 1.0
 */
public class ClusterContainer extends CommonExtensions {

	private static ArrayList<Cluster> clusters;
	private static ArrayList<CommonNeuronExtension> neurons;
	private static final ArrayList<CommonNeuronExtension> ccneurons = new ArrayList<CommonNeuronExtension>();
	private static final ArrayList<CommonNeuronExtension> oldneurons = new ArrayList<CommonNeuronExtension>();
	private static final ArrayList<Connection> oldInputConnections = new ArrayList<Connection>();
	private static Point offset;
	private boolean locked;
	private int id;

	private void onCreate(){
		id=hashCode();
		clusters = new ArrayList<Cluster>();
		neurons = new ArrayList<CommonNeuronExtension>();
		
	}
	/**
	 * Create new ClusterContainer and initialize cluster ArrayLists
	 */
	public ClusterContainer() {
		onCreate();
	}

	/**
	 * Create a ClusterContainer from @param c
	 *
	 * @param name - String to name or describe the cluster container
	 * @param c - A cluster the container will be created from.
	 */
	public ClusterContainer(String name, Cluster c) {
		// name the cluster container
		this.name = name;
		
		onCreate();
		// clear temp list to hold source neurons
		oldneurons.clear();
		// clear temp list to hold copied neurons
		ccneurons.clear();
		// clear temp list of input source connections
		oldInputConnections.clear();
		// navigate to the top of the cluster stack
		while (c.SubParent != null) {
			c = c.SubParent;
		}

		// set the offset for cluster placement
		offset = c.getPos();
		// create a copy of the bottom cluster

		Cluster subP = c.copy(c.getPos().sub(offset));
		// add cluster to the containers cluster list
		this.clusters.add(subP);
		// add all source neurons to temp list
		oldneurons.addAll(c.getNeurons());
		// add all copied neurons to temp list
		ccneurons.addAll(subP.getNeurons());

		// recursively add sub clusters
		constructContainer(c, subP);

		/* remove connections not part of the contained neurons */
		// create an iterator for input connections
		Iterator<Connection> ic = oldInputConnections.iterator();
		while (ic.hasNext()) {
			Connection tc = ic.next();
			// find complete connection, if not then remove
			if (!oldneurons.contains(tc.getInput())) {
				ic.remove();
			}
		}

		// copy connections and waypoints
		for (Connection oc : oldInputConnections) {
			// get relative destination neuron
			CommonNeuronExtension dst = ccneurons.get(oldneurons.indexOf(oc.getOutput()));
			// get relative source neuron
			CommonNeuronExtension src = ccneurons.get(oldneurons.indexOf(oc.getInput()));
			// Create a copy of the connection
			Connection nc = new Connection(src, dst, oc.getWeight(),
				oc.getType());
			// Create copy of waypoints associated with the connection
			if (!oc.getWayPoints().isEmpty()) {
				for (WayPoint wp : oc.getWayPoints()) {
					wp.copy(nc);
				}
			}
		}
		// add all assocciated neurons to container

		this.neurons.addAll(ccneurons);
	}

	// recursively do all sub clusters
	private void constructContainer(Cluster c, Cluster subP) {
		// add source connections to old connections list
		for (CommonNeuronExtension n : c.getNeurons()) {
			oldInputConnections.addAll(n.getInputs());
		}
		// do each sub cluster
		for (Cluster old : c.SubCluster) {
			// copy the cluster
			Cluster nu = old.copy(old.getPos().sub(offset));
			// associate the copy to the parent copy
			nu.SubParent = subP;
			// add souce neurons of the cluster
			oldneurons.addAll(old.getNeurons());
			// add copied neurons of the copied cluster
			ccneurons.addAll(nu.getNeurons());
			// add cluster to the cluster container
			this.clusters.add(nu);
			// recursivly do the same for the clusters sub clusters
			constructContainer(old, nu);
		}
	}

	/**
	 * @param locked
	 */
	public void setProtected(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return state
	 */
	public boolean isProtected() {
		return locked;
	}

	/**
	 * Add cluster container to the selected neural net project
	 *
	 * @param p point to place the cluster container
	 * @return True if successful
	 */
	public boolean addToNeuralNet(Point p) {
		/*
		 * Make a copy of clusters ArrayList and then add to the selected
		 * NeuralNet Clusters ArrayList
		 */
		if (!NeuralNet.testPlaceableNeurons(neurons, p)) {
			return false;
		}
		ArrayList<Cluster> tc = new ArrayList<Cluster>();
		ArrayList<CommonNeuronExtension> ta = new ArrayList<CommonNeuronExtension>();
		ArrayList<Connection> tnc = new ArrayList<Connection>();
		for (CommonNeuronExtension n : neurons) {
			tnc.addAll(n.getInputs());
		}
		for (Cluster c : clusters) {
			Cluster nc = c.copy(c.getPos().add(p));
			// nc.Attach();
			tc.add(nc);
			ta.addAll(nc.getNeurons());
		}
		Connection.CloneAll(neurons, ta);

		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).SubParent != null) {
				tc.get(i).SubParent = tc
					.get(clusters.indexOf(clusters.get(i).SubParent));

				tc.get(i).SubParent.SubCluster.add(tc.get(i));
			}
		}
		Command cmd = new AddClusterContainer(tc);
		NeuralNet.addCommand(cmd);
		return true;
	}

	/**
	 * Convert a ClusterContainer into a data String
	 *
	 * @return DataString
	 */
	public static String dump() {
		String s = NvrL.getBUILD() +"\n";
		for (ClusterContainer clusterContainer : FileIO
			.getClusterContainerlist()) {
			s += "[+]" + clusterContainer.toString()
				+ "[-]\n";
		}
		return s;
	}

	@Override
	public String toString() {
		String s = ""+id+"&";
		s += name + "&";
		s += (locked ? "true" : "false") + "&\n";
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
		return s;
	}

	/**
	 * @param in
	 * @return data string for this ClusterContainer
	 */
	public static boolean load(String in) {
		ClusterContainer cc = new ClusterContainer();
		int i = 1;
		//if(!NvrL.isUpdatedBuild()){}
		
		//if(NvrL.getLoadBuild()>.0056){}
		cc.id = Integer.parseInt(FileIO.getPart(in, i++));
		cc.name = FileIO.getPart(in, i++);
		cc.locked = Boolean.parseBoolean(FileIO.getPart(in, i++));
		while (!(in = FileIO.readLine())
			.contentEquals("[-]")) {

			if (in.startsWith("[N--]")) {
				in = in.substring("[N--]".length());
				cc.neurons.add(new Neuron(in));
			}else if (in.startsWith("[TN]")) {
				in = in.substring("[TN]".length());
				cc.neurons.add(new Text_Input(in));
			} else if (in.startsWith("[C]")) {
				in = in.substring("[C]".length());
				cc.clusters.add(new Cluster(in, cc.neurons));
			} else if (in.startsWith("[c]")) {
				in = in.substring("[c]".length());
				(new Connection(in, cc.neurons)).connect();
			} else if (in.startsWith("[S]")) {
				in = in.substring("[S]".length());
				Cluster.assignSubs(in, cc.clusters);
			}
		}
		FileIO.getClusterContainerlist().add(cc);
		return true;
	}
}
