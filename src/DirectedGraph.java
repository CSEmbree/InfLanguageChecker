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
		int stateIndex = 0; //init state is always state 0 by construction
		
		ArrayList<Edge> currentVertexEdges = null;
		String currentWordLetter;
		
		
		//TODO - check if this 'word' gets to an end state in our Directed Graph (FA)
		
		for (int wordIndex = 0; wordIndex < word.length(); wordIndex++) {
			
			currentWordLetter = Character.toString(word.charAt(wordIndex));
			currentVertexEdges = this.graph.get(stateIndex).GetEdges();
			
			for (int edgeIndex = 0; edgeIndex < currentVertexEdges.size(); edgeIndex++) {
				
				if(currentVertexEdges.get(edgeIndex).GetCost().equals(currentWordLetter)) {
					//TODO - force choose a new edge if the current one didnt work in the end by keeping state the same
					
					stateIndex = Integer.parseInt(currentVertexEdges.get(edgeIndex).GetDest());
					break;
				}
			}
		}
		
		
		if( this.graph.get(stateIndex).GetEndFlag() == true)
			valid = true;
		
		
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
	
	public int GetLowerInputSizeRequirment()
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
