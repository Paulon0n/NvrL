package com.nvrl;

import com.nvrl.NeuralNetCommands.AddNeuron;

public class Copy_Neuron extends CommonNeuronExtension {
	//private int lastCreatedNeuronID=0;
	
	private static final String IDENT = "Copy Neuron";
	public static final String SAVE_ID ="[CNN]";
	
	public String getIdentity(){
		return IDENT;
	}
	
	@Override
	public String toString() {
		return SAVE_ID + super.toString();
	}

	
	public Copy_Neuron(String i){
		super(i);
		if(name.contentEquals("")){
			name=IDENT;
		}
	}


	public Copy_Neuron(){
		super();
		name=IDENT;
	}

	@Override
	public void doInputs() {
		// TODO: Implement this method
		super.doInputs();
	}
	
	
	@Override
	public Copy_Neuron copy() {
		Copy_Neuron n = new Copy_Neuron();
		n.name=name;
		n.setThreshold(thresholdHigh);
		n.setEnergy(energy);
		return n;
	}

	@Override
	public void doTransfer() 
	{

		if (!inputs.isEmpty())
		{
			Connection c1 = inputs.get(0);
			if (c1.getEnergy()> getThreshold())
			{
				float x=0,y=0,z=0;
				int id=0;
				if(inputs.size()!=5)
				{
					//report error
					sendError(IDENT,"incorrect number of inputs.");
				}else{
					c1 = inputs.get(1);
					id=(int)c1.getEnergy();
					c1 = inputs.get(2);
					x=(float)c1.getEnergy();
					c1 = inputs.get(3);
					y=(float)c1.getEnergy();
					c1 = inputs.get(4);
					z=(float)c1.getEnergy();

					if(id!=0)
					{
						CommonNeuronExtension n = CommonNeuronExtension.getNeuronFromMap(id);
						if(n!=null)
						{
							CommonNeuronExtension n2 = n.copy();
							AddNeuron cmd = new AddNeuron(n2,new Point(x,y,z),true);
							if (!NeuralNet.addCommand(cmd))
							{
								//report error
								CommonNeuronExtension.removeNeuronFromMap(n2.getID());
								sendError(IDENT,"attempted to place a neuron on a neuron @"+(new Point(x,y,z)).toString());
							}else{
								n2.setClamp(n.isClamp());
								n2.setMinClamp(n.getMinClamp());
								n2.setMaxClamp(n.getMaxClamp());
								n2.name = n.name;
								energy=n2.getID();

							}
						}else{
							sendError(IDENT,"received invalid neuron id: "+id);

						}
					}
				}
			}
		}
	}
}

