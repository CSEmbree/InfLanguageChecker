//Cameron S. Embree


public class Edge {
	
	String source = "";
	String dest = "";
	String cost = "";
	
	
	//default constructor
	public Edge()
	{
		this("","","");
	}
	
	//overloaded constructor - record the traversal info for an edge including start, end, and traversal cost
	public Edge(String source, String dest, String cost)
	{
		SetSource(source);
		SetDest(dest);
		SetCost(cost);
	}
	
	
	//mutator method
	private void SetSource(String source)
	{
		this.source = source;
	}
	
	
	//mutator method
	private void SetDest(String dest) 
	{
		this.dest = dest;
	}
	
	
	//mutator method
	private void SetCost(String cost)
	{
		this.cost = cost;
	}
	
	
	//accesor method
	public String GetSource()
	{
		return this.source;
	}
	
	
	//accesor method
	public String GetDest()
	{
		return this.dest;
	}
	
	
	//accesor method
	public String GetCost()
	{
		return this.cost;
	}
	
	
	//accessor method - return nicely formatted description of this Edge
	public String ToString()
	{
		String info = "EDGE:: "+this.source+" to "+this.dest+" with: "+this.cost;
		
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
