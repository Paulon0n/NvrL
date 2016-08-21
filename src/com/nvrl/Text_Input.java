package com.nvrl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Text_Input extends CommonNeuronExtension{
	private static final String IDENT = "Text Input Neuron";
	
	public static final String SAVE_ID ="[TIN]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	public static final List<Integer> binaryDecoder = new ArrayList<Integer>();
	
	public static void dumpInput(String t){
		byte[] bytes = null;
		try {
			bytes = t.getBytes("UTF-8");
			for (int i = 0; i < t.length(); i++) {
				String oneByte =Integer.toBinaryString(bytes[i]);
				
				for(int j = oneByte.length();j<8;j++){
					oneByte = "0"+oneByte;
				}
				
				String x1="";
				for(int j = 0; j<oneByte.length();j++){
					int x = Integer.parseInt(oneByte.substring(j,j+1));
					x1+=x+"";
					binaryDecoder.add(x);
				}
				//NvrL.getContext().appendTextAndScroll(x1);	
			}
		}
		catch (UnsupportedEncodingException e) {}
		
	}
	
	
	public Text_Input(String name, float threshold, float energy, Point p){
		super(name,threshold,energy,p);
	}
	
	public Text_Input(){
		super();
		name=IDENT;
	}


	@Override
	public void doTransfer() {
		if (!inputs.isEmpty()){
			Connection c = inputs.get(0);
			if (c.getEnergy()> getThreshold()){
				//NvrL.getContext().appendTextAndScroll("good "+outputs.size());
				for(int i =0;i<outputs.size();i++){
					Connection d = outputs.get(i);
					if(!binaryDecoder.isEmpty()){
						d.getOutput().setEnergy(binaryDecoder.get(0));
						binaryDecoder.remove(0);
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
		
	}

	/**
	 * construct a neuron from a data string (to load)
	 *
	 * @param load
	 */
	
	public Text_Input(String load) {
		super(load);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}

	@Override
	public Text_Input copy() {
		Text_Input n = new Text_Input();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		n.setClamp(clamp);
		n.setMaxClamp(maxClamp);
		n.setMinClamp(minClamp);
		return n;
	}
}
