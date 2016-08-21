package com.nvrl;


import com.nvrl.CommonCommands.*;
import java.util.*;

/**
 * Neuron - a nexus point for connections and capable of many things
 *
 * @author Michael
 */
public class Neuron extends CommonNeuronExtension {
	 
	public static final String SAVE_ID ="[N--]";
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();

	}
	
	public Neuron(String in){
		super(in);
	}
	
	public Neuron(){
		super();
	}
	
}
