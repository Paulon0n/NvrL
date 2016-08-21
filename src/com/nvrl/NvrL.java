package com.nvrl;

/**
 * imports
 */

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.text.method.*;
import android.text.style.*;
import android.view.*;
import android.view.ViewTreeObserver.*;
import android.view.inputmethod.*;
import android.widget.*;
import android.widget.SlidingDrawer.*;
import android.widget.TextView.*;

import android.graphics.Point;


/**
 * This is the main class introduction of NeuronVPL.
 *
 * @author Michael
 */
public class NvrL extends Activity {

	private static NvrLView ENNView;
	private static RelativeLayout fl;
	private static SlidingDrawer drawer;
	private static Button handle;
	private static TextView text;
	private static EditText edittext;
	private static NvrL context;
	private static Toast toaster;
	
	private static EditorUi ui = new EditorUi();
	private static final DisplayEnvironment DE = new DisplayEnvironment();
	private static FileIO Mix = new FileIO();
	private static final double BUILD = 0.0059;
	private static double loadBuild;
	private static boolean updatedBuild = false;

	public static void setLoadBuild(double loadBuild) {
		NvrL.loadBuild = loadBuild;
	}

	public static double getLoadBuild() {
		return loadBuild;
	}
	/**
	 * The constructor that is first called upon the creation of
	 * EditNeuralNetActivity
	 *
	 * @param savedInstanceState The state NvrL is in.
	 */
	@SuppressLint("ShowToast")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		NeuralNet.setSelected(new NeuralNet());
		
		context = this;
		setContentView(R.layout.edit_neuralnet);

		/* create toast messenger */
		setToaster(Toast.makeText(context, "Neural virtual reality Language for Android", Toast.LENGTH_SHORT)).show();

		/* create drawable surface? */
		ENNView = new NvrLView(context);
		ENNView.requestFocus();
		/* setup the console drawer */
		edittext = (EditText) findViewById(R.id.input);
		edittext.setOnEditorActionListener(new OnEditorActionListener() 
			{        
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
					if( actionId==EditorInfo.IME_ACTION_DONE ||
						event.getAction() == KeyEvent.ACTION_DOWN && 
						event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
						String et = edittext.getText().toString();
						if(!et.contentEquals("")){
							//appendTextAndScroll(et); 
							Text_Input.dumpInput(et);
						}
						edittext.setText("");
						return true; // keep the keyboard up
					}
					// if you don't have the return statements in the if structure above, you
					// could put return true; here to always keep the keyboard up when the "DONE"
					// action is pressed. But with the return statements above, it doesn't matter
					return false; // or return true
				}
			});
		
		handle = (Button) findViewById(R.id.handle);
		text = (EditText) findViewById(R.id.text);
		text.setMovementMethod(new ScrollingMovementMethod());
		drawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
		// open drawer listener
		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				handle.setText("↓");
				edittext.requestFocus();
			}
		});

		// close drawer listener
		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {handle.setText("↑");}});

		disableTextDrawer();
		// configure screen size
		configScreenSize();
		
		// create enviroment directories
		FileIO.setRootDir(Environment.getExternalStorageDirectory().toString()
			+ "/NvrL/");
		if (FileIO.createDirIfNotExists(FileIO.getRootDir())) {
			FileIO.createDirIfNotExists(FileIO.getRootDir() + "Data/");
			FileIO.createDirIfNotExists(FileIO.getRootDir() + "Saves/");
			FileIO.createDirIfNotExists(FileIO.getRootDir() + "Scripts/");

		}
		
		// load groups and cluster containers
		FileIO.loadWorkspace();

		// start workload thread (does nothing for now)
		ENNView.startMyLogicThread();
	}
	
	public synchronized static void enableTextDrawer(){
		context.runOnUiThread(new Runnable(){
			@Override
			public void run(){	
				edittext.setVisibility(View.VISIBLE);
				handle.setVisibility(View.VISIBLE);
				text.setVisibility(View.VISIBLE);
				drawer.setVisibility(View.VISIBLE);
			}
		});
	}
	
	public static void disableTextDrawer(){
		context.runOnUiThread(new Runnable(){
			@Override
			public void run(){	
				edittext.setVisibility(View.INVISIBLE);
				handle.setVisibility(View.INVISIBLE);
				text.setVisibility(View.INVISIBLE);
				drawer.setVisibility(View.INVISIBLE);
			}
		});
	
	}
	
	public void showKeyboard(final EditText ettext){
		ettext.requestFocus();
		ettext.postDelayed(new Runnable(){
				@Override public void run(){
					InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					keyboard.showSoftInput(ettext,0);
				}
			}
			,10);
	}
	protected void hideKeyboard(EditText input) {
		input.setInputType(0);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);    
	}

	/**
	 * Configures the screen for use.
	 */
	private void configScreenSize() {
		fl = (RelativeLayout) context.findViewById(R.id.edit_neuralnet);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(10000,
			10000);
		fl.addView(ENNView, params);

		ViewTreeObserver observer = fl.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				init();
				//LayerDrawable ld = (LayerDrawable)fl.getBackground();
				ViewTreeObserver obs = fl.getViewTreeObserver();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					obs.removeOnGlobalLayoutListener(this);
				} else {
					obs.removeGlobalOnLayoutListener(this);
				}
			}
		});
	}

	
	public void appendTextAndScroll(final String texts) {
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				TextView mTextView = (EditText) findViewById(R.id.text);
				mTextView.append("\n" + texts);
				
				final Layout layout = mTextView.getLayout();
				if (layout != null) {
					int scrollDelta = layout
						.getLineBottom(mTextView.getLineCount() - 1)
						- mTextView.getScrollY() - mTextView.getHeight();
					if (scrollDelta > 0)
						mTextView.scrollBy(0, scrollDelta);
				}
			}
		});
		
	}
	
	public void clearOutput(){
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				TextView mTextView = (EditText) findViewById(R.id.text);
				mTextView.setText("");				
			}
		});
	}

	/**
	 * @param in
	 */
	public static void writeToConsole(String in) {
		context.appendTextAndScroll(in);
	}

	/**
	 * Create the xml menu for neuralnet_menu.
	 *
	 * @param menu The Menu handle
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = context.getMenuInflater();
		inflater.inflate(R.layout.nvrl_menu, menu);
		return true;
	}

	/**
	 * Updates the menu item for moving a neuron.
	 *
	 * @param menu The Menu to be altered.
	 * @return true.
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.gear).setIcon(DisplayEnvironment.ERRORS? R.drawable.gear32red:R.drawable.gear32);
		
		MenuItem liveitem = menu.findItem(R.id.showerrors);
		liveitem.setVisible(DisplayEnvironment.ERRORS);
		SpannableString s = new SpannableString(liveitem.getTitle().toString());
		s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
		liveitem.setTitle(s);
		
		menu.findItem(R.id.n_move).setTitle(
			EditorUi.isPlaceNeuron() ? "Place" : "Move");
		menu.findItem(R.id.p_import).setVisible(false);
		menu.findItem(R.id.c_prop).setVisible(false);
		
		if (Neuron.getSelected() == null) {
			menu.findItem(R.id.n_add).setVisible(true);
			menu.findItem(R.id.n_move).setVisible(EditorUi.isPlaceNeuron());
			menu.findItem(R.id.n_prop).setVisible(false);
			menu.findItem(R.id.n_name).setVisible(false);
			menu.findItem(R.id.n_energy ).setVisible(false);
			menu.findItem(R.id.n_threshold).setVisible(false);
			menu.findItem(R.id.n_position).setVisible(false);
			menu.findItem(R.id.n_cluster).setVisible(false);
			menu.findItem(R.id.n_delete).setVisible(false);
			menu.findItem(R.id.n_inputs).setVisible(false);
			menu.findItem(R.id.n_outputs).setVisible(false);
			menu.findItem(R.id.n_clamp).setVisible(false);
			menu.findItem(R.id.n_min).setVisible(false);
			menu.findItem(R.id.n_max).setVisible(false);
			
			//program neurons enable
			menu.findItem(R.id.addneuron).setVisible(true);
			menu.findItem(R.id.copyneuron).setVisible(true);
			menu.findItem(R.id.moveneuron).setVisible(true);
			menu.findItem(R.id.setclamp).setVisible(true);
			menu.findItem(R.id.setclampmax).setVisible(true);
			menu.findItem(R.id.setclampmin).setVisible(true);
			menu.findItem(R.id.getinputsize).setVisible(true);
			menu.findItem(R.id.getinputneuron).setVisible(true);
			menu.findItem(R.id.getoutputsize).setVisible(true);
			menu.findItem(R.id.getoutputneuron).setVisible(true);
			menu.findItem(R.id.deleteneuron).setVisible(true);
			menu.findItem(R.id.connect).setVisible(true);
			menu.findItem(R.id.getconnection).setVisible(true);
			menu.findItem(R.id.setweight).setVisible(true);
			menu.findItem(R.id.disconnect).setVisible(true);
			
			if (Connection.SELECTION.getSelected() != null) {
				menu.findItem(R.id.c_add).setVisible(false);
				menu.findItem(R.id.c_delete).setVisible(true);
				menu.findItem(R.id.c_type).setVisible(true);
				menu.findItem(R.id.c_weight).setVisible(true);
				menu.findItem(R.id.c_weight).setTitle("Weight "+Connection.SELECTION.getSelected().getWeight());
				
			} else {
				menu.findItem(R.id.c_add).setVisible(false);
				menu.findItem(R.id.c_delete).setVisible(false);
				menu.findItem(R.id.c_type).setVisible(false);
				menu.findItem(R.id.c_weight).setVisible(false);
			}
		} else {
			menu.findItem(R.id.n_add).setVisible(false);
			menu.findItem(R.id.n_move).setVisible(true);
			menu.findItem(R.id.n_prop).setVisible(false);
			
			MenuItem m = menu.findItem(R.id.n_name);
			m.setVisible(true);
			m.setTitle("Name: "+Neuron.getSelected().getName());
			
			m = menu.findItem(R.id.n_energy );
			m.setVisible(true);
			m.setTitle("Energy: "+Neuron.getSelected().getEnergy());
			
			m = menu.findItem(R.id.n_threshold);
			m.setVisible(true);
			m.setTitle("Threshold: "+Neuron.getSelected().getThreshold());
			
			m = menu.findItem(R.id.n_position);
			m.setVisible(true);
			m.setTitle( "x: "+Neuron.getSelected().getPos().X +
						" \t y: "+Neuron.getSelected().getPos().Y +
						" \t z: "+Neuron.getSelected().getPos().Z);
			
			m = menu.findItem(R.id.n_clamp);
			m.setVisible(true);
			m.setTitle("Clamp: \t\t"+(Neuron.getSelected().isClamp()?"Enabled":"Disabled"));
			
			m=menu.findItem(R.id.n_min);
			m.setVisible(Neuron.getSelected().isClamp());
			m.setTitle("Min: "+Neuron.getSelected().getMinClamp());
			
			m=menu.findItem(R.id.n_max);
			m.setVisible(Neuron.getSelected().isClamp());
			m.setTitle("Max: "+Neuron.getSelected().getMaxClamp());
			
			m=menu.findItem(R.id.n_cluster);
			m.setVisible(true);
			CommonNeuronExtension n = Neuron.getSelected();
			if(n!=null){
				if(n.isInCluster())
					m.setTitle("Cluster: \t\t"+n.getCluster().getName());
				else
					m.setTitle("Cluster Unassigned:");
			}
			menu.findItem(R.id.n_delete).setVisible(true);
			menu.findItem(R.id.n_inputs).setVisible(true);
			menu.findItem(R.id.n_outputs).setVisible(true);
			
			if (Connection.SELECTION.getSelected() != null) {
				menu.findItem(R.id.c_add).setVisible(false);
				menu.findItem(R.id.c_delete).setVisible(true);
				menu.findItem(R.id.c_type).setVisible(true);
				menu.findItem(R.id.c_type).setTitle("Type:  "+Connection.SELECTION.getSelected().getTypeDescription());
				menu.findItem(R.id.c_weight).setVisible(true);
				menu.findItem(R.id.c_weight).setTitle("Weight:  "+Connection.SELECTION.getSelected().getWeight());
				
			} else if (Neuron.getPreviousSelected() != null) {
				menu.findItem(R.id.c_add).setVisible(true);
				menu.findItem(R.id.c_delete).setVisible(false);
				menu.findItem(R.id.c_type).setVisible(false);
				menu.findItem(R.id.c_weight).setVisible(false);
				
			} else if (Connection.SELECTION.getSelected() == null) {
				menu.findItem(R.id.c_add).setVisible(false);
				menu.findItem(R.id.c_delete).setVisible(false);
				menu.findItem(R.id.c_type).setVisible(false);
				menu.findItem(R.id.c_weight).setVisible(false);
				
			}
			//program neurons disable
			menu.findItem(R.id.addneuron).setVisible(false);
			menu.findItem(R.id.copyneuron).setVisible(false);
			menu.findItem(R.id.moveneuron).setVisible(false);
			menu.findItem(R.id.setclamp).setVisible(false);
			menu.findItem(R.id.setclampmax).setVisible(false);
			menu.findItem(R.id.setclampmin).setVisible(false);
			menu.findItem(R.id.getinputsize).setVisible(false);
			menu.findItem(R.id.getinputneuron).setVisible(false);
			menu.findItem(R.id.getoutputsize).setVisible(false);
			menu.findItem(R.id.getoutputneuron).setVisible(false);
			menu.findItem(R.id.deleteneuron).setVisible(false);
			menu.findItem(R.id.connect).setVisible(false);
			menu.findItem(R.id.getconnection).setVisible(false);
			menu.findItem(R.id.setweight).setVisible(false);
			menu.findItem(R.id.disconnect).setVisible(false);
			
		}
		NvrL.getContext().invalidateOptionsMenu();
		return true;
	}

	/**
	 * On selection of a UI element
	 *
	 * @param item The UI element selected
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.n_add:
				UI.NeuronAdd.execute();break;
			case R.id.n_move:
				EditorUi.UIaction(context,EditorUi.NEURON_MOVE);break;
			case R.id.n_prop://legacy
				EditorUi.UIaction(context,EditorUi.NEURON_PROPERTIES);break;
			case R.id.n_name:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditName1);break;
			case R.id.n_energy:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditEnergy1);break;
			case R.id.n_threshold:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditThreshold1);break;
			case R.id.n_cluster:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditAttachedCluster1);break;
			case R.id.n_clamp:
				UI.NeuronClamp.execute();break;
			case R.id.n_min:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditClampMin1);break;
			case R.id.n_max:
				EditorUi.UIaction(context, EditorUi.NeuronPropertiesEditClampMax1);break;
			case R.id.n_delete:
				EditorUi.UIaction(context,EditorUi.NeuronDelete);break;
			case R.id.n_inputs:
				EditorUi.UIaction(context,EditorUi.NeuronInputs);break;
			case R.id.n_outputs:
				EditorUi.UIaction(context,EditorUi.NeuronOutputs);break;
			
			case R.id.c_add:
				EditorUi.UIaction(context,EditorUi.Connection_Add);break;
			case R.id.c_prop: // legacy
				EditorUi.UIaction(context,EditorUi.Connection_Properties);break;
			case R.id.c_type:
				EditorUi.UIListElementSelected = 0;
				EditorUi.UIaction(context,EditorUi.Connection_PropertiesRoot);
				break;
			case R.id.c_weight:
				EditorUi.UIListElementSelected = 1;
				EditorUi.UIaction(context,EditorUi.Connection_PropertiesRoot);
				break;
			case R.id.c_delete:
				EditorUi.UIaction(context,EditorUi.Connection_Delete);break;
	
			case R.id.l_up:
				EditorUi.UIaction(context,EditorUi.Layer_Up);break;
			case R.id.l_down:
				EditorUi.UIaction(context,EditorUi.Layer_Down);break;
			case R.id.l_goto:
				EditorUi.UIaction(context,EditorUi.Layer_GoTo);break;
			
			case R.id.p_new:
				EditorUi.UIaction(context,EditorUi.RootMenuNewProject);break;
			case R.id.p_save:
				EditorUi.UIaction(context,EditorUi.RootMenuSaveProject);break;
			case R.id.p_open:
				EditorUi.UIaction(context,EditorUi.RootMenuOpenProject);break;
			case R.id.p_import:
				EditorUi.UIaction(context,EditorUi.RootMenuImportNeuralNetwork);break;
			case R.id.p_preferences:
				EditorUi.UIaction(context,EditorUi.Preferences);break;
			
			case R.id.undo:
				EditorUi.UIaction(context,EditorUi.RootMenuUndo);break;
			case R.id.redo:
				EditorUi.UIaction(context,EditorUi.RootMenuRedo);break;
			case R.id.exit:
				/* !! prompt to save first !! */
				finish();
				//finishAndRemoveTask();
				break;
			case R.id.showerrors:
				EditorUi.UIaction(context,EditorUi.ShowErrors);break;
				
			
			// neuron input commands
			case R.id.textinput:UI.TextInput_Add.execute();break;
				
			//neuron output commands
			case R.id.textoutput:UI.TextOutput_Add.execute();break;
			case R.id.clearoutput:UI.ClearOutput_Add.execute();break;
			case R.id.centerview: UI.Center_View_Neuron.execute();break;
			
			case R.id.addneuron: UI.Add_Neuron.execute();break;
			case R.id.copyneuron: UI.Copy_Neuron.execute();break;
			case R.id.moveneuron: UI.Move_Neuron.execute();break;
			case R.id.deleteneuron: UI.Delete_Neuron.execute();break;
			
			
			default:
				return super.onOptionsItemSelected(item);
		}
		NvrL.getContext().invalidateOptionsMenu();
		return true;
	}

	/**
	 * Used to change Neuron Move/Place within the drop down menu.
	 *
	 * @param config the configuration that changed
	 */
	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (NvrLView.screenHeight > NvrLView.screenWidth) {
				NvrLView.screenHeight = height;
				NvrLView.screenWidth = width;
				DisplayEnvironment.screenChanged();
			}
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (NvrLView.screenWidth > NvrLView.screenHeight) {
				NvrLView.screenWidth = width;
				NvrLView.screenHeight = height;
				DisplayEnvironment.screenChanged();
			}
		}

	}

	/**
	 * Displays a message in a toast
	 *
	 * @param msg - the message to toast
	 */
	public static void toast(String msg) {
		toaster.cancel();
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!NvrLView.isActive()) {
			NvrLView.setActive(true);
			ENNView.startMyLogicThread();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		NvrLView.setActive(false);
	}

	@Override
	public void onStop() {
		super.onStop();
		NvrLView.setActive(false);
	}

	@Override
	public void onRestart() {
		super.onRestart();
		NvrLView.setActive(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		NvrLView.setActive(false);
	}

	@Override
	public void onStart() {
		super.onStart();
		NvrLView.setActive(true);
	}

	@Override
	public void onBackPressed() {
		// add dialog for saving
		NvrLView.setActive(false);
		toaster.cancel();
		FileIO.clear();
		Mix = null;
		context = null;
		finish();
	}

	/**
	 * @return String
	 */
	public static final double getBUILD() {
		return BUILD;
	}

	/**
	 * Get the DrawEnvironment
	 *
	 * @return NeuralEnviroment
	 */
	public static DisplayEnvironment getDE() {
		return DE;
	}

	/**
	 * Assign the views screen width and height.
	 */
	protected void init() {
		NvrLView.screenHeight = fl.getHeight();
		NvrLView.screenWidth = fl.getWidth();
		NvrLView.getOEE().resetEdit();
	}

	/**
	 * Get the available memory the application can use
	 *
	 * @return a numeric string
	 */
	public static String getMem() {
		ActivityManager activityManager = (ActivityManager) context
			.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		return "" + memoryInfo.availMem;
	}

	/**
	 * Set the context of this activity
	 *
	 * @param act The activity / context
	 */
	public static void setContext(NvrL act) {
		NvrL.context = act;
	}

	/**
	 * get Activity / context
	 *
	 * @return context
	 */
	public static NvrL getContext() {
		return context;
	}

	/**
	 * Sets the ui being used.
	 *
	 * @param ui The User Interface being used.
	 */
	public static void setUi(EditorUi ui) {
		NvrL.ui = ui;
	}

	/**
	 * Get User Interface being Used
	 *
	 * @return ui - EditorUi
	 */
	public static EditorUi getUi() {
		return ui;
	}

	/**
	 * Set a toaster
	 *
	 * @param toaster
	 * @return Toast
	 */
	public static Toast setToaster(Toast toaster) {
		return NvrL.toaster = toaster;
	}

	/**
	 * Get the toaster
	 *
	 * @return toaster - a Toast
	 */
	public static Toast getToaster() {
		return toaster;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	public static boolean isUpdatedBuild(){
		return updatedBuild;
	}

	public static void setUpdatedBuild(boolean updated){
		updatedBuild=updated;
	}
}
