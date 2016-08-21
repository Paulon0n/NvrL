package com.nvrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.nvrl.ConnectionCommands.DeleteConnection;
import com.nvrl.ConnectionCommands.InputDown;
import com.nvrl.ConnectionCommands.InputUp;
import com.nvrl.ConnectionCommands.OutputDown;
import com.nvrl.ConnectionCommands.OutputUp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Displays the Connections that are inputs to a Neuron. Additionally,
 * allows the changing of order of Connections.
 *
 * @author Michael
 * @version 1.0
 */
public class EditInOutActivity extends Activity {

	

	private static CommonNeuronExtension Selected;

	private static ArrayList<Connection> al;
	private static CommandManager comM = new CommandManager();
	List<Map<String, String>> inputList = new ArrayList<Map<String, String>>();
	ListView lv;
	SimpleAdapter simpleAdpt;
	private int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.input_editer);
		Selected = Neuron.getSelected();
		
		al = Neuron.outs ? Selected.getOutputs() : Selected.getInputs();
		initList();
		lv = (ListView) findViewById(R.id.inputediterListViewInputs);
		simpleAdpt = new SimpleAdapter(this, inputList,
			android.R.layout.simple_list_item_1, new String[]{"input"},
			new int[]{android.R.id.text1});

		lv.setAdapter(simpleAdpt);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view,
				int position, long id) {
				view.showContextMenu();
			}
		});
		registerForContextMenu(lv);

	}

	/**
	 * Create a menu
	 *
	 * @param menu
	 * @param v
	 * @param menuInfo
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
		position = aInfo.position;
		HashMap<?, ?> map = (HashMap<?, ?>) simpleAdpt.getItem(aInfo.position);

		menu.setHeaderTitle("Options for " + map.get("input"));
		menu.add(1,!Neuron.outs? 1:101, 1, "Move up");
		menu.add(1, 2, 2, "Delete");
		menu.add(1, !Neuron.outs? 3:303, 3, "Move down");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Command cmd;
		int itemId = item.getItemId();
		switch (itemId) {
			case 1:
				if (position > 0) {
					cmd = new OutputUp(al.get(position), position);
					comM.addCommand(cmd);
					Collections.swap(inputList, position, position - 1);
				}
				break;
			case 101:
				if (position > 0) {
					cmd = new InputUp(al.get(position), position);
					comM.addCommand(cmd);
					Collections.swap(inputList, position, position - 1);
				}
				break;
			case 2:
				if (position < al.size() && position > -1) {
					cmd = new DeleteConnection(al.get(position));
					comM.addCommand(cmd);
					// Selected.getInputs().remove(position);
					inputList.remove(position);
				}
				break;
			case 3:
				if (position < al.size() - 1) {
					cmd = new OutputDown(al.get(position), position);
					comM.addCommand(cmd);
					Collections.swap(inputList, position, position + 1);
				}
				break;
			case 303:
				if (position < al.size() - 1) {
					cmd = new InputDown(al.get(position), position);
					comM.addCommand(cmd);
					Collections.swap(inputList, position, position + 1);
				}
				break;
			case R.id.ioredo:
				comM.redo();
				simpleAdpt.notifyDataSetChanged();
				return true;
			case R.id.ioundo:
				comM.undo();
				simpleAdpt.notifyDataSetChanged();
				return true;

		}
		simpleAdpt.notifyDataSetChanged();
		return true;
	}

	private void initList() {
		inputList.clear();
		for (int i = 0; i < al.size(); i++) {
			String description = al.get(i).getTypeDescription();
			inputList.add(createInput("input", description
				+ al.get(i).getName()));
		}
	}

	private HashMap<String, String> createInput(String key, String name) {
		HashMap<String, String> input = new HashMap<String, String>();
		input.put(key, name);
		return input;
	}

	/**
	 * Set the selected Neuron to look at
	 *
	 * @param selected
	 */
	public static void setSelected(CommonNeuronExtension selected) {
		Selected = selected;
	}

	/**
	 * Get the selected Neuron to look at
	 *
	 * @return Selected neuron
	 */
	public static CommonNeuronExtension getSelected() {
		return Selected;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
