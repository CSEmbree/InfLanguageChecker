import java.util.HashSet;
import java.util.Random;
import java.util.Set;


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
		boolean isInfinite = false; //result of method
		
		Set<String> allPossibleWords = GeneratePossibleWords(); //all possible combinations of alphabet between NUM(V) and NUM(V)*2 

//		System.out.println("SIZE: "+allPossibleWords.size()); //TEST
//		for (String string : allPossibleWords) {
//			System.out.println(string); //TEST
//		}

//		//Hard coded testing for specific words
//		String testWord = "bbb"; //word for testing specific strings for a graph
//		int lowerLengthLimit = graph.GetNumberOfNodesInGraph();
//		int upperLengthLimit = lowerLengthLimit*2;
//		int wordLength = testWord.length();
//
//		isValidWord = CheckWordAgainstGraph( testWord );
//
//		if( (wordLength >= lowerLengthLimit) && (wordLength <= upperLengthLimit) )
//			isValidLength = true;
//
//		System.out.println("isValidWord: "+isValidWord+", isValidLength: "+isValidLength);
//
//		if(isValidWord == true && isValidLength == true)
//			isInfinite = true;
//		 
		
		
		int lowerLengthLimit = graph.GetNumberOfNodesInGraph();
		int upperLengthLimit = lowerLengthLimit*2;
		int wordLength = 0;
		
		for (String word : allPossibleWords) {
			wordLength = word.length();
//			System.out.println("Checking word: "+word); //TEST

			if( (wordLength >= lowerLengthLimit) && (wordLength <= upperLengthLimit) )
			{
				if(CheckWordAgainstGraph(word) == true)
				{
//					System.out.println("FOUND with word: "+word); //TEST
					isInfinite = true;
					break;
				}
			}	
		}

		return isInfinite;
	}

	
	private Set<String> GeneratePossibleWords()
	{
		int lowerSizeLimit = this.graph.GetNumberOfNodesInGraph();

		Set<String> allPossibleWords = new HashSet<String>();
		Set<String> generatedWords;

		for (int i = lowerSizeLimit; i <= lowerSizeLimit*2; i++) {
			generatedWords = CreateWordsForLength(i);
			for (String word : generatedWords) {
				allPossibleWords.add(word);
			}
		}

		return allPossibleWords;
	}

	
	private Set<String> CreateWordsForLength(int n)
	{
		Set<String> set = new HashSet<String>();
		Random rand = new Random();
		rand.setSeed(System.nanoTime());

		double expectedSize = Math.pow(2, n);
		char [] chars = {'a', 'b'};
		String prospect = "";

		while (set.size() < expectedSize)
		{
			for (int i = 0; i < n; i++)
				prospect += chars[rand.nextInt(2)];

			set.add(prospect);
			prospect = "";
		}

		return set;
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
