package com.nvrl;

public class Center_View extends CommonNeuronExtension{
	private static final String IDENT = "Center View Neuron";
	public static final String SAVE_ID ="[CVN]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}


	public Center_View(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Center_View(){
		super();
		name=IDENT;
	}

	@Override
	public void doInputs(){
		if (!inputs.isEmpty()){
			if(inputs.size()==4){
				
				Connection c1 = inputs.get(0);
				if (c1.getInput().getEnergy()> getThreshold()){
					Integer x=0,y=0,z=0;
				
					c1 = inputs.get(1);
					x=(int)c1.getInput().getEnergy();
					c1 = inputs.get(2);
					y=(int)c1.getInput().getEnergy();
					c1 = inputs.get(3);
					z=(int)c1.getInput().getEnergy();
					DisplayEnvironment.setViewOffset(x,y,z);
				}
			}else{
				//report error
				sendError(IDENT,"incorrect number of inputs.");
			}
		}
	}

	@Override
	public void doTransfer() {
		//super.doTransfer();
	}

	@Override
	public Center_View copy() {
		Center_View n = new Center_View();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
	
	
}
