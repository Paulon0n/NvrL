/*******************   [   C H A N G E   L O G   ]   *********************/ 
[07.10.16] NVRLAnd (Alpha) v0.0055
- Noted:	[*****] A number of changes and fixes that have not been logged occured
- Added:	[***..] neuron to receive text from console
- Added:	[***..] neuron to display text on console
- Added:	[***..] accessable id's for neurons, connections, and clusters 
- Aband:	[*****] scripting in all forms (complete removal)

[09.25.15] NvrL (Alpha) v.0.054
- renamed Neural VPL to NvrL because why not.
- removed scripting to focus on the completion of the interface and 
	editing commands. (complete removal)
- reworking WayPoint handling
- Fixed:	[*****] waypoint referencing neuron for position
- Fixed:	[*****] moving selection ignores waypoints
- Fixed:	[*****] copy/paste selection causes crash
- Fixed:	[*****] adding a waypoint was relative giving incorrect location
- Fixed:	[*****] move/place waypoint was relative
- Fixed:	[*****] Cluster move/place waypoints ignored
- Fixed:	[*****] Cluster copy/paste waypoints ignored
- Optimized:[*****] Groups file size a bit with much room for improvement.

[01.06.15] neuron (Alpha) v.0.033
- Rewrote script engine (still incomplete).

[12.21.14] neuron (Alpha) v.0.032
-replaced:	[.....] script engine, still working on it, and may replace 
					again if i find something more compatible... 
-replaced:	[.....] neuron type data with ability to store script engine
					instances, may add variable storage as well if script 
					engine does not do it.
- Aband:	[*****] allow editing of neuron limit 
					(script override will do this)

[12.08.14] neuron (Alpha) v.0.031
- Fixed:    [*****] cleanup game elements from neurons
- Added:	[*****] script engine (very incomplete)
- Added:	[*****] console (this is where scripts for neurons will be 
					created)
- Added:	[*....] show one step count and activation, green true, white 
					false.
- Fixed:	[*****] cluster container not saving connections
- Aband:	[-----] develop the script neuron 
					(all neurons will have this ability)

[12.05.14] neuron (Alpha) v.0.030
- Added:	[.....] Undo/Redo for placing Groups and Cluster Containers 
					into project
- Fixed:	[*****] doubling of connections in Cluster Container place and
					paste cluster
- Added:	[****.] safety to the cloning of connections

[12.04.14] neuron (Alpha) v.0.029
- Feature:	[*****] undo-redo
	Neuron [done]
	[X] Add
	[X] Move
	[X] set Energy
	[X] set Threshold
	[X] set random Threshold
	[X] selection set Energy
	[X] selection set Threshold
	[X] selection set random Threshold
	[X] name
	[X] set Cluster
	[X] selection set Cluster
	[X] Delete
	Connection [done]
	[X] Add
	[X] Delete
	[X] set Weight
	[X] set Type
	[X] selection set Weight
	[X] selection set random Weight
	[X] selection set Type
	Way Point [done]
	[X] Add
	[X] Move
	[X] Delete
	[X] Re-Order
	Cluster [done]
	[X] Add
	[X] Delete
	[X] Name
	[X] Move
	[X] Paste
	[X] Resize
	[X] set one step
	[X] set step count
	Selection
	[X] Move
	[X] Paste
	[X] Delete
	[X] turn left
	[X] turn right
	[X] flip vertically
	[X] flip horizontally
- Fixed:  [.....] some bugs with cluster loading

[11.24.14] neuron (Alpha) v.0.028
- Fixed:  [*****]  issues with cluster resizing 
- Fixed:  [*****]  selection properties edit causes problems 
- Added:  [*****]  selection rotate with options of left and right
- Added:  [*****]  selection flip with options of horizontal and vertical
- Added:  [*****]  selection manager to free up duplicated code
- Fixed:  [*****]  bugs related to deleting selections with way points and clusters
- Fixed:  [*****]  selection turn and flip not working correctly with 
					selection within cluster
- Fixed:  [*****]  creating cluster containers works again

[11.17.14] neuron (Alpha) v.0.027
- Fixed:  [*****]  copied neurons were not retaining energy.
- Added:  [*****]  Project new/save/load all work
- Added:  [*****]  cluster containers now work exactly like groups 
					(auto save/load, option to delete)

[11.11.14] neuron (Alpha) v.0.026
- Added:  [*****]  cluster container save works, however cannot load yet.
- Added:  [*****]  cluster container can handle sub clusters, cannot 
					be saved, yet is functional
- Added:  [*****]  alter cluster sub parent reference
- Added:  [*****]  stops accidental circular references for cluster sub 
						parent attachment (Infinite looping simulation)
- Fixed:  [*****]  sub clusters not processed correctly

[11.02.14] neuron (Alpha) v.0.025
- Added:  [**...]  cluster container class. (used to contain clusters and subs)
- Fixed:  [****.]  Improved auto arrange Way Points, shuffles the Way Points 
					so each auto arrange could be slightly different
- Fixed:  [*****]  neuron move/place broke
- Added:  [*****]  Cluster touch menu options, though are incomplete
- Fixed:  [***..]  Cleaned up compiler warnings

[10.27.14] neuron (Alpha) v.0.024
- Fixed:  [*****]  removed clusters from groups
- Fixed:  [*****]  groups are working again (auto save and load as well)
- Added:  [*****]  alter multiple properties of Neurons and Connections in
					a selection
- Added:  [***..]  auto arrange Way Points (since it is guessing do not try 
					to use on a complicated Way Point path)
- Added:  [*****]  javadoc - to code (needs better documenting but it is 
					a start)
- Added:  [*****]  Cluster resizes when moving an attached neuron out of 
					cluster bounds
- Added:  [****.]  option to resize cluster to selection (includes 
					non-cluster neurons into cluster)
- Added:  [*****]  option to de-attach neuron from all clusters
- Aband:  [*****]  Reorganize cluster attributes.(not needed with new 
					resizing methods)

[10.20.14] neuron (Alpha) v.0.023
- Fixed:  [*****]  application crashes sometimes with long touch
- Fixed:  [*....]  when moving/copying selection of multi clusters; 
					connections are not part of selection. 
- Added:  [*....]  integrate clusters into selection options. 
- Added:  [*****]  cluster select choice when multiple clusters exist on 
					touch location
- Added:  [.....]  Cluster name is not on cluster menu options for clarity 
					and confirmation. 
- Fixed:  [*....]  Way Points processed faster in copy/paste
- Fixed:  [*....]  Clusters do not handle way points (this might not be a
					 problem) ?
- Fixed:  [*****]  when changing neuron 'attached to cluster' option. 
				 	neuron position does not update to the selected cluster.
- Added:  [*****]  allow sub clusters.
- Fixed:  [*****]  neurons with connection outside of selection make a 
					connection to its master self on paste
- Added:  [*****]  Cluster options: delete,move/place,copy/paste
- Fixed:  [*....]  when moving/copying selection, clusters are not part of 
					selection. 

[10.15.14] neuron (Alpha) v.0.022
- Added:  [***..]  Cluster resize 
- Added:  [*....]  make cluster name visible
- Start:  [*****]  started work on script neuron
- Fixed:  [**...]  Menu option 'Move' for neuron does not switch to 'Place'
- Fixed:  [*****]  view resizing makes drawing fall off the grid just a bit.
- Fixed:  [*****]  Screen project is persistent through orientation change
- Fixed:  [***..]  rethink or remove the title menu.
- Fixed:  [*****]  fixed various U.I. issues and made options of U.I. 
					easier to understand
- Fixed:  [*****]  show only usable menu options
- Aband:  [*....]  integrating clusters into groups. [clusters are handled 
					on their own] 
- Aband:  [.....]  i have a thought, i need to rework the neural engine 
                    and do some planning, but containers should be added 
                    and the grid could be removed… [clusters do this already
                    , grid needed]
- Aband:  [.....]  create a container, add in-ports and out-ports, 
                    make descriptions visible on hover.[clusters could this]
- Fixed:  [**...]  Way Points within a selection are moved with the selection
- Change: [*****]  move code around for easier reading

[01.27.14] neuron (Alpha) v.0.021
- Fixed:  [*****]  added connection types: increase/decrease neuron's 
					output connection weights
- Fixed:  [*****]  added storage of neuron output connections for altering 
					output connecting weights connection
- Fixed:  [*****]  delete cluster containers
- Fixed:  [*****]  selecting a connection longer then the screen does not 
					work, ie. requiring movement in the environment!
- Fixed:  [*****]  simulating a neural net had a conflict during run 
					simulation where the neural net would clear or perform 
					unexpectedly
- Change: The name of this project is now 'Neural VPL', 
					VPL = Visual Programming Language

[01.14.14] neuron (Alpha) v.0.020
- Fixed: Selection move/place and copy/paste now handle way points
- Fixed: Issue with way points being created 4 times.
- Fixed: removed ability to add a way point to a feedback neuron connection
- Fixed: Flickering top editor line.
- Fixed: Editor lines in landscape are not displayed correctly

[01.04.14] neuron (Alpha) v.0.019
- Fixed: Groups do not handle way points
- Added: Connection way-points (this will visually help with organization.)
- Added: Way-Point options(add, move/place, delete)
- Added: Way Point class and integrated it into connections and connection 
			saved data.
- Fixed: editor touch behavior (selecting neurons does not progress with 
			long touch)
- Started: connection way points
- Fixed: selection selecting cluster neurons randomly
- Fixed: placing a group doubles connections
- Fixed: CRASH when removing a neuron created by placing a group
- Fixed: cluster processing complete
- Fixed: when moving neuron that is part of a cluster (was not placing 
			correctly).
- Fixed: inhibiter connection is inhibiting even if controlling neuron is 
			not active
- Fixed: created cluster neuron not being processed
- undone: cluster process not removing non-cluster neuron energy making 
			entire cluster active. 
	* note: this has to be handled inside a cluster because multiple 
	clusters and/or neurons might make use of said neuron's activity
- Fixed: editing a property is now automatically selected, making changes 
			to the property much faster.
- Fixed: cannot change connection type
- Fixed: neuron created in cluster using world cords and not cluster cords.
- Fixed: neuron not being added to cluster neuron list.
- Fixed: creating a cluster always says "cluster contains many layers"

[12.30.13] neuron (Alpha) v.0.018
- added: simulation step menu option
- started: cluster processing.  Note: very buggy
- fixed: multi-layer Cluster (removed unwanted ability)
- fixed: Save\loading a game with the use of clusters is bugged, crashes 
			game when trying to edit bot that makes use of a cluster.
- fixed: when deleting a selection, connections are remaining
- fixed: Neurons of a cluster need to be associated as a part of a cluster
- fixed: bug in save/load game that caused a crash
- fixed: long touch menu neuron move/place
- fixed: long touch menu for neurons and connections
- fixed: adding a neuron to a cluster.
- fixed: clusters cannot overlap on same layer.
			(will look into changing this in the future)
- updated CHANGELOG
- updated TODO

[12.16.13] neuron (Alpha) v.0.017
- I think creating a Group with Cluster/s is done, need to finish Group 
	place to know for sure.
- fixed: Changed Cluster start/end to Pos/Size and implemented the changes 
			in code.
		* This will make doing things with cluster much easier.
- fixed: Cluster Neurons now take advantage of cluster positioning in draw
- added: neuron and connection options to long press edit neural network 
			menu
- fixed: drawing Neurons in cluster , and connections in clusters and 
			connections going into and out of clusters.
- fixed: Some re-organization of Editor U.i. class.
- fixed: selection not deselecting when creating a neuron. 
		* it would look like creating a neuron in a selection would also 
		move/copy that neuron as well, but is not the case.
- updated CHANGELOG
	

[12.09.13] neuron (Alpha) v.0.016
- fixed: cluster one Step Count always displaying 8.
- added: deselect on third double tap
- fixed: group size not displaying correct size
- added: ability to select overlapping cluster to edit preferences
- fixed: food loading with y axis always 0
- added: selection able to select clusters
- added: clusters to groups(not group save yet)
- added: group size takes cluster size into account
- added: entire cluster must be selected for cluster to be included in group
- added: start play activity after loading a saved game
- updated README
- updated TODO
- updated CHANGELOG

[12.07.13] neuron (Alpha) v.0.015
- started work on cluster U.I..
	- added "Create cluster from selection"
	- added editable options for cluster

[12.02.13] neuron (Alpha) v.0.014
- fixed small bug with editing connection weight

[12.01.13] neuron (Alpha) v.0.014
- Neuron type UI is complete
	- data for each type needs to be entered
- U.I. overhaul is done
	- con's
		- visibly not pretty
	- pro's
		- crazy easy to expand

[11.28.13] neuron (Alpha) v.0.013
- lots of cleanup
	- reorganized U.I. class
		- still need to order U.I. action numbers
		- reordered to menu/sub menu layout
	- removed U.I. handler/thread 
		- not needed with new U.I. structure
- Groups now automatically save on creation/deletion of a group
	- removed menu option to save groups (not needed)
- 'move' neuron sub menu does not change to 'place' as planned
- neuron types still in development.

[11.24.13] neuron (Alpha) v.0.012
- changed bot movement to wrap around screen, seeing as there are no wall 
sensors yet.
- added some code documentation
- BrainStorming an idea for a script neuron type
- [side note] i have to finish neuron type selection before moving on...
	- There are still several things that need to be worked out with neuron
	types
		- UI will be the easiest as when one creates/adds a neuron a popup 
		menu will
			need to be navigated to select the neurons type.
		- Neuron properties will need to include editing the type of neuron
		- Neuron types will need a way to alter the finer attributes of 
		each type's properties
		- I just had a thought that would completely redesign neuron 
		properties
			- instead of a U.I. (including sliders check boxes and such)
have a list of the neurons properties and make them editable appropriately.
this would make the expansion of customization to each neuron much easier.
Right now it might be slower because each edit of a property of a neuron 
would require selecting: neuron icon >>> neuron properties >>> select the
desired property to edit >>> edit it >>> confirm with o.k.
5 steps is on the edge of annoying(could be very, i have yet to use it 
actively), so must be reduced somehow.
				
				
[11.23.13] neuron (Alpha) v.0.011
- created menu hierarchy for Neuron Type selecting
- removed generation count
- removed simulation step count
- removed selected bot display of neural network (does not work with 
neuron types)

[11.22.13] neuron (Alpha) v.0.010
- added frequency to food for future input update

[11.21.13] neuron (Alpha) v.0.009
- added 
	- food sense neuron type handling into bot engine.
	- enemy sense neuron type handling into bot engine.
- need to fix bot cloning/copying to include neuron types (bot global 
engine and U.I.).
- need to change group naming convention for placing and deleting a group.

[11.20.13] neuron (Alpha) v.0.009
- adding some game input types into the bot engine
	- heart
	- health
- added game outputs into bot engine
	- turn
		- left
		- right
		- inhibit left
		- inhibit right
		- inhibit turn
	- move
		- forward
		- backward
		- inhibit forward
		- inhibit backward
		- inhibit move

[11.19.13] neuron (Alpha) v.0.008
- changed U.I. from bugs to bots
- made layer below and above active layer, visible
- changed touched point cursor to x rather then a circle
- changed selection marker to + rather then a circle
- updated read me, needs work.

[11.18.13] neuron (Alpha) v.0.007
- added button to U.I. for type editing
	- still trying to decide the best way to handle type selection as 
	their can be many sub groups.
- internally renamed 'bug' to 'Bot'
	- updated naming conventions(i'm sure i have missed some).
	- U.I. needs updating.
	- updated save to reflect change.

[11.16.13] neuron (Alpha) v.0.006
- added new neuron type system
	- it is now more flexible and expandable

[11.16.13] Brain-storming (notes/comments)
- rename bugs to bots (this just makes sense, and less confusing)
- neuron types are going to make neuron save data variable 
	- Array List should be used to keep track of neuron type data as 
	different types will need/use different amounts of parameters.
- I get very excited when I think of the first release(whenever that 
happens).
	- the thought of threading becomes very clear, because of natural 
	parallelization to neurons.
	- I keep thinking that one well developed cluster can give rise to 
	intelligence.
		- The key is to find good connection growth code. (which might 
		change or have a greater degree of control via options)
	- I am also continuing to grasp the idea of 'neural' as a stand alone 
	programming construct with all the flexibility of java.
		- This will take a great amount of thought
			- mostly as to how neurons will fill parameters to a java 
			command
			- also because of neural parallelization how would numeric 
			variables find there place.
			- I also think the learning curve for this is going to be 
				troublesome for traditional coders, but i have hopes that 
				the trouble of learning will be worth it.
			- basic math is also a concern
				- variables and math can be done as of now but requires a 
				great deal of work
					- comparable to the construction of hardware mechanics
					to handle the operations.
					- I would like to find some way of making it simple.

[11.13.13] neuron (Alpha) v.0.005
- cluster
	- save/load cluster added
	
[11.12.13] Brain-storming (notes/comments)
- stuck on neuron properties layout
	- integrating types into properties
		- button -> list ?
		- list view?
		- other?
- started building 'save' data for a cluster

[11.11.13] neuron (Alpha) v.0.004
- fixed goto layer
- added some ground work for neuron types
	- added type id's
		- Normal
		- growth Output
		- growth Input
		- growth Neutral
		- Virtual
		- Game Input
		- Game Output
	- fixed save/load
		- neuron type
		- neuron type destination
	- added getter for game inputs/outputs in neural net
- added some ground work for clusters
	- created Cluster class

[11.10.13] neuron (Alpha) v.0.003
- To-do
	- add neuron types
		- fix neuron properties dialog
		- fix logic for the different types
		- fix save/load
	- add steps to begin work on clusters
		- more thought is needed for clusters
			- clusters will replicate on higher layers but how will it work
			 with sub clusters.
				- N'th dimensionally traversal
				- cluster id's
				- trajectories
				- other
			- how will virtual neurons work
		- fix save/load
- after these updates i think a release is in order.

[11.09.13] neuron (Alpha) v.0.003
- added layer traversal
	- done
		- fixed connections and neurons showing up no matter what layer is
			active
		- fixed neurons being created always at layer 0.
		- fixed moving selection between layers
		- fixed copy section between layers
		- fixed placing groups between layers
		- fixed multi-layer selection for move
		- fixed multi-layer selection for copy
		- fixed save/load to include layers
		- fixed better visualization for multi-layer selection

[11.08.13] neuron (Alpha) v.0.002
- added icons to menus
- removed menu text

[EDITED 11.7.13]
- [ADDED] CHANGELOG.md
	1. Look for progress updates there.
	
[EDITED 11.3.13]
- [FIXED] Note: There are problems running this in Eclipse
	(trying to fix this), AIDE works great.
	1. [DONE] Eclipse works great now.
- [FIXED] Play / Pause on 'Play view' title menu option.
- [REMOVED] Play>> / Paused|| in 'Play view' draw.
	1. [DONE] remove once the title menu option is fixed.
- [ADDED] 'delete' to 'Input order editor'
- [UPDATED] README.md

[11.01.13] neuron (Alpha) v.0.001 
- GitHub release
- git fixes
