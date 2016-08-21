package com.nvrl;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import com.nvrl.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Basic file input/output as well as a memory storage of Groups and
 * Clusters
 *
 * @author Michael
 */
public class FileIO {

	/**
	 *
	 */

	public static final String[] IDENTS = {
		Neuron.SAVE_ID, 
		Text_Input.SAVE_ID, 
		Text_Output.SAVE_ID, 
		Clear_Output.SAVE_ID,
		Add_Neuron.SAVE_ID,
		Copy_Neuron.SAVE_ID,
		Center_View.SAVE_ID,
		Delete_Neuron.SAVE_ID,
		Move_Neuron.SAVE_ID,
	};
	
	public static String SaveFilename = "";
	private static boolean projectLoaded = true;
	private static final String groupPath = "Data/groups.txt";
	private static final String clusterContainerPath = "Data/clusters.txt";
	private static final String projectSavePath = "Data/saves.txt";
	private static final ArrayList<String> types = new ArrayList<String>();
	private static final ArrayList<Group> groupList = new ArrayList<Group>();
	private static final ArrayList<ClusterContainer> clusterContainerList = new ArrayList<ClusterContainer>();
	private static final Random rn = new Random();
	private static String rootDir;
	private static File myFile;
	private static FileOutputStream fOut;
	private static OutputStreamWriter myOutWriter;
	private static FileInputStream fIn;
	private static BufferedReader myReader;

	/**
	 * Used to load a project.
	 */
	private static void loadProject() {
		NeuralNet n = null;
		String l = FileIO.readLine(), nn = "[+]", e1 = "[E]", c1 = "[c]", k1 = "[C]", s1 = "[S]";
		while (l != null) {
			//Enviroment
			if (l.startsWith(e1)) {
				DisplayEnvironment.load(l.substring(e1.length()));
			}
			
			//NeuroNet
			if (l.startsWith(nn)) {
				n = new NeuralNet(FileIO.getPart(l.substring(nn.length()),1));
				NeuralNet.setSelected(n);
			}
			
			if (n != null&&l.length()>5) {

				String l2 = l.substring(5);

				int i=0;
				//neurons
				if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Neuron(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Text_Input(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Text_Output(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Clear_Output(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Add_Neuron(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Copy_Neuron(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Center_View(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Delete_Neuron(l2));
				}else if (l.startsWith(IDENTS[i++])) {
					n.neurons.add(new Move_Neuron(l2));
				}
				
				//clusters
				if (l.startsWith(k1)) {
					Cluster c = (new Cluster(l.substring(k1.length()),n.neurons));
					c.nn = n;
					n.addCluster2(c);
				}
				
				//connections
				if (l.startsWith(c1)) {
					Connection connection = new Connection(l.substring(c1												   .length()), n.neurons);
					connection.connect();
				}
				
				//nested cluster info
				if (l.startsWith(s1)) {
					Cluster.assignSubs(l.substring(s1.length()), n.clusters);
				}
			}
			l = FileIO.readLine();
		}
	}

	private static String saveProject() {
		return DisplayEnvironment.toString_() + NeuralNet.getSelected().toString();
	}
	
	/**
	 *
	 */
	public static void clear() {
		types.clear();
		groupList.clear();
		clusterContainerList.clear();
	}

	/**
	 * set the root DIR
	 *
	 * @param rootDir
	 */
	public static void setRootDir(String rootDir) {
		FileIO.rootDir = rootDir;
	}

	/**
	 * Get the root DIR
	 *
	 * @return String
	 */
	public static String getRootDir() {
		return rootDir;
	}

	/**
	 * Get the list of Cluster containers
	 *
	 * @return ArrayList<ClusterContainer>
	 */
	public static ArrayList<ClusterContainer> getClusterContainerlist() {
		return clusterContainerList;
	}

	/**
	 * Get the list of Group containers
	 *
	 * @return ArrayList<Group>
	 */
	public static ArrayList<Group> getGrouplist() {
		return groupList;
	}

	/**
	 * Extract data from a data string at position p
	 *
	 * @param line
	 * @param part
	 * @return String
	 */
	public static String getPart(String line, int part) {
		if (part == 1) {
			return line.substring(0, indexOfo(line, "&", 1));
		}
		int start = indexOfo(line, "&", part - 1) + 1;
		int end = indexOfo(line, "&", part);
		return line.substring(start, end);
	}

	/**
	 * Used to count the parts of data within a data string with "&"
	 *
	 * @param s
	 * @return int
	 */
	public static int oCount(String s) {
		return s.length() - s.replaceAll("&", "").length();
	}

	/**
	 * Used to count the parts of data within a data string with
	 * custom Search
	 *
	 * @param s
	 * @param search
	 * @return int
	 */
	public static int oCount(String s, String search) {
		return s.length() - s.replaceAll(search, "").length();
	}

	/**
	 * Get the index of a part within a data string with searchFOR
	 *
	 * @param SearchIN
	 * @param SearchFOR
	 * @param Occurance
	 * @return int
	 */
	public static int indexOfo(String SearchIN, String SearchFOR, int Occurance) {
		int i = 1, lj = 0;
		int o = 0;
		for (; i < SearchIN.length(); i++) {
			int j = SearchIN.indexOf(SearchFOR, i);
			if (j > lj) {
				o++;
				i = j;
			}
			if (o == Occurance) {
				return j;
			}
			lj = j;
		}
		return -1;
	}

	/**
	 * Open file for writing
	 *
	 * @param file
	 */
	public static void openWriteFile(String file) {
		myFile = new File(rootDir, file);
		try {
			myFile.createNewFile();
			fOut = new FileOutputStream(myFile);
			myOutWriter = new OutputStreamWriter(fOut);
		} catch (Exception e) {
		}

	}

	/**
	 * Write data to file
	 *
	 * @param in
	 * @return boolean
	 */
	public static boolean writeToFile(String in) {
		try {
			myOutWriter.append(in);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * close write file
	 */
	public static void closeWriteFile() {
		try {
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Open file for read
	 *
	 * @param file
	 */
	public static void openReadFile(String file) {
		myFile = new File(rootDir, file);

		try {
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			fIn = new FileInputStream(myFile);
			myReader = new BufferedReader(new InputStreamReader(fIn));
		} catch (Exception e) {
		}
	}

	/**
	 * Read a line
	 *
	 * @return String
	 */
	public static String readLine() {
		if(myReader != null)
			try {
				return myReader.readLine();
			} catch (IOException e) {
			}
		return null;
	}

	/**
	 * close read file
	 */
	public static void closeReadFile() {
		if(myReader != null)
			try {
				myReader.close();
			} catch (IOException e) {
			}
	}

	/**
	 * Create file or directory of path
	 *
	 * @param path
	 * @return boolean
	 */
	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;
		File file = new File(path);
		if (!file.exists() && !file.mkdirs()) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Write Groups to file
	 */
	public static void saveGroups() {
		FileIO.openWriteFile(groupPath);
		FileIO.writeToFile(Group.dump());
		FileIO.closeWriteFile();
	}

	/**
	 * Write Cluster containers to file
	 */
	public static void saveClusterContainer() {
		FileIO.openWriteFile(clusterContainerPath);
		FileIO.writeToFile(ClusterContainer.dump());
		FileIO.closeWriteFile();
	}

	/**
	 *
	 */
	public static void saveWorkSpace() {
		saveGroups();
		saveClusterContainer();
	}

	/**
	 * Checks if external storage is available for write
	 *
	 * @return boolean true if available
	 */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return (Environment.MEDIA_MOUNTED.equals(state));
	}

	/**
	 * Checks if external storage is available for read
	 *
	 * @return boolean true if available
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY
			.equals(state));
	}

	/**
	 * Loads groups and clusters into the workspace
	 */
	public static void loadWorkspace() {
		LoadGroups();
		LoadClusters();
	}

	/**
	 * Loads Groups into usable space.
	 */
	private static void LoadGroups() {
		if (FileIO.isExternalStorageReadable()) {
			FileIO.openReadFile(groupPath);
			double build = Double.parseDouble(FileIO.readLine());
			if (NvrL.getBUILD()>(build)) {
				// verify file structure and make changes where required
				NvrL.setUpdatedBuild(true);
				NvrL.setLoadBuild(build);
			}
			String l;
			while ((l = FileIO.readLine()) != null) {
				if (l.startsWith("[+]")) {
					Group.load(l.substring("[+]".length()));
				}
			}
			FileIO.closeReadFile();
		}
	}

	/**
	 * Loads Clusters into usable space.
	 */
	private static void LoadClusters() {
		if (FileIO.isExternalStorageReadable()) {
			FileIO.openReadFile(clusterContainerPath);
			double build = Float.parseFloat(FileIO.readLine());
			if (NvrL.getBUILD()>(build)) {
				// verify file structure and make changes where required
				NvrL.setUpdatedBuild(true);
				NvrL.setLoadBuild(build);
			}
			String l;
			while ((l = FileIO.readLine()) != null) {
				if (l.startsWith("[+]")) {
					ClusterContainer.load(l.substring("[+]".length()));
				}
			}
			// ClusterContainer.load();
			FileIO.closeReadFile();
		}
	}

	
	/**
	 * get a random float ( should be in fast math maybe? )
	 *
	 * @return float
	 */
//	public static float Rand(int i) {
//		return rn.nextInt();
//	}
//	public static float Rand(float i) {
//		return rn.nextFloat();
//	}
//	public static double Rand(double i) {
//		return rn.nextDouble();
//	}
	public static double Rand() {
		return rn.nextDouble();
	}

	private static void setProjectLoaded(boolean setter) {
		projectLoaded = setter;
	}
	
	public static boolean isProjectLoaded(){return projectLoaded;}
	
	/**
	 * @param context
	 * @param Save
	 * @return AlertDialog.Builder
	 */
	public static AlertDialog.Builder SelectFileList(final Context context,
		final boolean Save) {
		AlertDialog.Builder b = new AlertDialog.Builder(context);
		types.clear();
		if (Save) {
			types.add("New File");
			b.setTitle("Save...");
		} else {
			b.setTitle("Open...");
		}

		FileIO.openReadFile(projectSavePath);
		String line;
		while ((line = FileIO.readLine()) != null) {
			types.add(line.endsWith(".txt") ? line : line + ".txt");
		}

		FileIO.closeReadFile();
		b.setItems(types.toArray(new String[types.size()]),
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
					if (which == 0 && Save) {
						filename(context);
					} else {
						// get selected file name
						FileIO.SaveFilename = types.get(which);
						FileIO.UIaction(Save ? 1 : 2);
					}
				}
			});
		return b;
	}

	/**
	 *
	 * @param context
	 */
	public static void filename(final Context context) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Save as...");

		// Set an EditText view to get user input
		final EditText uinput = new EditText(context);
		alert.setView(uinput);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				if (uinput.getText().toString().contentEquals("")) {
					dialog.dismiss();
					filename(context);
					NvrL.toast("A filename must be entered.");
				} else {
					FileIO.SaveFilename = uinput.getText().toString();
					if (!SaveFilename.endsWith(".txt")) {
						SaveFilename += ".txt";
					}
					if (projectLoaded) {
						types.add(SaveFilename);
						FileIO.openWriteFile(projectSavePath);
						for (int i = 1; i < types.size(); i++) {
							FileIO.writeToFile(types.get(i) + "\n");
						}
						FileIO.closeWriteFile();
					}
					FileIO.UIaction(1);
				}
			}
		});

		alert.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

		alert.create().show();
	}

	/**
	 * Needs to be fixed and moved into EditorUi with relative cases.
	 *
	 * @param action
	 */
	public static void UIaction(int action) {
		CommonNeuronExtension.clearErrors();
		switch (action) {
			case 1:// save
				
				if (projectLoaded && !SaveFilename.contentEquals("")) {
					if (!SaveFilename.endsWith(".txt")) {
						SaveFilename += ".txt";
					}
					if (isExternalStorageWritable()) {
						FileIO.openWriteFile("Saves/" + SaveFilename);
						FileIO.writeToFile(saveProject());
						FileIO.closeWriteFile();
						FileIO.saveWorkSpace();
					}
				} else {
					NvrL.toast("This should never appear.");
				}
				break;
			case 2:// open
				setProjectLoaded(false);
				if (isExternalStorageReadable() && !SaveFilename.contentEquals("")) {
					FileIO.openReadFile("Saves/" + SaveFilename);
					loadProject();
					FileIO.closeReadFile();
					setProjectLoaded(true);
				}
				// UIaction(1);
				break;

			case 3:
				SelectFileList(NvrL.getContext(), true).create().show();
				break;

			case 4:
				SelectFileList(NvrL.getContext(), false).create().show();
				break;
		}
	}

}
