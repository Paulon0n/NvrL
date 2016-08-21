package com.nvrl;

import com.nvrl.NeuralNetCommands.*;

public class Delete_Neuron extends CommonNeuronExtension {
	private int id = 0;
	CommonNeuronExtension n ;
	
	private static final String IDENT = "Delete Neuron";
	public static final String SAVE_ID ="[DNN]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}


	public Delete_Neuron(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Delete_Neuron(){
		super();
		name=IDENT;
	}


	@Override
	public void doInputs() {
	// TODO: Implement this method
		super.doInputs();
		}
		
	@Override
	public void doTransfer() {
		//super.doTransfer();
		if (!inputs.isEmpty()){
			if(inputs.size()==2){
				Connection c1 = inputs.get(0);
				if (c1.getEnergy()> getThreshold()){

					c1 = inputs.get(1);
					id=(int)c1.getEnergy();
					n = CommonNeuronExtension.getNeuronFromMap(id);
					if(n!=null){
						//report error
						RemoveNeuron cmd = new RemoveNeuron(n);
						NeuralNet.addCommand(cmd);
					}else{
						sendError(IDENT,"received invalid neuron id: "+id);
						
					}
				}

			}else{
				//report error
				sendError(IDENT,"incorrect number of inputs.");
			}
		}
	
	}
	
	@Override
	public Delete_Neuron copy() {
		Delete_Neuron n = new Delete_Neuron();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
}
