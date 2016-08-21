/*
 * Project name:
 * Author: Michael Camacho
 * Date created:
 * Class: CSC110A
 * Description:
 */
package com.nvrl;
import com.nvrl.*;
import com.nvrl.NeuralNetCommands.*;

import static com.nvrl.EditorUi.setCopySelected;
import static com.nvrl.EditorUi.setMoveSelected;
import static com.nvrl.EditorUi.setSelectionProperties;
import static com.nvrl.EditorUi.setSelectionConnectionProperties;
import com.nvrl.CommonCommands.*;

public enum UI {
	NeuronAdd(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Neuron newNeuron = new Neuron();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast("Neuron Created.");
				Connection.setSelected(null);
			}
			return true;
		}
		
		
	},
	NeuronMove(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronPlace(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronCopy(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}
		
		
	},
	NeuronPaste(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronName(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronEnergy(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronThreshold(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronInputs(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronOutputs(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronDelete(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	NeuronClamp(){

		@Override
		public boolean execute()
		{
			CommonNeuronExtension sn = Neuron.getSelected();
			sn.setClamp(!sn.isClamp());
			return true;
		}
		
		
	},
	ConnectionAdd(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ConnectionType(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ConnectionWeight(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ConnectionDelete(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	WayPointAdd(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	WayPointMove(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	WayPointDelete(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	LayerUp(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	LayerGoto(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	LayerDown(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	Undo(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	Redo(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ProjectNew(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ProjectOpen(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ProjectSave(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	ProjectPreferences(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	AppExit(){

		@Override
		public boolean execute()
		{
			// TODO: Implement this method
			return false;
		}

		
	},
	SelectionDeselect(){

		@Override
		public boolean execute()
		{
			NvrLView.setSelecting(false);
			NvrLView.setDrawselect(false);
			Neuron.getSelection().clear();
			Neuron.getAbsoluteSelection().clear();
			WayPoint.getSelection().clear();
			Cluster.getSelection().clear();
			Connection.getSelection().clear();
			setCopySelected(false);
			setMoveSelected( false);
			setSelectionProperties (false);
			setSelectionConnectionProperties(false);
			return true;
		}
		
		
	},
	TextInput_Add(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Text_Input newNeuron = new Text_Input();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				{}Connection.setSelected(null);
			}
			return true;
		}


	},
	TextOutput_Add(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Text_Output newNeuron = new Text_Output();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	ClearOutput_Add(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Clear_Output newNeuron = new Clear_Output();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	Add_Neuron(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Add_Neuron newNeuron = new Add_Neuron();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	Copy_Neuron(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Copy_Neuron newNeuron = new Copy_Neuron();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	Center_View_Neuron(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Center_View newNeuron = new Center_View();
		
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	Move_Neuron(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Move_Neuron newNeuron = new Move_Neuron();
			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	},
	Delete_Neuron(){

		@Override
		public boolean execute()
		{
			//UIaction(null, EditorUi.Selection_Deselect);
			SelectionDeselect.execute();
			Delete_Neuron newNeuron = new Delete_Neuron();

			Point pnt = Grid
				.ScreenToGrid(DisplayEnvironment.getTouch())
				.setZ(DisplayEnvironment.getActiveLayer())
				.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()));
			AddNeuron cmd = new AddNeuron(newNeuron, pnt,false);
			if (!NeuralNet.addCommand(cmd)) {
				toast("A neuron already exists here!");
			} else {
				Neuron.setSelected(newNeuron);
				toast(newNeuron.getIdentity() +" Created.");
				Connection.setSelected(null);
			}
			return true;
		}
	}
	;
	
	private UI(){
		
	}
	
	public abstract boolean execute();
	
	
	public static void toast(String msg) {
		NvrL.getToaster().setText(msg);
		NvrL.getToaster().show();
	}
}
