package com.nvrl;
import android.view.accessibility.*;

public class Move_Neuron extends CommonNeuronExtension{
	
	private static final String IDENT = "Move Neuron";
	public static final String SAVE_ID ="[MNN]";

	public String getIdentity(){
		return IDENT;
	}

	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}


	public Move_Neuron(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Move_Neuron(){
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
			if(inputs.size()==5){

				Connection c = inputs.get(0);
				if (c.getEnergy()> getThreshold()){
					Integer x=0,y=0,z=0,id=0;
					c = inputs.get(1);
					id=(int)c.getEnergy();
					c = inputs.get(2);
					x=(int)c.getEnergy();
					c = inputs.get(3);
					y=(int)c.getEnergy();
					c = inputs.get(4);
					z=(int)c.getEnergy();
					if(id>0){
						CommonNeuronExtension cn = CommonNeuronExtension.getNeuronFromMap(id);
						if(cn!=null){
							Point pl=new Point(x,y,z);
							if (NeuralNet.getSelected().isPlaceableNeuron(pl)) {
								cn.setPos(pl);
							}else{
								sendError(IDENT,"attempted to place a neuron on a neuron @"+(new Point(x,y,z)).toString());
							}
						}else{
							//error
							sendError(IDENT,"received invalid neuron id: "+id);
						}
					}
					
				}
			}else{
				//report error
				sendError(IDENT,"incorrect number of inputs.");
			}
		}
	}

	@Override
	public Move_Neuron copy() {
		Move_Neuron n = new Move_Neuron();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
	
}
