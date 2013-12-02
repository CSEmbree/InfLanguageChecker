

public class Edge {
	
	String source = "";
	String dest = "";
	String cost = "";
	
	
	public Edge()
	{
		this("","","");
	}
	
	public Edge(String source, String dest, String cost)
	{
		SetSource(source);
		SetDest(dest);
		SetCost(cost);
	}
	
	
	private void SetSource(String source)
	{
		this.source = source;
	}
	
	private void SetDest(String dest) 
	{
		this.dest = dest;
	}
	
	private void SetCost(String cost)
	{
		this.cost = cost;
	}
	
	public String GetSource()
	{
		return this.source;
	}
	
	public String GetDest()
	{
		return this.dest;
	}
	
	public String GetCost()
	{
		return this.cost;
	}
	
	public String ToString()
	{
		String info = "EDGE:: "+this.source+" to "+this.dest+" with: "+this.cost;
		
		return info;
	}
	
	public String Display()
	{
		String info = this.ToString();
		
		System.out.println(info);
		
		return info;
	}
	
}
