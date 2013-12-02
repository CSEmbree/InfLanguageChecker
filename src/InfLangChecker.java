import java.util.ArrayList;

import sun.font.TrueTypeFont;


public class InfLangChecker {
	
	private DirectedGraph graph = null;
	
	
	public InfLangChecker()
	{
		this("");
	}
	
	public InfLangChecker(String fileName)
	{
		this.graph = new DirectedGraph(fileName);	
	}
	
	
	public boolean IsInfinite()
	{
		boolean IsInfinite = true;
		
		ArrayList<String> possibleWords = GeneratePossibleWords();
		
		//TODO - determine finite or infinite
		/* 
		 * 
		 * for(int i = 0; i < possibleWords.size() && IsInfinite == false; i++) {
		 * 	IsInfinite = CheckWordToGraph( possibleWords.getIndex(i) );
		 * 	
		 * }
		 * 
		 * return isFinite;

		*/
		String testWord = "ab"; //word for testing specific strings for a graph
		
		
		IsInfinite = CheckWordAgainstGraph( testWord );
		System.out.println("IsInfite: "+IsInfinite);
		
		
		return IsInfinite;
	}
	
	private ArrayList<String> GeneratePossibleWords()
	{
		ArrayList<String> possibleWords = null;
		int lowerSizeLimit = this.graph.GetLowerInputSizeRequirment();
		int upperSizeLimit = lowerSizeLimit * 2;
		
		CreateWord(possibleWords, lowerSizeLimit, upperSizeLimit);
		
		return possibleWords;
	}
	
	private void CreateWord(ArrayList<String> possibleWords, int lowerSizeLimit, int upperSizeLimit)
	{
		//TODO - recursivly create all words with length between lower and upper limit with the letters a,b 
	}
	
	public boolean CheckWordAgainstGraph(String word)
	{
		return this.graph.CheckWordAgainstGraph(word);
	}
	
	
	public String ToString()
	{
		String info = this.graph.ToString();
				
		return info;
	}
	
	public String Display()
	{
		String info = this.ToString();
				
		System.out.println(info);
		
		return info;
	}
	
}
