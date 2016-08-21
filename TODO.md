###What's next

There are many planned features which include:

[Feature/Glitch/To-do/Thought] + [Priority]


  
- To-Do:	[*****] include cluster and selection boxes in selection rotation
- To-Do:	[*****] keyboard shortcuts
- To-Do:	[.....] file versions for changing save data changes.
- To-Do:	[*****] add option to spread connections from Neuron selection A to Neuron selection B ( set properties first )
- To-Do:	[***..] Need to come up with a way to easily navigate large collections of groups and cluster containers (option to add categories?)
- To-Do:	[*****] Need better view of layers and connections, linking boxes to layer(connection feature-
					 click on it and it takes you to that layer? will it interfere with adding new neurons?), how to display neurons on multiple layers

[Adding]
{ dunno
	1. Implement Neuron types
		- Growth 
			1. How they work:
				- Growth Output
				- output will grow to input and neutral.
				- Growth Neutral
				- neutral will grow to input and other neutrals.
				- Growth Input
				- input does not grow, and is available for more control.
			2. Selection
				- Grow On This Growth neurons Layer
				- Grow To Cluster and its children
				- Grow In Layer Range
				- (+/-) min/max range of this layer.
				- Grow To All Layers
		- Virtual
			1. How virtual neurons work:
				- all neurons connected to a virtual neuron will be affected when a cluster replicates.
				- the virtual neuron will be removed once a child is created, the child will still have it.
			2. Selection
				- Link Source To Child
					1. link parent to child on child creation.
				- Link Child To Source
					1. link child to parent on child creation.
				- Link Source To Each Child
					1. links the source neuron to each child.
				- Link Each Child To Source
					1. links each child to the source neuron.
				- Combine Source With Child
					1. combine source neuron to child neuron on cluster child creation, the child neuron inherits the sources input connections.
				- Cluster Command
					1. How it works:
						- Commands manipulate clusters in some way
					2. Selection
						- Cluster Command Replicate
						- Cluster Command Delete
	4. create neuron that will trigger a cluster to replicate or delete.
		- replication will occur on/with layer increment of z-axis depending on the nature of clusters.
		- deletion will occur on layers above deleted layer if no connections remain? this is confusing.
	5. virtual neurons or connections for auto connecting layers or clusters?
		- visually distinguishable from other neurons and connections
		- designate a neuron a virtual one
		- no processing is done with virtual neurons and connections
	6. extending program input/output to neurons
		- touch: where the user touched
}

I have done a project with growing connections and plan on including it into neural.
There will be modifications to make it much faster, but here is a demo for how it might look.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=441oF-_G2KE
" target="_blank"><img src="http://img.youtube.com/vi/441oF-_G2KE/0.jpg" 
alt="connection growth demo" width="240" height="180" border="10" /></a>

  1. they grow and destroy their own connections.
    * [neurons]
	    1. growth neurons will only grow to other growth neurons,
	    2. more specifically Output Growth Neurons(OGN) will grow 
    	  * Input Growth Neurons(IGN) 
    	  * Neutral Growth Neurons(NGN).
	    3. IGN will not grow connections?
	    4. NGN will grow to any of the neuron growth types?
	    5. will growth neurons grow to other layers?
    * [connections]
	    1. the handling of these connections will be different then what is already done.
	    2. they will strengthen when firing together
	    3. weaken to disconnect when not firing together.
	
 	
(Updates will continue as things are completed)

  
