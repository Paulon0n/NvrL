package com.nvrl;
import com.nvrl.NeuralNetCommands.AddNeuron;

public class Add_Neuron extends CommonNeuronExtension{
	private int lastCreatedNeuronID=0;
	
	private static final String IDENT = "Add Neuron";
	public static final String SAVE_ID ="[ANN]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}

	
	public Add_Neuron(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}
	

	public Add_Neuron(){
		super();
		name=IDENT;
	}

	@Override
	public void doInputs(){
		super.doInputs();
	}

	@Override
	public void doTransfer() {
		if (!inputs.isEmpty()){
			Connection c1 = inputs.get(0);
			if (c1.getEnergy()> getThreshold()){
				float x=0,y=0,z=0;
				double t=0;
				if(inputs.size()>=4){
					c1 = inputs.get(1);
					x=(float)c1.getEnergy();
					c1 = inputs.get(2);
					y=(float)c1.getEnergy();
					c1 = inputs.get(3);
					z=(float)c1.getEnergy();
				}
				if(inputs.size()==5){
					c1 = inputs.get(4);
					t=c1.getEnergy();
				}
				if(inputs.size()!=4&&inputs.size()!=5){
					//report error
					sendError(IDENT,"incorrect number of inputs.");
				}else{
					Neuron n = new Neuron();
					AddNeuron cmd = new AddNeuron(n,new Point(x,y,z),true);
					if (!NeuralNet.addCommand(cmd)) {
						//report error
						CommonNeuronExtension.removeNeuronFromMap(n.getID());
						sendError(IDENT,"attempted to place a neuron on a neuron @"+(new Point(x,y,z)).toString());
					}else{
						n.setThreshold(t);
						lastCreatedNeuronID=n.getID();
					}
				}
			}
		}
		// TODO: Implement this method
		//super.doTransfer();
		energy=lastCreatedNeuronID;
		//lastCreatedNeuronID=0;
	}
	
	@Override
	public Add_Neuron copy() {
		Add_Neuron n = new Add_Neuron();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
}
