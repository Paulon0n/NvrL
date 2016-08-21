package com.nvrl.CommonCommands;

import com.nvrl.Cluster;
import com.nvrl.Command;
import java.util.ArrayList;

/**
 * @author Michael
 *
 */
public class AddClusterContainer implements Command {

	private final ArrayList<Cluster> alc;

	/**
	 * @param alc
	 */
	public AddClusterContainer(ArrayList<Cluster> alc) {
		this.alc = alc;
	}

	@Override
	public boolean unexecute() {
		for (Cluster c : alc) {
			c.Detach();
		}
		return true;
	}

	@Override
	public boolean execute() {
		for (Cluster c : alc) {
			c.Attach();
		}
		return true;
	}

}
