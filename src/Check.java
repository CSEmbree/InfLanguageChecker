

public class Check {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String fileName1 = "hw3q4_i.txt"; //HW3Q4 (i)
		String fileName2; //HW3Q4 (ii)
		String fileName3; //HW3Q4 (iii)
		String fileName4; //HW3Q4 (iv)
		
		boolean isFinite = false;
		
		//TEST 1
		System.out.println("Building graph from file: "+fileName1);
		
		InfLangChecker ilc = new InfLangChecker(fileName1);
		ilc.Display(); //print graph after reading in from file

		
		System.out.println("Determining the scope of HW3Q4 (i) from file: "+fileName1);
		isFinite = ilc.IsInfinite();
		
		System.out.println("RESULT: "+FormatResult(isFinite));
		
		
		//TODO - TEST 2, 3, 4

	}
	
	public static String FormatResult(boolean isFinite)
	{
		String result = "";
		
		if (isFinite == true)
			result += "finite";
		else 
			result += "infinite";
		
		return result;
	}

}
