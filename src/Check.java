//Cameron S. Embree

public class Check {

	public static void main(String[] args) 
	{
		final String tm_desc_path = "tm_descriptions/"; 
		
		String fileName1 = tm_desc_path+"hw3q4_i.txt";  //HW3Q4 (i)
		String fileName2 = tm_desc_path+"hw3q4_ii.txt"; //HW3Q4 (ii)
		String fileName3 = tm_desc_path+"hw3q4_ii.txt"; //HW3Q4 (iii)
		String fileName4 = tm_desc_path+"hw3q4_iv.txt"; //HW3Q4 (iv)

		boolean isInfinite = false; //result of the infinite language check


		//TEST 1
		System.out.println("Building graph from file: "+fileName1);

		InfLangChecker ilc = new InfLangChecker(fileName1);
		ilc.Display(); //print graph after reading in from file

		System.out.println("Determining the scope of HW3Q4 (i) from file: "+fileName1);
		isInfinite = ilc.IsInfinite();

		System.out.println("RESULT: "+FormatResult(isInfinite)+" language.\n\n");



		//TEST 2
		System.out.println("Building graph from file: "+fileName2);

		ilc = new InfLangChecker(fileName2);
		ilc.Display(); //print graph after reading in from file

		System.out.println("Determining the scope of HW3Q4 (i) from file: "+fileName2);
		isInfinite = ilc.IsInfinite();

		System.out.println("RESULT: "+FormatResult(isInfinite)+" language.\n\n");



		//TEST 3
		System.out.println("Building graph from file: "+fileName3);

		ilc = new InfLangChecker(fileName3);
		ilc.Display(); //print graph after reading in from file

		System.out.println("Determining the scope of HW3Q4 (i) from file: "+fileName3);
		isInfinite = ilc.IsInfinite();

		System.out.println("RESULT: "+FormatResult(isInfinite)+" language.\n\n");




		//TEST 4
		System.out.println("Building graph from file: "+fileName4);

		ilc = new InfLangChecker(fileName4);
		ilc.Display(); //print graph after reading in from file

		System.out.println("Determining the scope of HW3Q4 (i) from file: "+fileName4);
		isInfinite = ilc.IsInfinite();

		System.out.println("RESULT: "+FormatResult(isInfinite)+" language.\n\n");
	}

	
	//convenience method - quickly format result of isInfinte check nicely
	public static String FormatResult(boolean isInfinite)
	{
		String result = "";

		if (isInfinite == true)
			result += "Infinite";
		else 
			result += "Finite";

		return result;
	}
	
}
