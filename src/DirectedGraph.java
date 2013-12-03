import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DirectedGraph {

	String langDescriptionFileName;
	ArrayList<String> alphabet;
	ArrayList<Vertex> graph;
	private final String START = "S";
	private final String END = "F";


	public DirectedGraph()
	{
		this("");
	}

	
	public DirectedGraph(String fileName)
	{
		this.alphabet = new ArrayList<String>();
		this.graph = new ArrayList<Vertex>();

		SetLangDescriptionFileName(fileName);

		SetAlphabetLetters();

		if ( BuildGraph(fileName) == false)
			System.out.println("ERROR:: No graph was created!");
	}


	private void SetAlphabetLetters()
	{
		this.alphabet = new ArrayList<String>(GenerateLanguageLetters());
	}

	
	private ArrayList<String> GenerateLanguageLetters()
	{
		//could be made more generically by reading from file
		ArrayList<String> letters = new ArrayList<String>();

		letters.add("a");
		letters.add("b");

		return letters; 
	}

	
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
				if (br != null) br.close();
			} catch (IOException ex) {
				System.out.println("ERROR: An issue occured while trying to CLOSE the file: "+fileName);
				ex.printStackTrace();
			}
		}

		return builtGraph;
	}

	
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
				dest = part.substring(0, 1);
				cost = part.substring(1, 2);

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

	
	private void AddVertexToGraph(Vertex v)
	{
		this.graph.add(v);
	}


	public boolean CheckWordAgainstGraph(String word)
	{
		boolean valid = false;
		int initStateIndex = 0; //init state is always state 0 by construction

		ArrayList<Integer> stateIndexes = new ArrayList<Integer>();
		stateIndexes.add(initStateIndex);

		ArrayList<Integer> newPathStates = new ArrayList<Integer>();
		
		ArrayList<Integer> crashedPathIndexes = new ArrayList<Integer>();

		ArrayList<Edge> currentUseableVertexEdges = null;
		String currentWordLetter;
		int currentPathState;
		int nextState;

//		System.out.println("Checking word: "+word); //TEST


		//TODO - check if this 'word' gets to an end state in our Directed Graph (FA)
		for (int wordIndex = 0; wordIndex < word.length(); wordIndex++) {
			currentWordLetter = Character.toString(word.charAt(wordIndex));

			for (int pathIndex = 0; pathIndex < stateIndexes.size(); pathIndex++) {
				currentPathState = stateIndexes.get(pathIndex);
				
//				System.out.println("Checking PATH "+pathIndex+"..."); //TEST

				currentUseableVertexEdges = this.graph.get(currentPathState).GetEdges(currentWordLetter);

//				System.out.println("Found "+currentUseableVertexEdges.size()+" edges from state: "+currentPathState+" with cost: "+currentWordLetter); //TEST
//				for (Edge edge : currentUseableVertexEdges) {
//					System.out.println("\t"+edge.ToString()); //TEST
//				}

				if(currentUseableVertexEdges.size() > 0) {
					
					for (int edgeIndex = 0; edgeIndex < currentUseableVertexEdges.size(); edgeIndex++) {
						nextState = Integer.parseInt(currentUseableVertexEdges.get(edgeIndex).GetDest());

						if(edgeIndex == 0) {
//							System.out.println("Moving PATH: "+pathIndex+"'s state from "+currentUseableVertexEdges.get(edgeIndex).GetSource()+" to "+currentUseableVertexEdges.get(edgeIndex).GetDest()); //TEST

							stateIndexes.set(pathIndex, nextState);

						}
						else {
//							System.out.println("Creating new PATH from state "+currentUseableVertexEdges.get(edgeIndex).GetSource()+" to "+currentUseableVertexEdges.get(edgeIndex).GetDest()); //TEST

							newPathStates.add(nextState);
						}
					}
				}
				else {
//					System.out.println("PATH "+pathIndex+" has CRASHED and will be cleaned up later."); //TEST
					
					crashedPathIndexes.add(pathIndex);
				}
			}
			

			//remove paths that have reached a crash state -- must not have been the choice of path to take at a previous vertex
//			System.out.println("Performing any cleanup that may or may not be needed..."); //TEST
			for (Integer crashedPathIndex : crashedPathIndexes) {
//				System.out.println("\tPATH "+crashedPathIndex+" crashed for recent input, cleaning it up.");				
				stateIndexes.remove((int)crashedPathIndex); //remove index of crashed path				
			}
			crashedPathIndexes.clear();
			
			
			
			//add any new path choices that stemed from NonDeterministic behavior at a vertex
//			System.out.println("Adding any new paths enocuntered...");
			//stateIndexes.addAll(newPathStates); //refered over hardcoded adding but for debugging is commented out
			for (Integer newPathState : newPathStates) {
//				System.out.println("\tCreating new path starting at state "+newPathState+"."); //TEST
				stateIndexes.add(newPathState); //add new paths state to list of ongoing path states
			}
			newPathStates.clear();
			
//			System.out.println("UPDATE: Total Paths: "+stateIndexes.size()+"\n" ); //TEST
		}
		
//		System.out.println("Finished parseing word: "+word+"\n"); //TEST


		for (int pathIndex = 0; pathIndex < stateIndexes.size() && valid==false ; pathIndex++){

			int currentState = stateIndexes.get(pathIndex); 
//			System.out.println("Checking PATH: "+pathIndex+" at state: "+currentState+" for end state"); //TEST

			if( this.graph.get(currentState).GetEndFlag() == true ) {
//				System.out.println("\tFINAL STATE ENCOUNTERED!"); //TEST
				valid = true;
			}
		}

		return valid;
	}


	public String GetLangDescriptionFileName()
	{
		return this.langDescriptionFileName;
	}

	
	private void SetLangDescriptionFileName( String fileName )
	{
		this.langDescriptionFileName = fileName;
	}

	
	public int GetNumberOfNodesInGraph()
	{
		return this.graph.size();
	}

	
	public String ToString()
	{
		String info = "";

		for (int i = 0; i < this.graph.size(); i++) {
			info += this.graph.get(i).ToString();
		}

		return info;
	}

	
	public String Display()
	{
		String info = "GRAPH GENERATED FROM: " + langDescriptionFileName + "\n";

		info += this.toString();

		System.out.println(info);

		return info;
	}

}
