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
	
	
	public boolean IsFinite()
	{
		boolean isFinite = true;
		
		ArrayList<String> possibleWordStrings = GeneratePossibleWords();
		
		//TODO - determine finite or infinite
		//CheckWordToGraph( someWord );
		
		String testWord = "ab"; //word for testing specific strings for a graph
		
		CheckWord( testWord );
		
		return isFinite;
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
	
	public boolean CheckWord(String word)
	{
		boolean valid = false;
		
		//TODO - check if this 'word' gets to an end state in our Directed Graph (FA)
		
		return valid;
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
