//Cameron S. Embree
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DirectedGraph {

	String langDescriptionFileName; //file name of the language (graph/TM) description
	ArrayList<String> alphabet; //all the characters for this a language
	ArrayList<Vertex> graph; //set of all vertices containing edge relationships and cost requirments for traversal
	private final String START = "S"; //file format quantifier for a START (initial) vertex
	private final String END = "F"; //file format quantifier for a END (termination) vertex

	
	//default constructor
	public DirectedGraph()
	{
		this("");
	}

	//overloaded constructor
	public DirectedGraph(String fileName)
	{
		this.alphabet = new ArrayList<String>();
		this.graph = new ArrayList<Vertex>();

		SetLangDescriptionFileName(fileName);

		SetAlphabetLetters();

		if ( BuildGraph(fileName) == false)
			System.out.println("ERROR:: No graph was created!");
	}

	//mutator - hard coded creation of language letters
	private void SetAlphabetLetters()
	{
		this.alphabet = new ArrayList<String>(GenerateLanguageLetters());
	}

	
	//business method - hardcoded for sake of testing graph, generates array of the letters in our test language
	private ArrayList<String> GenerateLanguageLetters()
	{
		//could be made more generically by reading from file
		ArrayList<String> letters = new ArrayList<String>();

		letters.add("a");
		letters.add("b");

		return letters; 
	}

	
	//Open and read a graph description from a file called 'fileName' -assumed to be in current directory
	public boolean BuildGraph(String fileName)
	{
		BufferedReader br = null;
		boolean builtGraph = false;

		try {
			String lineBuff;

			br = new BufferedReader(new FileReader(fileName));

			while ((lineBuff = br.readLine()) != null) {

				//System.out.println(lineBuff); // TEST - displays the current raw vertex data being interpreted
				this.AddDescriptionToGraph(lineBuff);
				//this.Display(); //TEST - displays the graph after each vertex is added
			}

			builtGraph = true;

		} catch (IOException e)  {
			System.out.println("ERROR: An issue occured while trying to OPEN the file: "+fileName);
			e.printStackTrace();
		} finally {
			try {
				//clean up after reading the whole graph description
				if (br != null) br.close();
			} catch (IOException ex) {
				System.out.println("ERROR: An issue occured while trying to CLOSE the file: "+fileName);
				ex.printStackTrace();
			}
		}

		return builtGraph;
	}

	
	//business method - parse a description of a vertex where
	// A-B-Cc-...-Cc
	// A = some origin numerical state
	// B = identifier for type of State: Start(S), Final/Terminal(F), Neutral(N)
	// C = some destination numerical state
	// c = the cost to get from the source to destination state via that edge.
	// EX: 0-S-2b-1a
	//     State 0 is a START state. State 0 has two edges from 0 to 2 with cost b and 0 to 1 with cost a.
	private void AddDescriptionToGraph(String description)
	{
		//vertex info needed to create graph
		Vertex vertex = null;
		String vertexId = "";
		boolean startFlag = false;
		boolean endFlag = false;

		//edge info needed for vertex
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Edge edge = null;
		String source = "";
		String dest = "";
		String cost = "";

		//parse the "-" delimiterized text file and create the graph from the vertex and edge info
		String[] tokens = description.split("-");
		int index = 0;

		for (String part : tokens) {
			//System.out.println("Parsing token: "+part); //TEST - display the raw vertex data being parsed

			switch (index) {
			case 0: //Edge number: 0, 1, 2,...
				vertexId = part;
				break;
			case 1: //Initial and terminal vertices
				startFlag = (part.toLowerCase()).contains(START.toLowerCase());
				endFlag = (part.toLowerCase()).contains(END.toLowerCase());
				break;
			default: //zero or many edges
				source = vertexId;
				dest = part.substring(0, 1); //first char
				cost = part.substring(1, 2); //second char

				//create a new edge with sourse, destination, and cost
				edge = new Edge(source, dest, cost);
				edges.add( edge );

				break;
			}

			index++; //counter for input parsed
		}

		//add collected information to the graph
		vertex = new Vertex(vertexId, startFlag, endFlag, edges);

		AddVertexToGraph(vertex);
	}

	
	//mutator method
	private void AddVertexToGraph(Vertex v)
	{
		this.graph.add(v);
	}


	//business method - decides whether a given word can reach a TERMINAL state in a TM (graph).
	public boolean CheckWordAgainstGraph(String word)
	{
		boolean valid = false; //result of if this word is accepted by this graph
		int initStateIndex = 0; //init state is always state 0 by construction

		//array of each state we are currently at. 
		//Starts at initial state but can take multiple paths depending on non-deterministic graph
		ArrayList<Integer> stateIndexes = new ArrayList<Integer>();
		stateIndexes.add(initStateIndex); //init state

		//keep track of new possible paths we could take if there are multiple edges with the same cost
		ArrayList<Integer> newPathStates = new ArrayList<Integer>();
		
		//keep track of indices for paths we are following that have crashed so we can clean them up after 1 full move through each path
		ArrayList<Integer> crashedPathIndexes = new ArrayList<Integer>();

		
		ArrayList<Edge> currentUseableVertexEdges = null; //container for all edges available to take for a given cost at a given vertex.
		String currentWordLetter; //container for each letter we attempt to parse in a word
		int currentPathState; //convenient code readability holder for the current state of a path
		int nextState; //convenient code readability holder for the next state we reach based on what edge we traverse


		//traverse the whole word and then check the final state for TERMINAL state
		for (int wordIndex = 0; wordIndex < word.length(); wordIndex++) {
			currentWordLetter = Character.toString(word.charAt(wordIndex));

			
			//account for each possible path we can take through our graph. 
			//Multiple paths could be generated from two edges from a vertex having the same cost.
			for (int pathIndex = 0; pathIndex < stateIndexes.size(); pathIndex++) {
				currentPathState = stateIndexes.get(pathIndex);
				
				//collect all edges we could take at a vertex (state) with the cost we have (currentWordLetter)
				currentUseableVertexEdges = this.graph.get(currentPathState).GetEdges(currentWordLetter);

				//Go through all edge options that can be made by keeping track of new path's as needed.
				if(currentUseableVertexEdges.size() > 0) {
					
					for (int edgeIndex = 0; edgeIndex < currentUseableVertexEdges.size(); edgeIndex++) {
						nextState = Integer.parseInt(currentUseableVertexEdges.get(edgeIndex).GetDest());

						if(edgeIndex == 0) {
							//Choose the first edge we already know about as being our first choice of edges to take
							stateIndexes.set(pathIndex, nextState);
						} else {
							//any remaining options for edges to take need to be new possible paths.
							//multiple paths can be taken because of a possible NFA graph, so we account for all possible edge choices.
							newPathStates.add(nextState); 
						}
					}
				}
				else {
					//no edge was available
					crashedPathIndexes.add(pathIndex);
				}
			}
			

			//remove paths that have reached a crash state -- must not have been the choice of edge to take at a previous vertex
			for (Integer crashedPathIndex : crashedPathIndexes) {
				stateIndexes.remove((int)crashedPathIndex); //remove index of crashed path				
			}
			crashedPathIndexes.clear();	
			
			
			//add any new edge choices as unique paths.
			//again, these stem from the potential NonDeterministic behavior at a vertex
			stateIndexes.addAll(newPathStates);
			newPathStates.clear();	
		}
		
		
		//after we have computed all possible path choices through a graph based on our word,
		//  then check the final state of all possible path choices. Only 1 of these possible paths
		//  that our word took needs to be a TERMINAL state.
		for (int pathIndex = 0; pathIndex < stateIndexes.size() && valid==false ; pathIndex++){
			int currentState = stateIndexes.get(pathIndex); 

			if( this.graph.get(currentState).GetEndFlag() == true ) {
				valid = true;
			}
		}

		return valid;
	}


	//accessor method
	public String GetLangDescriptionFileName()
	{
		return this.langDescriptionFileName;
	}

	
	//mutator method
	private void SetLangDescriptionFileName( String fileName )
	{
		this.langDescriptionFileName = fileName;
	}

	
	//accessor method
	public int GetNumberOfNodesInGraph()
	{
		return this.graph.size();
	}

	
	//accessor method - return nicely formatted description of this graph
	public String ToString()
	{
		String info = "";

		for (int i = 0; i < this.graph.size(); i++) {
			info += this.graph.get(i).ToString();
		}

		return info;
	}

	
	//accessor method - same functionality of ToString but also displays contents.
	public String Display()
	{
		String info = "GRAPH GENERATED FROM: " + langDescriptionFileName + "\n";

		info += this.toString();

		System.out.println(info);

		return info;
	}

}
