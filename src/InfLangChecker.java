//Cameron S. Embree
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class InfLangChecker {

	private DirectedGraph graph = null; //TM that defines our language. We test this graph to see if the language is finite or infinite.


	//default constructor
	public InfLangChecker()
	{
		this("");
	}

	
	//overloaded constructor - create a TM from a language description so we can test that graph for being a finite or infinite language
	public InfLangChecker(String fileName)
	{
		this.graph = new DirectedGraph(fileName);	
	}

	
	//business method - Checks if a TM is infinite by checking if ANY one combination of letters of length n to 2n reaches a Terminal state
	//  Any working word means this language is infinite by Theorem 19 in the book (pg. 215)
	public boolean IsInfinite()
	{
		boolean isInfinite = false; //result of checking graph for all words for one that reaches a terminal state
		
		Set<String> allPossibleWords = GeneratePossibleWords(); //all possible combinations of alphabet between NUM(V) and NUM(V)*2 
		
		
		int lowerLengthLimit = graph.GetNumberOfNodesInGraph(); //by Theorem 19, lower limit of possible words is n, where n=number of graph verticies.
		int upperLengthLimit = lowerLengthLimit*2; //by Theorem 19, upper limit of possible words is 2*n
		int wordLength = 0; //container for making sure word length is valid 
		
		//NOTE: all our words will be within range because we only generate words between n and 2*n (NOTE 1)
		
		for (String word : allPossibleWords) {
			wordLength = word.length();

			if( (wordLength >= lowerLengthLimit) && (wordLength <= upperLengthLimit) ) //not needed for currently implimentation, see (NOTE 1)
			{
				//check to see if a word reaches a terminal state in the TM (graph)
				if(CheckWordAgainstGraph(word) == true)
				{
					isInfinite = true; //stop when we find a single word that works
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

	
	//Generate all possible strings between n and n^2 to test these strings in our TM graph of the language.
	//  If any word of all possible combinations of the alphabet for a lgnuage for for this TM with lengths between n and 2n,
	//  then this language is infinite.
	//NOTE: This is the ugliest, least creative, & least efficient possible way of generating all possible strings between n and 2n.
	//         This method was not the goal of this assignment and it worked for testing graph code. 
	// TODO - improve method to not waste cycles - each loop should generate a new unique sequence of the alphabet
	private Set<String> CreateWordsForLength(int n)
	{
		Set<String> set = new HashSet<String>(); //list of all possible combination of the language with length between n and 2n.
		Random rand = new Random(); //used to randomly choose an 'a' or 'b' from language
		rand.setSeed(System.nanoTime());

		double expectedSize = Math.pow(2, n); //maximum size of a word that, if it passed our TM, would show this is an inifinite language
		char [] chars = {'a', 'b'}; //hardcoded alphabet for this language. TODO - retrieve alphabet from Directed Graph.
		String prospect = ""; //container for randomly generated strings of alphabet letters.

		
		//generating strings of every length of unique alphabet characters combinations between n and 2n
		// CURRENT TERRIBLE IMPLIMENTATION - keep randomly creating strings until we have generated all expected combos. 
		//   If we find a previously generated combo, then try again
		while (set.size() < expectedSize)
		{
			for (int i = 0; i < n; i++)
				prospect += chars[rand.nextInt(2)];

			set.add(prospect); //only add new unique arrangments of alphabet to set.
			prospect = "";
		}

		return set;
	}

	
	public boolean CheckWordAgainstGraph(String word)
	{
		return this.graph.CheckWordAgainstGraph(word);
	}


	//accessor method - return nicely formatted description of this graph we want to check the scope of
	public String ToString()
	{
		String info = this.graph.ToString();

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
