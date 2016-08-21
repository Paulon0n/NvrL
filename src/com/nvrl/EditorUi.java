package com.nvrl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import com.nvrl.ClusterCommands.ClusterPaste;
import com.nvrl.ClusterCommands.ClusterPlace;
import com.nvrl.ClusterCommands.ClusterResize;
import com.nvrl.CommonCommands.SetName;
import com.nvrl.CommonCommands.SetPos;
import com.nvrl.ConnectionCommands.AddConnection;
import com.nvrl.ConnectionCommands.ConnectionSelectionSetType;
import com.nvrl.ConnectionCommands.ConnectionSelectionSetWeight;
import com.nvrl.ConnectionCommands.ConnectionSetType;
import com.nvrl.ConnectionCommands.ConnectionSetWeight;
import com.nvrl.ConnectionCommands.DeleteConnection;
import com.nvrl.NeuralNetCommands.AddCluster;
import com.nvrl.NeuralNetCommands.RemoveCluster;
import com.nvrl.NeuralNetCommands.RemoveNeuron;
import com.nvrl.NeuronCommands.NeuronSelectionSetClampMax;
import com.nvrl.NeuronCommands.NeuronSelectionSetClampMin;
import com.nvrl.NeuronCommands.NeuronSelectionSetEnergy;
import com.nvrl.NeuronCommands.NeuronSelectionSetThreshold;
import com.nvrl.NeuronCommands.SetClampMax;
import com.nvrl.NeuronCommands.SetClampMin;
import com.nvrl.NeuronCommands.SetCluster;
import com.nvrl.NeuronCommands.SetEnergy;
import com.nvrl.NeuronCommands.SetThreshold;
import com.nvrl.SelectionCommands.SelectionDelete;
import com.nvrl.SelectionCommands.SelectionPaste;
import com.nvrl.SelectionCommands.SelectionReposition;
import com.nvrl.WayPointCommands.AddWayPoint;
import com.nvrl.WayPointCommands.WayPointDelete;
import com.nvrl.WayPointCommands.WayPointReOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
This needs to be enumerated.
*/
public class EditorUi {
	
	/* edit in vr mode @tm does nothing */
	private static boolean vrEdit = false;
	
	/* designate a speed for simulation */
	private static int NeuralNetUpdateSpeed;

	/* a temporary reference to a Neuron that will be moved */
	private static CommonNeuronExtension NeuronToBeMoved;

	/* a temporary reference for a WayPoint that will be moved */
	private static WayPoint WayPointToBeMoved;

	/* Used to designate if the Neural Net simulation is active */
	private static boolean runnet = false;

	/* place neuron menu option trigger */
	private static boolean placeNeuron = false;

	/* place cluster menu option trigger */
	private static boolean placeCluster = false;

	/* paste cluster menu option trigger */
	private static boolean pasteCluster = false;

	/* designate if the PROPERTIES of a created element auto shows */
	
	private static boolean moveSelected = false;
	private static boolean copySelected = false;
	private static Point topleft;
	private static boolean selectionProperties = false;
	private static boolean selectionConnectionProperties = false;

	public static boolean isCopySelected(){
		return copySelected;
	}
	public static void setCopySelected( boolean arg ){
		copySelected = arg;
	}
	public static boolean isMoveSelected(){
		return moveSelected;
	}
	public static void setMoveSelected( boolean arg ){
		moveSelected = arg;
	}
	public static boolean isSelectionProperties(){
		return selectionProperties;
	}
	public static void setSelectionProperties( boolean arg ){
		selectionProperties = arg;
	}
	public static boolean isSelectionConnectionProperties(){
		return selectionConnectionProperties;
	}
	public static void setSelectionConnectionProperties( boolean arg ){
		selectionConnectionProperties = arg;
	}
	/* UI Action Identifiers */
	/**
	 * Action ID: add a neuron to the selected NeuralNet
	 */
	
	public static final int NEURON_ADD = 1;
	/**
	 * Action ID: move a neuron
	 */
	public static final int NEURON_MOVE = 2;
	/**
	 * Action ID:
	 */
	public static final int NEURON_PROPERTIES = 3;
	/**
	 * Action ID:
	 */
	public static final int NeuronInputs = 4;
	/**
	 * Action ID:
	 */
	public static final int NeuronOutputs = 5;
	/**
	 * Action ID:
	 */
	public static final int NeuronDelete = 6;
	/**
	 * Action ID:
	 */
	public static final int NeuronMenu = 7;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesRoot = 10;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesEditName1 = 11;
	public static final int NeuronPropertiesEditName2 = 1111;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesEditEnergy1 = 1212;
	public static final int NeuronPropertiesEditEnergy2 = 12;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesEditThreshold1 = 1313;
	public static final int NeuronPropertiesEditThreshold2 = 13;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagory = 20;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagoryInput = 21;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagoryOutput = 22;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagoryGrowth = 23;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagoryVirtual = 24;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesTypeCatagoryCluster = 25;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesSelectExtendedField = 30;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesEditExtendedField = 31;
	/**
	 * Action ID:
	 */
	public static final int NeuronPropertiesEditAttachedCluster1 = 3232;
	public static final int NeuronPropertiesEditAttachedCluster2 = 32;
	public static final int NeuronPropertiesEditClamp = 33;
	public static final int NeuronPropertiesEditClampMin1 = 34;
	public static final int NeuronPropertiesEditClampMin2 = 3434;
	public static final int NeuronPropertiesEditClampMax1 = 35;
	public static final int NeuronPropertiesEditClampMax2 = 3535;
	

	/* C o n n e c t i o n A c t i o n I D ' s */
	/**
	 * Action ID:
	 */
	public static final int Connection_Menu = 100;
	/**
	 * Action ID:
	 */
	public static final int Connection_Add = 101;
	/**
	 * Action ID:
	 */
	public static final int Connection_Properties = 102;
	/**
	 * Action ID:
	 */
	public static final int Connection_Delete = 103;
	/**
	 * Action ID:
	 */
	public static final int Connection_PropertiesRoot = 110;
	/**
	 * Action ID:
	 */
	public static final int Connection_PropertiesType = 111;
	/**
	 * Action ID:
	 */
	public static final int Connection_PropertiesWeight = 112;

	/* W a y p o i n t */
	/**
	 * Action ID:
	 */
	public static final int WayPoint_Add = 120;
	/**
	 * Action ID:
	 */
	public static final int WayPoint_Move = 121;
	/**
	 * Action ID:
	 */
	public static final int WayPoint_Place = 122;
	/**
	 * Action ID:
	 */
	public static final int WayPoint_Delete = 123;
	/**
	 * Action ID:
	 */
	public static final int WayPoint_Menu = 124;
	/**
	 * Action ID:
	 */
	public static final int WayPoint_ReOrder = 125;

	/* Layer menu option ID's */
	/**
	 * Action ID:
	 */
	public static final int Layer_Up = 200;
	/**
	 * Action ID:
	 */
	public static final int Layer_GoTo = 201;
	/**
	 * Action ID:
	 */
	public static final int Layer_Down = 202;
	private static final int Layer_GoTo_Complete = 203;

	/**
	 * Action ID:
	 */
	public static final int RootMenuUndo = 298;
	/**
	 * Action ID:
	 */
	public static final int RootMenuRedo = 299;
	/**
	 * Action ID:
	 */
	public static final int RootMenuNewProject = 300;
	/**
	 * Action ID:
	 */
	public static final int RootMenuOpenProject = 310;
	/**
	 * Action ID:
	 */
	public static final int RootMenuSaveProject = 320;
	/**
	 * Action ID:
	 */
	public static final int RootMenuImportNeuralNetwork = 340;

	/**
	 * Action ID:
	 */
	public static final int Preferences = 350;
	/**
	 * Action ID:
	 */
	public static final int PreferenceEdit = 351;
	/**
	 * Action ID:
	 */
	public static final int Preferences_Run_Simulation = 352;
	/**
	 * Action ID:
	 */
	public static final int PREFERENCES_SIMULATION_SPEED = 353;

	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Create1 = 390;
	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Create2 = 391;
	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Place1 = 392;
	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Place2 = 393;
	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Delete1 = 394;
	/**
	 * Action ID:
	 */
	public static final int ClusterContainer_Delete2 = 395;

	/**
	 * Action ID:
	 */
	public static final int Group_Create1 = 400;
	/**
	 * Action ID:
	 */
	public static final int Group_Create2 = 401;
	/**
	 * Action ID:
	 */
	public static final int Group_Place1 = 402;
	/**
	 * Action ID:
	 */
	public static final int Group_Place2 = 403;
	/**
	 * Action ID:
	 */
	public static final int Group_Delete1 = 404;
	/**
	 * Action ID:
	 */
	public static final int Group_Delete2 = 405;

	/**
	 * Action ID:
	 */
	public static final int Selection_Move = 410;
	/**
	 * Action ID:
	 */
	public static final int Selection_Place = 411;
	/**
	 * Action ID:
	 */
	public static final int Selection_Copy = 420;
	/**
	 * Action ID:
	 */
	public static final int Selection_Paste = 421;
	/**
	 * Action ID:
	 */
	public static final int Selection_Deselect = 430;
	/**
	 * Action ID:
	 */
	public static final int Selection_Properties_Menu = 435;
	/**
	 * Action ID:
	 */
	public static final int Selection_Neuron_Properties = 436;
	/**
	 * Action ID:
	 */
	public static final int Selection_Connection_Properties = 437;
	/**
	 * Action ID:
	 */
	public static final int Selection_Delete = 440;
	/**
	 * Action ID:
	 */
	public static final int Selection_Turn_Right = 441;
	/**
	 * Action ID:
	 */
	public static final int Selection_Turn_Left_ = 442;
	/**
	 * Action ID:
	 */
	public static final int Selection_Flip_Vert_ = 443;
	/**
	 * Action ID:
	 */
	public static final int Selection_Flip_Horz_ = 444;

	/**
	 * Action ID:
	 */
	public static final int Cluster_Create1 = 452;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Create2 = 453;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Delete = 455;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Menu = 457;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Selector = 458;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Selected = 459;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Properties = 460;
	/**
	 * Action ID:
	 */
	public static final int Cluster_EditProperty = 461;
	/**
	 * Action ID:
	 */
	public static final int Cluster_ChangeProperty = 462;
	
	public static final int Cluster_SetSelected = 459459;
	/**
	 * Action ID:
	 */
	public static final int Cluster_SimStep = 470;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Resize = 471;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Move = 480;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Place = 481;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Copy = 490;
	/**
	 * Action ID:
	 */
	public static final int Cluster_Paste = 491;
	/**
	 * Action ID:
	 */
	public static final int Cluster_DrawNeurons = 492;

	/**
	 * Action ID:
	 */
	public static final int SimStep = 500;

	/**
	 * Action ID:
	 */
	public static final int ShowErrors = 600;
	
	/**
	 * Action ID:
	 */
	public static final int ShowErrorsAction = 601;
	
	// For UI return values and controll
	/**
	 * Used to pass the list menu index selected
	 */
	public static int UIListElementSelected = 0;

	/**
	 * Used to pass text from an edit text
	 */
	public static String UIEditTextReturn = "";

	/**
	 * Used to identify what extended field was accessed in a neuron
	 */
	public static int ExtendedFieldAccessed = 0;
	// public static Cluster SelectedCluster;

	/**
	 * Used as a temporary reference to a selected cluster
	 */
	public static Cluster ClusterReference = null;

	/**
	 * Used to identify the allowed characters in an edit text
	 */
	public final static int Str = android.text.InputType.TYPE_CLASS_TEXT;
	/**
	 * Used to identify the allowed characters in an edit text
	 */
	public final static int Num = android.text.InputType.TYPE_CLASS_NUMBER;
	/**
	 * Used to identify the allowed characters in an edit text
	 */
	public final static int Neg = android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;
	/**
	 * Used to identify the allowed characters in an edit text
	 */
	public final static int Dec = android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
	/**
	 * Used to identify the allowed characters in an edit text
	 */
	public final static int NotEditable = -1;

	/**
	 * @param placeNeuron
	 */
	public static void setPlaceNeuron(boolean placeNeuron) {
		EditorUi.placeNeuron = placeNeuron;
	}

	/**
	 * @return boolean
	 */
	public static boolean isPlaceNeuron() {
		return placeNeuron;
	}

	/**
	 * Sets a flag that allows the running of simulation
	 *
	 * @param Runnet
	 */
	public static void setRunnet(boolean Runnet) {
		EditorUi.runnet = Runnet;
	}

	/**
	 * Used to identify if the simulation is running
	 *
	 * @return boolean
	 */
	public static boolean isRunnet() {
		return EditorUi.runnet;
	}

	/**
	 * Used to set the speed at which the simulation runs
	 *
	 * @param speed
	 */
	public static void setSpeed(int speed) {
		EditorUi.NeuralNetUpdateSpeed = speed;
	}

	/**
	 * Used to get the speed at which the simulation runs
	 *
	 * @return NeuralNetUpdateSpeed
	 */
	public static int getSpeed() {
		return NeuralNetUpdateSpeed;
	}

	/**
	 * Sets a temporary Neuron reference that will be "moved"
	 *
	 * @param neuronToBeMoved
	 */
	public static void setNeuronToBeMoved(CommonNeuronExtension neuronToBeMoved) {
		EditorUi.NeuronToBeMoved = neuronToBeMoved;
	}

	/**
	 * Get the temporary Neuron reference
	 *
	 * @return NeuronToBeMoved
	 */
	public static CommonNeuronExtension getNeuronToBeMoved() {
		return NeuronToBeMoved;
	}

	/**
	 * Used as the nexus of all U.I. Actions
	 *
	 * @param context
	 * @param action
	 * @return boolean true if successful
	 */
	public static boolean UIaction(Context context, int action) {
		// Ensure that a NeuralNet is selected
		if (NeuralNet.getSelected() != null) {
			NvrLView.setActive(false);
			// create an ArrayList<String> for U.I. list options (lo)
			ArrayList<String> lo = new ArrayList<String>();
			// create an ArrayList<Integer> for U.I. list actions (la)
			ArrayList<Integer> la = new ArrayList<Integer>();
			// create a point holder
			Point tpoint;
			// create a Command holder
			Command cmd;
			Point tl, br, np;
			switch (action) {
				/* N e u r o n M e n u */
				case EditorUi.NeuronMenu:
					lo.add(!placeNeuron ? "Move" : "Place");
					la.add(EditorUi.NEURON_MOVE);
					lo.add("Properties");
					la.add(EditorUi.NEURON_PROPERTIES);
					lo.add("Inputs");
					la.add(EditorUi.NeuronInputs);
					lo.add("Outputs");
					la.add(EditorUi.NeuronOutputs);
					lo.add("Delete");
					la.add(EditorUi.NeuronDelete);
					CreateListDialog(NvrL.getContext(), lo, "Neuron Menu", la,
						0).create().show();
					return true;

				/* A d d N e u r o n */
				case EditorUi.NEURON_ADD:

					return UI.NeuronAdd.execute();

				/* M o v e N e u r o n */
				case EditorUi.NEURON_MOVE:
					UIaction(null, EditorUi.Selection_Deselect);
					if (!placeNeuron) {
						NeuronToBeMoved = Neuron.getSelected();
						if (NeuronToBeMoved != null) {
							placeNeuron = true;
						} else {
							toast("Neuron not selected.");
						}
					} else {
						Point pl = Grid.ScreenToGrid(DisplayEnvironment.getTouch()).add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset())).setZ(DisplayEnvironment.getActiveLayer());
						if (NeuralNet.getSelected().isPlaceableNeuron(pl)) {
							if (NeuronToBeMoved.isInCluster()) {
								NeuronToBeMoved.setPos(NeuronToBeMoved
									.isInCluster() ? pl.sub(NeuronToBeMoved
											.getCluster().Pos) : pl);
								Point cp1 = NeuronToBeMoved.getCluster().getPos();
								Point cp2 = NeuronToBeMoved.getCluster()
									.getAbsoluteSize();
								if (!NeuronToBeMoved.getAbsolutePos().isInRect(cp1,
									cp2)) {
									Point p = cp1.add(cp2);
									Point p0 = new Point(
										NeuronToBeMoved.getAbsolutePos().X < cp1.X ? NeuronToBeMoved
											.getAbsolutePos().X : cp1.X,
										NeuronToBeMoved.getAbsolutePos().Y < cp1.Y ? NeuronToBeMoved
											.getAbsolutePos().Y : cp1.Y);
									Point p1 = new Point(
										NeuronToBeMoved.getAbsolutePos().X > p.X ? NeuronToBeMoved
											.getAbsolutePos().X : p.X,
										NeuronToBeMoved.getAbsolutePos().Y > p.Y ? NeuronToBeMoved
											.getAbsolutePos().Y : p.Y);
									NeuronToBeMoved.getCluster().updateAbsolutePos(
										p0, p1);
									NeuronToBeMoved.updatePos();// <--command is in
									// method
								}
							} else {
								cmd = new SetPos<CommonNeuronExtension>(NeuronToBeMoved, pl);
								NeuralNet.addCommand(cmd);
							}
							NeuronToBeMoved = null;
							placeNeuron = false;
						} else {
							toast("Neuron exists at this location.");
							NeuronToBeMoved = null;
							placeNeuron = false;
						}
					}
					NvrL.getContext().invalidateOptionsMenu();
					return true;

				/* N e u r o n P r o p e r t i e s M e n u */
				case EditorUi.NEURON_PROPERTIES:
					if (Neuron.getSelected() != null || selectionProperties) {
						if (!selectionProperties && Neuron.getSelected() != null) {
							lo.add("Neuron Name:     "
								+ Neuron.getSelected().getName());
						}
						lo.add("Energy:     "
							+ (selectionProperties ? ""
								: (Neuron.getSelected() == null ? ""
									: Neuron.getSelected().getEnergy())));
						lo.add("Threshold:     "
							+ (selectionProperties ? ""
								: (Neuron.getSelected() == null ? ""
									: Neuron.getSelected()
									.getThreshold())));
						lo.add("Randomize Threshold");
						if (!selectionProperties) {
						// lo.add("<Type Selector>     " + (selectionProperties
							// ? "" : (Neuron.getSelected() == null ? "" :
							// Neuron.getSelected().getTypeName())));
							// lo.add("    [Edit Type Extended Fields]");
							lo.add("<Input Editor>");
							lo.add("<Output Editor>");
						}
						lo.add("Attached to cluster: "
							+ (selectionProperties ? ""
								: ((Neuron.getSelected() == null ? ""
									: Neuron.getSelected().getCluster()) != null ? (Neuron
									.getSelected() == null ? ""
										: Neuron.getSelected().getCluster().name)
									: "")));
						
						CreateListDialog(NvrL.getContext(), lo,
							"Neuron Properties Menu", null,
							EditorUi.NeuronPropertiesRoot).create().show();
					} else {
						toast("Select a neuron to view properties.");
					}
					return true;

				/* N e u r o n P r o p e r t i e s R o o t S w i t c h */
				case EditorUi.NeuronPropertiesRoot:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							Intent intent;
							switch (UIListElementSelected) {

								case 0:// edit name
									CreateEditTextDialog(context, "Neuron Name...",
										Str, EditorUi.NeuronPropertiesEditName2,
										Neuron.getSelected().getName());
									break;

								case 1:// edit energy
									CreateEditTextDialog(context, "Neuron Energy...",
										Num | Dec,
										EditorUi.NeuronPropertiesEditEnergy2, Neuron
										.getSelected().getEnergy() + "");
									break;

								case 2:// edit threshold
									CreateEditTextDialog(context,
										"Neuron Threashold...", Num | Dec,
										EditorUi.NeuronPropertiesEditThreshold2,
										Neuron.getSelected().getThreshold() + "");
									break;

								case 3:// set random threshold
									cmd = new SetThreshold(Neuron.getSelected(),
										FileIO.Rand());
									NeuralNet.addCommand(cmd);
									break;

						// case 499:// type editor
								// lo.addAll(Arrays.asList(Neuron.categoryNames));
								// CreateListDialog(EditNeuralNetActivity.getContext(),
								// lo, "Neuron Type Root Menu", null,
								// EditorUi.NeuronPropertiesTypeCatagory).create().show();
								// break;
								// case 599:// edit type extended fields
								// int ef = Neuron.getSelected().getExtendedFields();
								// for (int i = 2; i < (ef + 2); i++) {
								// lo.add("<Field " +
								// Neuron.getSelected().getExtendedFieldName(i - 2) +
								// " >:     " + Neuron.getSelected().getTypeData(i));
								// }
								// CreateListDialog(EditNeuralNetActivity.getContext(),
								// lo, "Extended Field Editor", null,
								// EditorUi.NeuronPropertiesSelectExtendedField).create().show();
								// break;
								case 4:// input editor
									Neuron.outs = false;
									intent = new Intent(context,
										EditInOutActivity.class);
									context.startActivity(intent);
									break;
								case 5:// output editor
									Neuron.outs = true;
									intent = new Intent(context,
										EditInOutActivity.class);
									context.startActivity(intent);
									break;
								case 6:// attach to cluster edit
									if (Neuron.getSelected().isInCluster()) {
										lo.add("Un-attach from cluster");
										if (!Cluster.getSelection().isEmpty()) {
											for (Cluster c : Cluster.getSelection()) {
												lo.add(c.name);
											}
										}
										CreateListDialog(NvrL.getContext(),
											lo,
											"Attach to Cluster...",
											null,
											EditorUi.NeuronPropertiesEditAttachedCluster2)
											.create().show();
									}
									break;
							}
						}
					} else {
						switch (UIListElementSelected) {
							case 0:// group select edit energy
								CreateEditTextDialog(context, "Neuron Energy...", Num
									| Dec, EditorUi.NeuronPropertiesEditEnergy2, "");
								break;
							case 1:// group select edit threshhold
								CreateEditTextDialog(context, "Neuron Threashold...",
									Num | Dec,
									EditorUi.NeuronPropertiesEditThreshold2, "");
								break;
							case 2:// group select set random threshhold
								cmd = new NeuronSelectionSetThreshold(
									Neuron.getAbsoluteSelection());
								NeuralNet.addCommand(cmd);
								selectionProperties = false;
								break;
							case 3:// group select attach to cluster edit
								lo.add("Un-attach from cluster");
								if (!Cluster.getSelection().isEmpty()) {
									for (Cluster c : Cluster.getSelection()) {
										lo.add(c.name);
									}
								}
								CreateListDialog(NvrL.getContext(), lo,
									"Attach to Cluster...", null,
									EditorUi.NeuronPropertiesEditAttachedCluster2)
									.create().show();
								break;
						}
					}
					return true;

				/* N e u r o n E d i t N a m e */
				case EditorUi.NeuronPropertiesEditName1:
					CreateEditTextDialog(context, "Neuron Name...",
						Str, EditorUi.NeuronPropertiesEditName2,
						Neuron.getSelected().getName());
					return true;
				case EditorUi.NeuronPropertiesEditName2:
					if (Neuron.getSelected() != null) {
						cmd = new SetName<CommonNeuronExtension>(Neuron.getSelected(),
							UIEditTextReturn);
						NeuralNet.addCommand(cmd);
					}
					return true;

				/* N e u r o n E d i t E n e r g y */
				case EditorUi.NeuronPropertiesEditEnergy1:
					CreateEditTextDialog(context, "Neuron Energy...",
						Neg | Num | Dec,
						EditorUi.NeuronPropertiesEditEnergy2, Neuron
						.getSelected().getEnergy() + "");			
					return true;
				case EditorUi.NeuronPropertiesEditEnergy2:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							cmd = new SetEnergy(Neuron.getSelected(),
								Float.parseFloat(UIEditTextReturn));
							NeuralNet.addCommand(cmd);
						}
					} else {
						cmd = new NeuronSelectionSetEnergy(
							Neuron.getAbsoluteSelection(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
						selectionProperties = false;
					}
					return true;
				
				/* N e u r o n E d i t T h r e s h o l d */
				case EditorUi.NeuronPropertiesEditThreshold1:
					CreateEditTextDialog(context,
						"Neuron Threashold...", Neg | Num | Dec,
						EditorUi.NeuronPropertiesEditThreshold2,
						Neuron.getSelected().getThreshold() + "");
								
					return true;
				case EditorUi.NeuronPropertiesEditThreshold2:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							cmd = new SetThreshold(Neuron.getSelected(),
								Float.parseFloat(UIEditTextReturn));
							NeuralNet.addCommand(cmd);
						}
					} else {
						cmd = new NeuronSelectionSetThreshold(
							Neuron.getAbsoluteSelection(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
						selectionProperties = false;
					}
					return true;
				case EditorUi.NeuronPropertiesEditClampMin1:
					CreateEditTextDialog(context,
										 "Clamp min energy", Neg | Num | Dec,
										 EditorUi.NeuronPropertiesEditClampMin2,
										 Neuron.getSelected().getMinClamp() + "");
					
					return true;
				case EditorUi.NeuronPropertiesEditClampMin2:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							cmd = new SetClampMin(Neuron.getSelected(),
												   Float.parseFloat(UIEditTextReturn));
							NeuralNet.addCommand(cmd);
						}
					} else {
						cmd = new NeuronSelectionSetClampMin(
							Neuron.getAbsoluteSelection(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
						selectionProperties = false;
					}
					return true;
				case EditorUi.NeuronPropertiesEditClampMax1:
					CreateEditTextDialog(context,
										 "Clamp max energy", Neg | Num | Dec,
										 EditorUi.NeuronPropertiesEditClampMax2,
										 Neuron.getSelected().getMaxClamp() + "");
					
					return true;
				case EditorUi.NeuronPropertiesEditClampMax2:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							cmd = new SetClampMax(Neuron.getSelected(),
												   Float.parseFloat(UIEditTextReturn));
							NeuralNet.addCommand(cmd);
						}
					} else {
						
						
						cmd = new NeuronSelectionSetClampMax(
							Neuron.getAbsoluteSelection(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
						selectionProperties = false;
					}
					return true;
				/* N e u r o n T y p e S e l e c t o r */
				case EditorUi.NeuronPropertiesTypeCatagory:
				// switch (UIListElementSelected) {
					// case 0://normal
					// if (selectionProperties) {
					// for (Neuron n : Neuron.getAbsoluteSelection()) {
					// n.ResetTypeData();
					// }
					// selectionProperties = false;
					// } else {
					// if (Neuron.getSelected() != null) {
					// Neuron.getSelected().ResetTypeData();
					// }
					// }
					// break;
					// }
					return true;

				/* N e u r o n E x t e n d e d F i e l d S e l e c t o r */
				case EditorUi.NeuronPropertiesSelectExtendedField:
				// if (Neuron.getSelected() != null) {
				// ExtendedFieldAccessed = UIListElementSelected + 2;
				// CreateEditTextDialog(context, "Edit Field...", Num | Neg |
				// Dec, EditorUi.NeuronPropertiesEditExtendedField,
				// Neuron.getSelected().getTypeData(ExtendedFieldAccessed) +
				// "");
				// }
				// return true;
				//
				/* N e u r o n E x t e n d e d F i e l d E d i t */
				case EditorUi.NeuronPropertiesEditExtendedField:
				// if (Neuron.getSelected() != null) {
					// Neuron.getSelected().setTypeData(ExtendedFieldAccessed,
					// Integer.parseInt(UIEditTextReturn));
					// }
					return true;

				/* N e u r o n E d i t C l u s t e r A t t a c h m e n t */
				case EditorUi.NeuronPropertiesEditAttachedCluster1:
					//if (Neuron.getSelected().isInCluster()) {
						lo.add("Detach from cluster");
						if (!Cluster.getSelection().isEmpty()) {
							for (Cluster c : Cluster.getSelection()) {
								lo.add(c.name);
							}
						}
						CreateListDialog(NvrL.getContext(),
							lo,
							"Attach to Cluster...",
							null,
							EditorUi.NeuronPropertiesEditAttachedCluster2)
							.create().show();
					//}//else{
						
					//}
					// create test if neuron is in cluster region
					// ask if we should add it to the cluster
					return true;
				case EditorUi.NeuronPropertiesEditAttachedCluster2:
					if (!selectionProperties) {
						if (Neuron.getSelected() != null) {
							if (UIListElementSelected == 0) {
								cmd = new SetCluster(Neuron.getSelected(), null,
									Neuron.getSelected().getAbsolutePos());
							} else {
								Cluster tc = Cluster.getSelection().get(UIListElementSelected-1);
								
								cmd = new SetCluster(Neuron.getSelected()
								, tc
								, Neuron.getSelected().getAbsolutePos()
									.sub(tc.getPos()));
							}
							NeuralNet.addCommand(cmd);
						}
					} else {
						// needs command for action
						for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
							if (UIListElementSelected == 0) {
								n.setPos(n.getAbsolutePos());
								n.setCluster(null);
							} else {
								n.setPos(n
									.getPos()
									.add(n.getCluster().getPos())
									.sub(Cluster.getSelection()
										.get(UIListElementSelected-1)
										.getPos()));
								n.setCluster(Cluster.getSelection().get(
									UIListElementSelected - 1));
							}
						}
						selectionProperties = false;
					}
					return true;
				/*---N-E-U-R-O-N- -P-R-O-P-E-R-T-I-E-S- -E-N-D---*/

				/* N e u r o n I n p u t s E d i t o r */
				case EditorUi.NeuronInputs:
					if (Neuron.getSelected() != null) {
						if (Neuron.getSelected().inputs.isEmpty()) {
							toast("Neuron has no inputs.");
						} else {
							Neuron.outs = false;
							Intent intent = new Intent(context,
								EditInOutActivity.class);
							context.startActivity(intent);
						}
					} else {
						toast("Select a neuron to edit input connection order");
					}
					return true;

				/* N e u r o n O u t p u t s E d i t o r */
				case EditorUi.NeuronOutputs:
					if (Neuron.getSelected() != null) {
						if (Neuron.getSelected().outputs.isEmpty()) {
							toast("Neuron has no outputs.");
						} else {
							Neuron.outs = true;
							Intent intent = new Intent(context,
								EditInOutActivity.class);
							context.startActivity(intent);
						}
					} else {
						toast("Select a neuron to edit input connection order");
					}
					return true;

				/* N e u r o n D e l e t e */
				case EditorUi.NeuronDelete:
					if (Neuron.getSelected() != null) {
						cmd = new RemoveNeuron(Neuron.getSelected());
						NeuralNet.addCommand(cmd);
					} else {
						toast("Select a neuron to delete.");
					}
					return true;

				/* C o n n e c t i o n M e n u */
				case EditorUi.Connection_Menu:
					if (Neuron.getPreviousSelected() != null
						&& Neuron.getSelected() != null
						&& Connection.getSelected() != null) {
						lo.add("Connection Properties");
						la.add(EditorUi.Connection_Properties);
						lo.add("< Way Points");
						la.add(EditorUi.WayPoint_Menu);
						lo.add("Connection Delete");
						la.add(EditorUi.Connection_Delete);
					}
					CreateListDialog(NvrL.getContext(), lo, "Connection Menu",
						la, 0).create().show();
					return true;

				/* C o n n e c t i o n A d d */
				case EditorUi.Connection_Add:
					if (Neuron.getPreviousSelected() != null
						&& Neuron.getSelected() != null) {
						if (Connection.getSelected() != null) {
							toast("Connection already exists.");
						} else {
							Connection
								.setSelected(new Connection(Neuron
										.getPreviousSelected(), Neuron
										.getSelected(), 1));
							cmd = new AddConnection(Connection.getSelected());
							NeuralNet.addCommand(cmd);
						}
					} else {
						toast("Select Neurons to make a connection");
					}
					return true;

				/* _-_C_o_n_n_e_c_t_i_o_n_ _P_r_o_p_e_r_t_i_e_s_ _S_t_a_r_t_-_ */
				/* C o n n e c t i o n P r o p e r t i e s M e n u */
				case EditorUi.Connection_Properties:
					if (Connection.getSelected() != null
						|| selectionConnectionProperties) {
						lo.add("Connection Type:    "
							+ (selectionConnectionProperties ? "" : Connection
								.getSelected().getTypeDescription()));
						lo.add("Weight:     "
							+ (selectionConnectionProperties ? "" : Connection
								.getSelected().getWeight()));
						lo.add("Randomize Weight");
					// there are many options that could be added to this but
						// for now this will be all.
						CreateListDialog(NvrL.getContext(), lo,
							"Connection Properties Menu", null,
							EditorUi.Connection_PropertiesRoot).create().show();
					} else {
						toast("Select a connection to view properties.");
					}
					return true;

				/*
				 * C o n n e c t i o n P r o p e r t i e s M e n u S e l e c t o
				 * r
				 */
				case EditorUi.Connection_PropertiesRoot:
					switch (UIListElementSelected) {
						case 0:// connection types selector
							lo.addAll(Arrays.asList(Connection.typeNames));
							CreateListDialog(NvrL.getContext(), lo,
								"Connection Type Menu", null,
								EditorUi.Connection_PropertiesType).create().show();
							break;
						case 1:// connection weight editor
							CreateEditTextDialog(NvrL.getContext(),
								"Connection Weight...", Num | Dec,
								EditorUi.Connection_PropertiesWeight,
								(selectionConnectionProperties ? "" : Connection
									.getSelected().getWeight() + ""));
							break;
						case 2:// connection weight setter
							if (!selectionConnectionProperties) {
								if (Connection.getSelected() != null) {
									cmd = new ConnectionSetWeight(
										Connection.getSelected(), FileIO.Rand());
									NeuralNet.addCommand(cmd);
								}
							} else {
								cmd = new ConnectionSelectionSetWeight(
									Connection.getSelection());
								NeuralNet.addCommand(cmd);
								selectionConnectionProperties = false;
							}
							break;
					}
					return true;

				/* C o n n e c t i o n T y p e S e t t e r */
				case EditorUi.Connection_PropertiesType:
					if (Connection.getSelected() != null) {
						cmd = new ConnectionSetType(Connection.getSelected(),
							UIListElementSelected);
						NeuralNet.addCommand(cmd);
					}
					if (selectionConnectionProperties) {
						cmd = new ConnectionSelectionSetType(
							Connection.getSelection(), UIListElementSelected);
						NeuralNet.addCommand(cmd);
						selectionConnectionProperties = false;
					}
					return true;

				/* C o n n e c t i o n W e i g h t S e t t e r */
				case EditorUi.Connection_PropertiesWeight:
					if (Connection.getSelected() != null) {
						cmd = new ConnectionSetWeight(Connection.getSelected(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
					}
					if (selectionConnectionProperties) {
						cmd = new ConnectionSelectionSetWeight(
							Connection.getSelection(),
							Float.parseFloat(UIEditTextReturn));
						NeuralNet.addCommand(cmd);
						selectionConnectionProperties = false;
					}
					return true;
				/* _-_C_o_n_n_e_c_t_i_o_n_ _P_r_o_p_e_r_t_i_e_s_ _E_n_d_-_ */

				/* C o n n e c t i o n D e l e t e */
				case EditorUi.Connection_Delete:
					if (Connection.getSelected() != null) {
						cmd = new DeleteConnection(Connection.getSelected());
						NeuralNet.addCommand(cmd);
						Connection.setSelected(null);
					} else {
						toast("No connection selected.");
					}

					return true;

				/* C o n n e c t i o n W a y P o i n t M e n u */
				case EditorUi.WayPoint_Menu:
					if (Connection.SELECTION.getSelected() != null) {
						lo.add("Add");
						la.add(EditorUi.WayPoint_Add);
						lo.add("Auto arrange");
						la.add(EditorUi.WayPoint_ReOrder);
					}
					if (WayPoint.getSelected() != null || WayPointToBeMoved != null) {
						lo.add(WayPointToBeMoved == null ? "Move" : "Place");
						la.add(WayPointToBeMoved == null ? EditorUi.WayPoint_Move
							: EditorUi.WayPoint_Place);
					}

					if (WayPoint.getSelected() != null) {
						lo.add("Remove");
						la.add(EditorUi.WayPoint_Delete);
					}
					CreateListDialog(NvrL.getContext(), lo, "WayPoint Menu",
						la, 0).create().show();
					return true;

				/* C o n n e c t i o n W a y P o i n t A d d */
				case EditorUi.WayPoint_Add: {
					if (Connection.getSelected() != null) {
						if (Connection.getSelected().getInput() != Connection.getSelected().getOutput()) {
							Point pl = Grid.ScreenToGrid(DisplayEnvironment.getTouch()).add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset())).setZ(DisplayEnvironment.getActiveLayer());
							WayPoint wayPoint = new WayPoint(Connection.getSelected(), pl);
							cmd = new AddWayPoint(wayPoint);
							NeuralNet.addCommand(cmd);
						} else {
							toast("Way point cannot be added to a feedback connection");
						}
					}
					return true;
				}

				/* C o n n e c t i o n W a y P o i n t M o v e */
				case EditorUi.WayPoint_Move: {
					if (WayPoint.getSelected() != null) {
						WayPointToBeMoved = WayPoint.getSelected();
					}
					return true;
				}

				/* C o n n e c t i o n W a y P o i n t P l a c e */
				case EditorUi.WayPoint_Place: {
					Point pl = Grid
						.ScreenToGrid(DisplayEnvironment.getTouch())
						.add(Grid.ScreenToGrid(DisplayEnvironment.getViewOffset()))
						.setZ(DisplayEnvironment.getActiveLayer());
					if (WayPointToBeMoved != null) {
						cmd = new SetPos<WayPoint>(WayPointToBeMoved,pl);
						NeuralNet.addCommand(cmd);
						WayPointToBeMoved = null;
					} else {
						toast("Select Move to move a waypoint");
					}
					return true;
				}

				/* C o n n e c t i o n W a y P o i n t R e m o v e */
				case EditorUi.WayPoint_Delete:
					cmd = new WayPointDelete(WayPoint.getSelected());
					NeuralNet.addCommand(cmd);
					return true;

				/* C o n n e c t i o n W a y P o i n t R e - O r d e r */
				case EditorUi.WayPoint_ReOrder:
					// could be way better
					ArrayList<WayPoint> tmp = new ArrayList<WayPoint>(Connection
						.getSelected().getWayPoints());
					for (int i = -1; i < Connection.getSelected().getWayPoints()
						.size(); i++) {
						WayPoint wp1;
						if (i == -1) {
							wp1 = new WayPoint(Connection.getSelected().getInput()
								.getAbsolutePos());
						} else {
							wp1 = Connection.getSelected().getWayPoints().get(i);
						}
						float d = 999999999;
						int index = 0;
						for (int j = i + 1; j < Connection.getSelected()
							.getWayPoints().size(); j++) {
							WayPoint wp2 = Connection.getSelected().getWayPoints()
								.get(j);
							float d2 = wp1.getAbsolutePos().getDistNoRoot(
								wp2.getAbsolutePos());
							if (d2 <= d) {
								index = j;
								d = d2;
							}
						}
						if (index > i + 1) {
							Collections.swap(Connection.getSelected()
								.getWayPoints(), index, i + 1);
						}
					}
					cmd = new WayPointReOrder(Connection.getSelected(), tmp);
					NeuralNet.addCommand(cmd);
					return true;

				/* _-_L_a_y_e_r_ _M_e_n_u_ _S_t_a_r_t_-_ */
				/* L a y e r U p */
				case EditorUi.Layer_Up:
					DisplayEnvironment
						.setActiveLayer(DisplayEnvironment.getActiveLayer() + 1);
					return true;

				/* L a y e r D o w n */
				case EditorUi.Layer_Down:
					DisplayEnvironment
						.setActiveLayer(DisplayEnvironment.getActiveLayer() - 1);
					return true;

				/* L a y e r G o T o L a y e r E d i t o r */
				case EditorUi.Layer_GoTo:
					CreateEditTextDialog(context, "Go to layer...", Neg | Num,
						EditorUi.Layer_GoTo_Complete,
						DisplayEnvironment.getActiveLayer() + "");
					return true;

				/* L a y e r G o T o L a y e r S e t t e r */
				case EditorUi.Layer_GoTo_Complete:
					DisplayEnvironment.setActiveLayer(UIEditTextReturn
						.contentEquals("") ? DisplayEnvironment.getActiveLayer()
							: Integer.parseInt(UIEditTextReturn));
					return true;
				/* _-_L_a_y_e_r_ _M_e_n_u_ _E_n_d_-_ */

				/* _!_G_r_o_u_p_ _S_t_a_r_t_!_ */
				/* T o u c h G r o u p C r e a t e N a m e E d i t */
				case EditorUi.Group_Create1:// add SELECTION to Saved group's
					CreateEditTextDialog(context, "Name Group as ...", Str,
						EditorUi.Group_Create2, "");
					return true;

				/* G r o u p C r e a t e r */
				case EditorUi.Group_Create2:// finish creating group
					FileIO.getGrouplist().add(
						new Group(UIEditTextReturn, Neuron
							.getAbsoluteSelection()));
					FileIO.saveGroups();
					toast("Group Created");
					UIaction(context, EditorUi.Selection_Deselect);
					return true;

				/* S e l e c t i o n M o v e */
				case EditorUi.Selection_Move:// move selected neurons
					topleft = NvrLView.getSelectStart().copy();
					moveSelected = true;
					toast("moving");
					return true;

				/* T o u c h S e l e c t i o n P l a c e */
				case EditorUi.Selection_Place:// complete move
					// add hanleing for clusters as well as connection waypoints
					for (CommonNeuronExtension ne : Neuron.getSelection()) {
						Point p = ne.getPos().sub(topleft)
							.add(NvrLView.getGridTouched());
						for (CommonNeuronExtension nnn : NeuralNet.getSelected().getNeurons()) {
							if (ne != nnn && nnn.getPos().equal(p)
								&& !Neuron.getSelection().contains(nnn)) {
								toast("Unable to place selection: Neuron on neuron conflict");
								return true;
							}
						}
					}
					cmd = new SelectionReposition(Neuron.getAbsoluteSelection(),
						Cluster.getSelection(), WayPoint.getSelection());
					for (CommonNeuronExtension ne : Neuron.getSelection()) {
						Point newpos = ne.getPos().sub(topleft).add(NvrLView.getGridTouched());
						ne.setPos(newpos);
					}
					for(WayPoint w:WayPoint.getSelection()){
						w.setPos(w.getPos().sub(topleft).add(NvrLView.getGridTouched()));
					}
					for (Cluster c : Cluster.getSelection()) {
						c.setPos(c.Pos.sub(topleft).add(
							NvrLView.getGridTouched()));
					}
					SelectionReposition.setNew((SelectionReposition) cmd);
					NeuralNet.addCommand(cmd);
					toast("placed");
					moveSelected = false;
					UIaction(context, EditorUi.Selection_Deselect);
					return true;

				/* S e l e c t i o n C o p y */
				case EditorUi.Selection_Copy:// copy selected neurons and completed
					// connections
					topleft = NvrLView.getSelectStart().copy();
					copySelected = true;
					toast("copied");
					return true;

				/* S e l e c t i o n P a s t e */
				case EditorUi.Selection_Paste:// complete copy
					// test for neuron position conflict
					for (CommonNeuronExtension ne : Neuron.getAbsoluteSelection()) {
						Point p = ne.getAbsolutePos().sub(topleft)
							.add(NvrLView.getGridTouched());
						for (CommonNeuronExtension n : NeuralNet.getSelected().getNeurons()) {
							if (ne != n && n.getAbsolutePos().equal(p)
								&& !Neuron.getAbsoluteSelection().contains(n)) {
								toast("Unable to paste selection: Neuron on neuron conflict");
								return true;
							}
						}
					}

					ArrayList<CommonNeuronExtension> copyN = new ArrayList<CommonNeuronExtension>();
					ArrayList<CommonNeuronExtension> origN = new ArrayList<CommonNeuronExtension>();
					ArrayList<Cluster> tc = new ArrayList<Cluster>();

					// add copy of clusters and their neurons
					for (Cluster c : Cluster.getSelection()) {
						/*
						 * create a copy of a cluster within a SELECTION at the
						 * relative position of touch
						 */
						 if(c.SubParent==null){
							Cluster NewCluster = c.copy_noConnections(c.Pos.sub(topleft).add(NvrLView.getGridTouched()));
							tc.add(NewCluster);
							copyN.addAll(Cluster.getAllNeuronsCloned());
							origN.addAll(Cluster.getAllNeuronsSource());
						}
					}
					// add copy of non-cluster selected neurons and add them to
					// copyN and the NeuralNet Project
					for (CommonNeuronExtension n : Neuron.getSelection()) {
						CommonNeuronExtension NewNeuron = n.copy(n.getPos().sub(topleft)
							.add(NvrLView.getGridTouched()));
						copyN.add(NewNeuron);
						origN.add(n);
					}

					// create connections
					Connection.CloneAll(origN, copyN,(topleft.sub(NvrLView.getGridTouched())).inv());
					cmd = new SelectionPaste(copyN, tc);
					NeuralNet.addCommand(cmd);
					return true;

				/* S e l e c t i o n D e s e l e c t */
				case EditorUi.Selection_Deselect:

					return UI.SelectionDeselect.execute();

				/* S e l e c t i o n D e l e t e */
				case EditorUi.Selection_Delete:
					cmd = new SelectionDelete(Neuron.getSelection(),
						Cluster.getSelection(),
						Connection.getAbsoluteSelection());
					NeuralNet.addCommand(cmd);
					toast("Selection deleted.");
					UIaction(context, EditorUi.Selection_Deselect);
					return true;

				/* S e l e c t i o n M e n u */
				case EditorUi.Selection_Properties_Menu:
					if (moveSelected) {
						lo.add("Place Selection");
						la.add(EditorUi.Selection_Place);
					} else if (!copySelected) {
						lo.add("Move Selection");
						la.add(EditorUi.Selection_Move);
					}
					lo.add("< Neuron Properties");
					la.add(EditorUi.Selection_Neuron_Properties);
					lo.add("< Connection Properties");
					la.add(EditorUi.Selection_Connection_Properties);
					lo.add(" Turn Right");
					la.add(EditorUi.Selection_Turn_Right);
					lo.add(" Turn Left");
					la.add(EditorUi.Selection_Turn_Left_);
					lo.add("Flip Vertically");
					la.add(EditorUi.Selection_Flip_Vert_);
					lo.add("Flip Horizontally");
					la.add(EditorUi.Selection_Flip_Horz_);
					CreateListDialog(NvrL.getContext(), lo, "Menu ...", la,
						EditorUi.Group_Place2).create().show();
					return true;

				/*
				 * S e l e c t i o n N e u r o n P r o p e r t i e s S e l e c t
				 * o r
				 */
				case EditorUi.Selection_Neuron_Properties:
					selectionProperties = true;
					UIaction(NvrL.getContext(), EditorUi.NEURON_PROPERTIES);
					return true;

				/*
				 * S e l e c t i o n C o n n e t i o n P r o p e r t i e s S e l
				 * e c t o r
				 */
				case EditorUi.Selection_Connection_Properties:
					selectionConnectionProperties = true;
					UIaction(NvrL.getContext(), EditorUi.Connection_Properties);
					return true;

				case EditorUi.Selection_Turn_Right:
					// now to rotate 90 degree's about the touched x,y cord.
					tpoint = NvrLView.getGridTouched().copy();
					cmd = new SelectionReposition(Neuron.getAbsoluteSelection(),Cluster.getSelection(), WayPoint.getSelection());
					np = tpoint;

					// do cluster reposition
					for(Cluster c : Cluster.getSelection()){
						tl = c.getPos();
						br = c.getAbsoluteSize();
						for(CommonNeuronExtension n: c.getNeurons()){
							n.setPos(n.getAbsolutePos());
						}
						br = c.getAbsoluteSize();
						tl = tl.sub(np);
						br = br.sub(np);
						tl.setXY(-tl.Y,tl.X);
						br.setXY(-br.Y,br.X);
						tl.sort(br);
						tl=tl.add(np);
						br=br.add(np);
						c.setPos(tl);
						c.setSize(br.sub(tl));
					}
					for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
						n.Pos = n.getPos().sub(tpoint);
						n.Pos.setXY(-n.Pos.Y,n.Pos.X);
						n.setAbsolutePos(n.Pos.add(np));
					}
					for (WayPoint w : WayPoint.getSelection()) {
						w.Pos = w.getPos().sub(tpoint);
						w.Pos.setXY(-w.Pos.Y,w.Pos.X);
						w.Pos.set(w.Pos.add(np));
					}
					
					
					tl = NvrLView.getSelectStart();
					br = NvrLView.getSelectEnd();
					
					// bring to touch origin
					tl = tl.sub(np);
					br = br.sub(np);
					tl.setXY(-tl.Y,tl.X);
					br.setXY(-br.Y,br.X);
					tl.sort(br);
					tl=tl.add(np);
					br=br.add(np);
					NvrLView.setSelectStart(tl);
					NvrLView.setSelectEnd(br);
				
					SelectionReposition.setNew((SelectionReposition) cmd);
					NeuralNet.addCommand(cmd);
					return true;
				case EditorUi.Selection_Turn_Left_:
					tpoint = NvrLView.getGridTouched().copy();
					cmd = new SelectionReposition(Neuron.getAbsoluteSelection(),Cluster.getSelection(), WayPoint.getSelection());
					np = tpoint;

					// do cluster reposition
					for(Cluster c : Cluster.getSelection()){
						tl = c.getPos();
						br = c.getAbsoluteSize();
						for(CommonNeuronExtension n: c.getNeurons()){
							n.setPos(n.getAbsolutePos());
						}
						br = c.getAbsoluteSize();
						tl = tl.sub(np);
						br = br.sub(np);
						tl.setXY(tl.Y,-tl.X);
						br.setXY(br.Y,-br.X);
						tl.sort(br);
						tl=tl.add(np);
						br=br.add(np);
						c.setPos(tl);
						c.setSize(br.sub(tl));
					}
					for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
						n.Pos = n.getPos().sub(tpoint);
						n.Pos.setXY(n.Pos.Y,-n.Pos.X);
						n.setAbsolutePos(n.Pos.add(np));
					}
					for (WayPoint w : WayPoint.getSelection()) {
						w.Pos = w.getPos().sub(tpoint);
						w.Pos.setXY(w.Pos.Y,-w.Pos.X);
						w.Pos.set(w.Pos.add(np));
					}


					tl = NvrLView.getSelectStart();
					br = NvrLView.getSelectEnd();

					// bring to touch origin
					tl = tl.sub(np);
					br = br.sub(np);
					tl.setXY(tl.Y,-tl.X);
					br.setXY(br.Y,-br.X);
					tl.sort(br);
					tl=tl.add(np);
					br=br.add(np);
					NvrLView.setSelectStart(tl);
					NvrLView.setSelectEnd(br);
					
					SelectionReposition.setNew((SelectionReposition) cmd);
					NeuralNet.addCommand(cmd);
					return true;
				case EditorUi.Selection_Flip_Vert_:
					tpoint = NvrLView.getGridTouched().copy();
					cmd = new SelectionReposition(Neuron.getAbsoluteSelection(),Cluster.getSelection(), WayPoint.getSelection());
					np = tpoint;

					// do cluster reposition
					for(Cluster c : Cluster.getSelection()){
						tl = c.getPos();
						br = c.getAbsoluteSize();
						for(CommonNeuronExtension n: c.getNeurons()){
							n.setPos(n.getAbsolutePos());
						}
						tl = tl.sub(np);
						br = br.sub(np);
						tl.Y=-tl.Y;
						br.Y=-br.Y;
						tl.sort(br);
						tl=tl.add(np);
						br=br.add(np);
						c.setPos(tl);
						c.setSize(br.sub(tl));
					}
					for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
						n.Pos = n.getPos().sub(tpoint);
						n.Pos.Y=-n.Pos.Y;
						n.Pos = n.Pos.add(tpoint);
						if (n.isInCluster()) {
							n.Pos = n.Pos.sub(n.getCluster().Pos);
						}
					}
					for (WayPoint w : WayPoint.getSelection()) {
						w.Pos = w.getPos().sub(tpoint);
						w.getPos().Y = -w.getPos().Y;
						w.Pos = w.Pos.add(tpoint);
					}
					/* do selection reposition
					 */

					tl = NvrLView.getSelectStart();
					br = NvrLView.getSelectEnd();
					np = tpoint;

					// bring to touch origin
					tl = tl.sub(np);
					br = br.sub(np);
					tl.setXY(tl.X,-tl.Y);
					br.setXY(br.X,-br.Y);
					tl.sort(br);
					tl=tl.add(np);
					br=br.add(np);
					NvrLView.setSelectStart(tl);
					NvrLView.setSelectEnd(br);
					SelectionReposition.setNew((SelectionReposition) cmd);
					NeuralNet.addCommand(cmd);

					return true;
				case EditorUi.Selection_Flip_Horz_:
					tpoint = NvrLView.getGridTouched().copy();
					cmd = new SelectionReposition(Neuron.getAbsoluteSelection(),
						Cluster.getSelection(), WayPoint.getSelection());
					np = tpoint;

					// do cluster reposition
					for(Cluster c : Cluster.getSelection()){
						tl = c.getPos();
						br = c.getAbsoluteSize();
						for(CommonNeuronExtension n: c.getNeurons()){
							n.setPos(n.getAbsolutePos());
						}
						tl = tl.sub(np);
						br = br.sub(np);
						tl.X=-tl.X;
						br.X=-br.X;
						tl.sort(br);
						tl=tl.add(np);
						br=br.add(np);
						c.setPos(tl);
						c.setSize(br.sub(tl));
					}
					for (CommonNeuronExtension n : Neuron.getAbsoluteSelection()) {
						n.Pos = n.getPos().sub(tpoint);
						n.Pos.X = -n.Pos.X;
						n.Pos = n.Pos.add(tpoint);
						if (n.isInCluster()) {
							n.Pos = n.Pos.sub(n.getCluster().Pos);
						}
					}
					for (WayPoint w : WayPoint.getSelection()) {
						w.Pos = w.getPos().sub(tpoint);
						w.getPos().X = -w.getPos().X;
						w.Pos = w.Pos.add(tpoint);
						
					}
					/* do selection reposition
					 */
					
					tl = NvrLView.getSelectStart();
					br = NvrLView.getSelectEnd();
					np = tpoint;

					// bring to touch origin
					tl = tl.sub(np);
					br = br.sub(np);
					tl.setXY(-tl.X,tl.Y);
					br.setXY(-br.X,br.Y);
					tl.sort(br);
					tl=tl.add(np);
					br=br.add(np);
					NvrLView.setSelectStart(tl);
					NvrLView.setSelectEnd(br);
					SelectionReposition.setNew((SelectionReposition) cmd);
					NeuralNet.addCommand(cmd);

					return true;

				case EditorUi.Group_Place1:// place group into editor
					for (Group g : FileIO.getGrouplist()) {
						lo.add(g.getName() + "   " + g.getStringSize());
					}
					CreateListDialog(NvrL.getContext(), lo, "Menu ...", null,
						EditorUi.Group_Place2).create().show();
					toast("Place a Group into editor");
					return true;
				case EditorUi.Group_Place2:// finish adding group to editor
					if (UIListElementSelected > -1
						&& !FileIO
						.getGrouplist()
						.get(UIListElementSelected)
						.addGroupToNeuralNet(
							NvrLView
							.getTouch()
							.toGrid()
							.setZ(DisplayEnvironment
								.getActiveLayer()))) {
						toast("Unable to place over an existing neuron");
					}
					return true;
				case EditorUi.Group_Delete1:
					for (Group g : FileIO.getGrouplist()) {
						lo.add(g.getName() + "   " + g.getStringSize());
					}
					CreateListDialog(NvrL.getContext(), lo, "Menu ...", null,
						EditorUi.Group_Delete2).create().show();
					toast("Delete a Group");
					return true;
				case EditorUi.Group_Delete2:// finish group delete
					if (UIListElementSelected > -1) {
						if (!FileIO.getGrouplist().get(UIListElementSelected)
							.isProtected()) {
							FileIO.getGrouplist().remove(UIListElementSelected);
						} else {
							toast("Group cannot be deleted");
						}
					}
					FileIO.saveGroups();
					return true;
				case EditorUi.ClusterContainer_Create1:
					CreateEditTextDialog(context, "Name Cluster Container as ...",
						Str, EditorUi.ClusterContainer_Create2, "");
					return true;
				case EditorUi.ClusterContainer_Create2:
				// think of easy way to allow range or single ie:
					// c1,c2,c3 individually or c1-c2 or c2-c3 or c1-c3
					// for now only allow complete chain
					if(FileIO.getClusterContainerlist()==null){
						return false;
					}
					
					FileIO.getClusterContainerlist().add(
						new ClusterContainer(UIEditTextReturn, Cluster
							.getSelected()));
					FileIO.saveWorkSpace();
					toast("Cluster Container Created");
					UIaction(context, EditorUi.Selection_Deselect);
					return true;
				case EditorUi.ClusterContainer_Place1:// place group into editor
					for (ClusterContainer g : FileIO.getClusterContainerlist()) {
						lo.add(g.getName());
					}
					CreateListDialog(NvrL.getContext(), lo, "Menu ...", null,
						EditorUi.ClusterContainer_Place2).create().show();
					toast("Place a Cluster into editor");
					return true;
				case EditorUi.ClusterContainer_Place2:// finish adding group to
					// editor
					if (UIListElementSelected > -1
						&& !FileIO
						.getClusterContainerlist()
						.get(UIListElementSelected)
						.addToNeuralNet(
							NvrLView
							.getTouch()
							.toGrid()
							.setZ(DisplayEnvironment
								.getActiveLayer()))) {
						toast("Unable to place over an existing neuron");
					}
					return true;
				case EditorUi.ClusterContainer_Delete1:
					for (ClusterContainer g : FileIO.getClusterContainerlist()) {
						lo.add(g.getName());
					}
					CreateListDialog(NvrL.getContext(), lo, "Menu ...", null,
						EditorUi.ClusterContainer_Delete2).create().show();
					toast("Delete a Cluster Container");
					return true;
				case EditorUi.ClusterContainer_Delete2:// finish group delete
					if (UIListElementSelected > -1) {
						if (!FileIO.getClusterContainerlist()
							.get(UIListElementSelected).isProtected()) {
							FileIO.getClusterContainerlist().remove(
								UIListElementSelected);
						} else {
							toast("Cluster Container cannot be deleted");
						}
					}
					FileIO.saveWorkSpace();
					return true;

				// Need Cluster menu/subments
				case EditorUi.Cluster_Menu: {
					if (Cluster.getSelected() != null || ClusterReference != null) {
						lo.add("Properties");
						la.add(EditorUi.Cluster_Properties);
						lo.add(!placeCluster ? "Move" : "Place");
						la.add(!placeCluster ? EditorUi.Cluster_Move
							: EditorUi.Cluster_Place);
						lo.add(!pasteCluster ? "Copy" : "Paste");
						la.add(!pasteCluster ? EditorUi.Cluster_Copy
							: EditorUi.Cluster_Paste);
						lo.add("Delete");
						la.add(EditorUi.Cluster_Delete);
					} else if (!Neuron.getSelection().isEmpty()) {
						lo.add("Create Cluster");
						la.add(EditorUi.Cluster_Create1);
					} else {
						toast(" No Cluster options available at this time. ");
						return true;
					}
					CreateListDialog(
						NvrL.getContext(),
						lo,
						"Cluster Menu... [ " + Cluster.getSelected().name
						+ " ]", la, 0).create().show();
					return true;
				}
				case EditorUi.Cluster_Create1: {
					Point ts = NvrLView.getSelectStart(), te = NvrLView
						.getSelectEnd();
					ts.sort(te);
					if (te.sub(ts).Z == 0) {
						CreateEditTextDialog(context, "Name Cluster as ...", Str,
							EditorUi.Cluster_Create2, "");
					} else {
						toast("Selection contains mulipul layers. \nA Cluster cannot have multipul layers.");
					}
					return true;
				}
				case EditorUi.Cluster_Create2: {
					Point ts = NvrLView.getSelectStart(), te = NvrLView
						.getSelectEnd();
					ts.sort(te);
					Cluster cluster;
					if (Cluster.getSelection().size() == 1) {
						cluster = new Cluster(UIEditTextReturn, ts, te.sub(ts));
					} else {
						cluster = new Cluster(UIEditTextReturn, ts, te.sub(ts),
							Cluster.getSelection());
					}
					cmd = new AddCluster(cluster);
					NeuralNet.addCommand(cmd);
					UIaction(context, EditorUi.Selection_Deselect);
					return true;
				}
				case EditorUi.Cluster_Selector:
					if (Cluster.getSelection().size() == 1) {
						UIListElementSelected = 0;
						UIaction(context, EditorUi.Cluster_Selected);
					} else {
						for (Cluster cl : Cluster.getSelection()) {
							lo.add(cl.name);
						}
						CreateListDialog(NvrL.getContext(), lo,
							"Select Cluster to edit", null,
							EditorUi.Cluster_SetSelected).create().show();
					}
					return true;
				case EditorUi.Cluster_SetSelected:
					Cluster.setSelected(Cluster.getSelection().get(UIListElementSelected));
					UIaction(context, EditorUi.Cluster_Menu);
					Cluster.getSelection().clear();
					return true;
				case EditorUi.Cluster_Selected:
					// Cluster.setSelected(Cluster.getSelection().get(UIListElementSelected));
					UIaction(context, EditorUi.Cluster_Menu);
					Cluster.getSelection().clear();
					return true;
				case EditorUi.Cluster_Properties:
					for (int i = 0; i < Cluster.PROPERTIES.length; i++) {
						lo.add(Cluster.PROPERTIES[i] + ":     "
							+ Cluster.getSelected().getProperty(i));
					}
					CreateListDialog(NvrL.getContext(), lo, "Cluster Menu",
						null, EditorUi.Cluster_EditProperty).create().show();
					return true;
				case EditorUi.Cluster_EditProperty:
					ExtendedFieldAccessed = UIListElementSelected;
					if (UIListElementSelected == Cluster.SUB_PARENT) {
						UIEditTextReturn = "";
						lo.add("Unassociate");
						for (int i = 0; i < NeuralNet.getSelected().getClusters()
							.size(); i++) {
							// add clusters to list
							lo.add(NeuralNet.getSelected().getClusters().get(i).name);
						}
						CreateListDialog(NvrL.getContext(), lo,
							"Select sub parent Cluster", null,
							EditorUi.Cluster_ChangeProperty).create().show();
					} else {
						CreateEditTextDialog(
							context,
							Cluster.PROPERTIES[UIListElementSelected] + "\n"
							+ Cluster.TIP[UIListElementSelected],
							Cluster.DATA_TYPE[UIListElementSelected],
							EditorUi.Cluster_ChangeProperty,
							Cluster.getSelected().getProperty(
								UIListElementSelected));
					}
					return true;
				case EditorUi.Cluster_ChangeProperty:

					if (!Cluster
						.getSelected()
						.setProperty(
							ExtendedFieldAccessed,
							UIEditTextReturn.contentEquals("") ? UIListElementSelected
								+ ""
								: UIEditTextReturn)) {
						toast("Cannot make circular reference");
					}
					return true;
				case EditorUi.Cluster_Delete:
					if (Cluster.getSelected() != null) {
						// do you want to delete neurons/connections
						cmd = new RemoveCluster(Cluster.getSelected());
						NeuralNet.addCommand(cmd);
						// Cluster.getSelected().delete();
					}
					return true;
				case EditorUi.Cluster_SimStep:
					if (Cluster.getSelected() != null) {
						Cluster.getSelected().SimStep();
						toast("Simulation Step for Selected Cluster: "
							+ Cluster.getSelected().name);
					} else {
						NeuralNet.getSelected().ClusterStepUpdate();
						toast("Simulation Step for ALL Clusters.");
					}
					return true;
				case EditorUi.Cluster_Resize: {
					Point p = (NvrLView.getSelectStart());
					Point s = (NvrLView.getSelectEnd().sub(p));
					cmd = new ClusterResize(Cluster.getSelected(), p, s);
					NeuralNet.addCommand(cmd);
					return true;
				}
				case EditorUi.Cluster_Move:
					if (Cluster.getSelected() != null) {
						ClusterReference = Cluster.getSelected();
						placeCluster = true;
					}
					return true;
				case EditorUi.Cluster_Place:
					if (!ClusterReference.isNeuronConflict(NvrLView
						.getGridTouched())) {
						cmd = new ClusterPlace(ClusterReference,
							NvrLView.getGridTouched());
						NeuralNet.addCommand(cmd);
						
					} else {
						toast("Neuron Conflict: connot complete Move.");
					}
					// ClusterReference.move(EditNeuralNetView.getGridTouched());
					ClusterReference = null;
					placeCluster = false;
					return true;
				case EditorUi.Cluster_Paste:
					Cluster c = ClusterReference.copy(NvrLView
						.getGridTouched());
					cmd = new ClusterPaste(c);
					NeuralNet.addCommand(cmd);

					ClusterReference = null;
					pasteCluster = false;
					return true;
				case EditorUi.Cluster_Copy:
					if (Cluster.getSelected() != null) {
						ClusterReference = Cluster.getSelected();
						pasteCluster = true;
					}
					return true;
				case EditorUi.SimStep:
					if(Cluster.getSelected()==null){
						NeuralNet.getSelected().StepUpdate();
					}else{
						Cluster.getSelected().SimStep();
					}
					return true;
				case EditorUi.Cluster_DrawNeurons:
					Cluster.getSelected().drawNeurns = !Cluster.getSelected().drawNeurns;
					return true;
				case EditorUi.ShowErrors:
					ArrayList<String> el = CommonNeuronExtension.errorlog;
					lo.add("Clear Log");
					for (int i = 0; i < el.size(); i++) {
						lo.add(el.get(i));
					}
					CreateListDialog(NvrL.getContext(), lo, "Error Log",
									 null, EditorUi.ShowErrorsAction).create().show();
					
					return true;
				case EditorUi.ShowErrorsAction:
					ExtendedFieldAccessed = UIListElementSelected-1;
					if (ExtendedFieldAccessed == -1) {
						CommonNeuronExtension.clearErrors();
						NvrL.getContext().invalidateOptionsMenu();
					}else{
						CommonNeuronExtension cne =CommonNeuronExtension.erroredNeurons.get(ExtendedFieldAccessed);
						DisplayEnvironment.setViewOffset(cne.Pos.X,cne.Pos.Y,cne.Pos.Z);
						CommonNeuronExtension.setSelected(cne);
						CommonNeuronExtension.setPreviousSelected(cne);
					}
					return true;
				
				/*
				 * =============================================Root Menu
				 * Options=====================================================
				 */
				case EditorUi.RootMenuUndo:
					NeuralNet.undo();
					return true;
				case EditorUi.RootMenuRedo:
					NeuralNet.redo();
					return true;
				case EditorUi.RootMenuNewProject: {
					/*
					 * do you want to save if project is active name project
					 * templates? ( too early to suggest this )
					 */
					NeuralNet.setSelected(new NeuralNet());
				}
				return true;
				case EditorUi.RootMenuOpenProject: {
					/*
					 * do you want to save if project is active project selector
					 */
					FileIO.UIaction(4);
				}
				return true;
				case EditorUi.RootMenuSaveProject: {
				// if (DrawEnvironment.getProjectFilename().contentEquals("")) {
				/*
					 * name project
					 */
					FileIO.UIaction(3);
				// }
				/*
					 * save active project
					 */
				}
				return true;
				case EditorUi.RootMenuImportNeuralNetwork: {
					/*
					 * add a project into this project ( page-ing ?)
					 */
				}
				return true;
				/**
				 * ****************************** PREFERENCES MENU
				 * ****************************
				 */
				case EditorUi.Preferences:// preferences
					lo.add("Run Neural Network Simulation:     "
						+ (runnet ? "Enabled" : "Disabled"));
					lo.add("Simulation Delay:     " + NeuralNetUpdateSpeed);
					lo.add("Draw Clusters:     "
						+ (DisplayEnvironment.isDrawClusters() ? "Enabled"
							: "Disabled"));
					//lo.add("Virtual Reality Edit: \t" + (vrEdit ? "Enabled" : " Disabled"));
					CreateListDialog(NvrL.getContext(), lo,
						"Preferences Menu", null, EditorUi.PreferenceEdit)
						.create().show();
					return true;
				case EditorUi.PreferenceEdit:
					switch (UIListElementSelected) {
						case 0:
							runnet = !runnet;
							break;
						case 1:
							CreateEditTextDialog(context,
								"Neural Network Simulation Delay [0 = no delay]",
								Num, EditorUi.PREFERENCES_SIMULATION_SPEED,
								NeuralNetUpdateSpeed + "");
							break;
						case 2:
							DisplayEnvironment.setDrawClusters(!DisplayEnvironment
								.isDrawClusters());
							break;
					}
					return true;
				case EditorUi.PREFERENCES_SIMULATION_SPEED:
					NeuralNetUpdateSpeed = Integer.parseInt(UIEditTextReturn);
					return true;
					
			}
			NvrLView.setActive(true);
		}
		return false;
	}

	/**
	 * Creates the long touch Main menu
	 */
	public void createEditNeuralNetMenu() {
		ArrayList<Integer> a = new ArrayList<Integer>();// uiactions
		ArrayList<String> lo = new ArrayList<String>();// displayed list options
		if (!NvrLView.isDrawselect()) {
			lo.add("Place Group");
			a.add(EditorUi.Group_Place1);
			lo.add("Place Cluster Container");
			a.add(EditorUi.ClusterContainer_Place1);
			if (Cluster.getSelected() != null
				|| !Cluster.getSelection().isEmpty()
				|| ClusterReference != null) {
				if (Cluster.getSelection().isEmpty()
					&& ClusterReference != null) {
					Cluster.getSelection().add(ClusterReference);
					Cluster.setSelected(ClusterReference);
				}
				if (Cluster.getSelected() != null
					|| Cluster.getSelection().size() == 1) {
					Cluster.setSelected(Cluster.getSelection().get(0));
					lo.add("< Cluster");
					a.add(EditorUi.Cluster_Menu);
					lo.add("Create Cluster Container");
					a.add(EditorUi.ClusterContainer_Create1);
				} else {
					lo.add("[Select Cluster]");
					a.add(EditorUi.Cluster_Selector);
				}
			}
			if (!NeuralNet.getSelected().clusters.isEmpty()) {
				lo.add("Cluster simulation step");
				a.add(EditorUi.Cluster_SimStep);
				if (Cluster.getSelected() != null) {
					lo.add("Resize cluster to selection");
					a.add(EditorUi.Cluster_Resize);
				}
			}
//			if (Neuron.getSelected() != null) {
//				lo.add("< Neuron ");
//				a.add(EditorUi.NeuronMenu);
//			} else {
//				lo.add(placeNeuron ? "Neuron Place" : "Neuron Add");
//				a.add(placeNeuron ? EditorUi.NEURON_MOVE : EditorUi.NEURON_ADD);
//			}
//			if (Connection.getSelected() != null) {
//				lo.add("< Connection  ");
//				a.add(EditorUi.Connection_Menu);
//			} else {
//				lo.add("Connection Add");
//				a.add(EditorUi.Connection_Add);
//			}
			if (WayPoint.getSelected() != null || WayPointToBeMoved != null
				|| Connection.getSelected() != null) {
				lo.add("< Way-Point");
				a.add(EditorUi.WayPoint_Menu);
			}
//			if (Neuron.getSelected() != null
//				&& Neuron.getPreviousSelected() != null
//				&& Connection.getSelected() == null) {
//				lo.add("Create Connection");
//				a.add(EditorUi.Connection_Add);
//			}
			lo.add("");
			a.add(0);
			lo.add("Delete Group");
			a.add(EditorUi.Group_Delete1);
			lo.add("Delete Cluster Container");
			a.add(EditorUi.ClusterContainer_Delete1);
			lo.add("");
			a.add(0);
			lo.add("Step Update");
			a.add(EditorUi.SimStep);

		} else {
//			if (moveSelected) {
//				lo.add("Place Selection");
//				a.add(EditorUi.Selection_Place);
//			} else if (!copySelected) {
//				lo.add("Move Selection");
//				a.add(EditorUi.Selection_Move);
//			}
			if (copySelected) {
				lo.add("Paste Selection");
				a.add(EditorUi.Selection_Paste);
			} else if (!moveSelected) {
				lo.add("Copy Selection");
				a.add(EditorUi.Selection_Copy);
			}
			lo.add("Edit / Move Selection");
			a.add(EditorUi.Selection_Properties_Menu);
			if (!NeuralNet.getSelected().clusters.isEmpty()
				&& Cluster.getSelection().size() == 1) {
				Cluster.setSelected(Cluster.getSelection().get(0));

				if (Cluster.getSelected() != null) {
					lo.add("Resize cluster to selection");
					a.add(EditorUi.Cluster_Resize);
				}
			}
			if (!moveSelected && !copySelected) {
				lo.add("Create group from selection");
				a.add(EditorUi.Group_Create1);
				lo.add("Create Cluster from selection");
				a.add(EditorUi.Cluster_Create1);
				lo.add("Deselect");
				a.add(EditorUi.Selection_Deselect);
				lo.add("");
				a.add(0);
				lo.add("Delete Selection");
				a.add(EditorUi.Selection_Delete);
			}
		}
		CreateListDialog(NvrL.getContext(), lo, "Selection menu", a, 0)
			.create().show();
	}

	// /**
	// * This could be handled elsewhere, but, handles neuron extended fields
	// *
	// * @param sub
	// * @param UIAction
	// */
	// public static void createTypeCatagorySubMenu(int sub, int UIAction) {
	// ArrayList<String> lo = new ArrayList<String>();
	// for (int i = 0; i < Neuron.categoryGroups[sub].length; i++) {
	// lo.add(Neuron.typeNames[Neuron.categoryGroups[sub][i]]);
	// }
	// CreateListDialog(EditNeuralNetActivity.getContext(), lo, "", null,
	// UIAction).create().show();
	// }
	// /**
	// * Generates a default type within extended fields of a Neuron
	// *
	// * @param SelectedNeuron
	// * @param catagory
	// */
	// public static void GenerateType(Neuron SelectedNeuron, int catagory) {
	// SelectedNeuron.ResetTypeData();
	// SelectedNeuron.setTypeData(0, catagory);
	// SelectedNeuron.setTypeData(1, UIListElementSelected);
	// int ExtendedFieldCount = SelectedNeuron.getExtendedFields();
	// for (int i = 0; i < ExtendedFieldCount; i++) {
	// SelectedNeuron.AddFieldTypeData(0);
	// }
	// }
	/**
	 * Used to display a Toast
	 *
	 * @param msg
	 */
	public static void toast(String msg) {
		NvrL.getToaster().setText(msg);
		NvrL.getToaster().show();
	}

	/**
	 * Used to create an edit text dialog
	 *
	 * @param context
	 * @param title
	 * @param type
	 * @param uiAction
	 * @param Value
	 */
	public static void CreateEditTextDialog(final Context context,
		String title, final int type, final int uiAction, String Value) {
		if (type == -1) {
			UIaction(context, uiAction);
			return;
		}
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		UIEditTextReturn = "";
		if (title != null) {
			alert.setTitle(title);
		}
		final EditText uinput = new EditText(context);
		uinput.setInputType(type);
		uinput.setSelectAllOnFocus(true);
		uinput.setText(Value);

		alert.setView(uinput);
		alert.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			});
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				if (uinput.getText().toString().contentEquals("")) {
					dialog.dismiss();
				} else {
					UIEditTextReturn = uinput.getText().toString();
					UIaction(context, uiAction);
					NvrL.getContext().invalidateOptionsMenu();
				}
			}
		});
		alert.create().show();
	}

	/**
	 * Used to create a list dialog
	 *
	 * @param context
	 * @param sList
	 * @param title
	 * @param action
	 * @param uia
	 * @return AlertDialog.Builder
	 */
	public static AlertDialog.Builder CreateListDialog(final Context context,
		ArrayList<String> sList, String title,
		final ArrayList<Integer> action, final int uia) {
		AlertDialog.Builder b = new AlertDialog.Builder(context);
		if (title != null) {
			b.setTitle(title);
		}
		b.setItems(sList.toArray(new String[sList.size()]),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (action != null) {
						if (which < action.size()) {
							UIaction(context, action.get(which));
						} else {
							UIListElementSelected = which;
							UIaction(context, uia);
						}
					} else {
						UIListElementSelected = which;
						UIaction(context, uia);
					}
				}
			});
		return b;
	}
}
