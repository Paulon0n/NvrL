package com.nvrl;

public class Text_Output extends CommonNeuronExtension{
	
	private static final String IDENT = "Text Output Neuron";
	public static final String SAVE_ID ="[TON]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}
	

	public Text_Output(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Text_Output(){
		super();
		name=IDENT;
	}
	
	
	static String out="";
	
	@Override
	public void doTransfer() {
		String x="";
		boolean print = false;
		if (!inputs.isEmpty()){
			Connection c1 = inputs.get(0);
			if (c1.getEnergy()> getThreshold()){
				for(Connection c : outputs){
				
					if (c.getOutput().isFiring()){
						x+="1";
					}else{
						x+="0";
					}
				}
			
				while(!x.contentEquals("")&&x.substring(0,1).contentEquals("0")){
					x=x.substring(1);
				}
				if(!x.contentEquals("")){
					int charCode = Integer.parseInt(x, 2);
					out+= new Character((char)charCode).toString();
				}else{ print =true;}
			}
		}
		if(print&&!out.contentEquals("")){
			NvrL con = NvrL.getContext();
			con.appendTextAndScroll(out);
			out="";
			print=false;
		}
	}
	
	@Override
	public Text_Output copy() {
		Text_Output n = new Text_Output();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
}
