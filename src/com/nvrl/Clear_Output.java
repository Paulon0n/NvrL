package com.nvrl;

public class Clear_Output extends CommonNeuronExtension{
	private static final String IDENT = "Clear Console Neuron";
	public static final String SAVE_ID ="[CON]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return "[CON]" + super.toString();
	}


	public Clear_Output(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Clear_Output(){
		super();
		name=IDENT;
	}

	@Override
	public void doTransfer() {
		if (!inputs.isEmpty()){
			Connection c1 = inputs.get(0);
			if (c1.getEnergy()> getThreshold()){
				NvrL.getContext().clearOutput();
			}
		}
	}
	
	@Override
	public Clear_Output copy() {
		Clear_Output n = new Clear_Output();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
}
