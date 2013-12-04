//Cameron S. Embree
import java.util.ArrayList;


public class Vertex {
	
	String id = ""; //id for a given vertex, is the same as the state
	//a state can be a START, TERM, START & TERM, or none. Used to indicate where to start graph traversal and when we have reached a terminal state
	boolean startFlag = false; 
	boolean endFlag = false;
	ArrayList<Edge> edges = null; //all edges that can be taken from this vertex to other vertices.
	
	
	//default constructor
	public Vertex()
	{
		this("", false, false, null);
	}
	
	
	//overloaded constructor - record all the information about a vertex and it's edges attached to it
	public Vertex(String ID, boolean startFlag, boolean endFlag, ArrayList<Edge> Edges)
	{
		this.edges = new ArrayList<Edge>();
		
		SetID(ID);
		SetStartFlag(startFlag);
		SetEndFlag(endFlag);
		SetEdges(Edges);
	}

	
	//mutator method
	private void SetID(String id)
	{
		this.id = id;
	}
	
	
	//mutator method
	private void SetStartFlag(boolean startFlag)
	{
		this.startFlag = startFlag;
	}
	
	
	//mutator method
	private void SetEndFlag(boolean endFlag)
	{
		this.endFlag = endFlag;
	}
	
	
	//mutator method
	private void SetEdges(ArrayList<Edge> edges)
	{	
		this.edges = new ArrayList<Edge>(edges);
	}
	
	
	//accessor method - ID is the same as the State (this is also the source from an edge from this vertex).
	public String GetID()
	{
		return this.id;
	}
	
	
	//accessor method
	public boolean GetStartFlag()
	{
		return this.startFlag;
	}
	
	
	//accessor method
	public boolean GetEndFlag()
	{
		return this.endFlag;
	}
	
	
	//accessor method
	public ArrayList<Edge> GetEdges()
	{
		return this.edges;
	}
	
	
	//return edges that require a specific cost from this vertex
	public ArrayList<Edge> GetEdges(String cost)
	{
		ArrayList<Edge> relevantEdges = new ArrayList<Edge>();
		
		for (Edge edge : this.edges) {
			if(edge.GetCost().equals(cost)) {
				relevantEdges.add(edge);
			}
		}
		
		return relevantEdges;
	}
	
	
	//accessor method - return nicely formatted description of this vertex
	public String ToString()
	{
		String info = "";
		
		info += "Vertex id: " + this.GetID() + ", START: " + this.GetStartFlag() + ", END: " + this.GetEndFlag() + "\n";
		
		for (int i = 0; i < edges.size(); i++) {
			info += "\t" + edges.get(i).ToString() + "\n";
		}
		
		return info;
	}
	
	
	//accessor method - same functionality of ToString but also displays contents.
	public String Display()
	{
		String info = this.ToString();
		
		System.out.println(info);
		
		return info;
	}

}
