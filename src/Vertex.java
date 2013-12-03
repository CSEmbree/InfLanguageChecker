import java.util.ArrayList;


public class Vertex {
	
	String id = "";
	boolean startFlag = false;
	boolean endFlag = false;
	ArrayList<Edge> edges = null;
	
	
	public Vertex()
	{
		this("", false, false, null);
	}
	
	
	public Vertex(String ID, boolean startFlag, boolean endFlag, ArrayList<Edge> Edges)
	{
		this.edges = new ArrayList<Edge>();
		
		SetID(ID);
		SetStartFlag(startFlag);
		SetEndFlag(endFlag);
		SetEdges(Edges);
	}

	
	private void SetID(String id)
	{
		this.id = id;
	}
	
	
	private void SetStartFlag(boolean startFlag)
	{
		this.startFlag = startFlag;
	}
	
	
	private void SetEndFlag(boolean endFlag)
	{
		this.endFlag = endFlag;
	}
	
	
	private void SetEdges(ArrayList<Edge> edges)
	{	
		this.edges = new ArrayList<Edge>(edges);
	}
	
	
	public String GetID()
	{
		return this.id;
	}
	
	
	public boolean GetStartFlag()
	{
		return this.startFlag;
	}
	
	
	public boolean GetEndFlag()
	{
		return this.endFlag;
	}
	
	
	public ArrayList<Edge> GetEdges()
	{
		return this.edges;
	}
	
	
	//return edges that require specific costs
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
	
	
	public String ToString()
	{
		String info = "";
		
		info += "Vertex id: " + this.GetID() + ", START: " + this.GetStartFlag() + ", END: " + this.GetEndFlag() + "\n";
		
		for (int i = 0; i < edges.size(); i++) {
			info += "\t" + edges.get(i).ToString() + "\n";
		}
		
		return info;
	}
	
	
	public String Display()
	{
		String info = this.ToString();
		
		System.out.println(info);
		
		return info;
	}

}
