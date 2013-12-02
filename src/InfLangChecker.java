import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class InfLangChecker {
	
	private String langDescriptionFileName = "";
	private ArrayList<Vertex> graph = null;
	private final String START = "S";
	private final String END = "F";
	
	
	public InfLangChecker()
	{
		this("");
	}
	
	public InfLangChecker(String fileName)
	{
		graph = new ArrayList<Vertex>();
		
		SetLangDescriptionFileName(fileName);
		
		BuildGraph(fileName);
		
		//this.Display();
	}
	
	
	public boolean CheckInfinite()
	{
		return true;
	}
	
	public boolean BuildGraph(String fileName)
	{
		BufferedReader br = null;
		boolean builtTree = false;
		
		try {
			String lineBuff;
 
			br = new BufferedReader(new FileReader(fileName));
 
			while ((lineBuff = br.readLine()) != null) {
				System.out.println(lineBuff);
				
				this.AddDescriptionToGraph(lineBuff);
				
				this.Display();
			}
			
			builtTree = true;
			
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
		
		return builtTree;
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
		  System.out.println("Parsing token: "+part);
		  
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
	
	public String GetLangDescriptionFileName()
	{
		return this.langDescriptionFileName;
	}
	
	private void SetLangDescriptionFileName( String fileName )
	{
		this.langDescriptionFileName = fileName;
	}
	
	public String ToString()
	{
		String info = "";
				
		for (int i = 0; i < graph.size(); i++) {
			info += graph.get(i).ToString();
		}
				
		return info;
	}
	
	public String Display()
	{
		String info = "GRAPH GENERATED FROM: " + langDescriptionFileName + "\n";

		info += this.ToString();
				
		System.out.println(info);
		
		return info;
	}
	
}
