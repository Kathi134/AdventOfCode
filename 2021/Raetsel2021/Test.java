package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

public class Test
{
	public static void main(String[] args) throws IOException
	{
		//testAddCharToString();
		//testAbs();
		//testDivisor();
		//testMath17();
		//testReplaceAt();
		//testComparsion19();
		//testEqualsonIntegerArray();
		testContain21();
	}
	
	public static void testContain21()
	{
		ArrayList<Possibility> alreadyChecked = new ArrayList<Possibility>();
		
		Possibility p = new Possibility(4, 0, 6, 0);
		alreadyChecked.add(p);
		Possibility p2 = new Possibility(4, 0, 6, 0);
		
		String contains = alreadyChecked.contains(p2) ? "ja" : "nein";
		String equals = p.equals(p2) ? "ja" : "nein";
		
		System.out.println("contains " + contains + "\nequals " + equals);
	}
	
	public static int count(String s)
	{
		int cnt = 0;
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i)=='#')
				cnt++;
		}
		return cnt;
	}
	
	public static void testEqualsonIntegerArray()
	{
		int[] arr1 = {1, 2};
		int[] arr2 = {1, 2};
		System.err.println(arr1.equals(arr2));
	}
	
	
	@SuppressWarnings("unused")
	public static void testComparsion19()
	{
		int[][] scanner0 = {{-618, -824, -621}, {-537, -823, -458}};
		int[][] scanner1 = {{686, 422, 578}, {605, 423, 415}};
		
		String matchOperations = "";
		for(int i0=0; i0<scanner0.length; i0++)
		{
			for(int i1=0; i1<scanner1.length; i1++)
			{
				int[] currMatchCoordinates = checkMatch(scanner0, scanner1, "", new int[] {0, 0, 0});
				int i = 0;
			}
		}
	}
	
	public static int[] checkMatch(int[][] scannerA, int[][] scannerB, String operations, int[] coordinates)
	{
		int pos = operations.length();
		if(pos == 3)
		{
			return coordinates;
		}
		
		//rechnr 0[0][0] +- 1[0][0] und 0[1][0] +- 1[1][0] und schaue ob gelcih
		int dif0 = scannerA[0][pos] - scannerB[0][pos];
		int dif1 = scannerA[1][pos] - scannerB[1][pos];
		int sum0 = scannerA[0][pos] + scannerB[0][pos];
		int sum1 = scannerA[1][pos] + scannerB[1][pos];
		
		if(dif0 == dif1)
		{
			operations += "-";
			coordinates[pos] = dif0;
			return checkMatch(scannerA, scannerB, operations, coordinates);
		}
		else if(sum0 == sum1)
		{
			operations += "+";
			coordinates[pos] = sum0;
			return checkMatch(scannerA, scannerB, operations, coordinates);
		}
		else
		{
			return coordinates; //unvollständige operations -> können nicht identsihc sein	
		}
	}
	
	public static void testReplaceAt()
	{
		System.out.println(replaceAt("[[[[10,[3,2]],[3,3]]]", 4, "10", "3"));
	}
	
	public static String replaceAt(String s, int index, String oldString, String replaceWith)
	{
		String part1 = s.substring(0, index);
		String part2 = s.substring(index+oldString.length());
		String replaced = part1 + replaceWith + part2;
		return replaced;
	}
	
	public static void testMath17()
	{
		int x=2; int y=x;
		y -= x==0 ? 0 : x/Math.abs(x);
		System.err.println(x + "->" + y);
		x=-2; y=x;
		y -= x==0 ? 0 : x/Math.abs(x);
		System.err.println(x + "->" + y);
		x=0; y=x;
		y -= x==0 ? 0 : x/Math.abs(x);
		System.err.println(x + "->" + y);
	}
	
	public static void testAbs()
	{
		int i = 3;
		int j = 0;
		int k = -2;
		System.err.println("|"+i+"| = " + Math.abs(i));
		System.err.println("|"+j+"| = " + Math.abs(j));
		System.err.println("|"+k+"| = " + Math.abs(k));
	}
	
	public static void testAddCharToString()
	{
		char c = 'a';
		String s = "katharin";
		s += c;
		System.out.println(s);
	}
	
	public static void testDivisor()
	{
		String inputKind = "intTable divBy .. q";
		//String divisor = inputKind.replace("intList divBy " , "");
		String divisor = inputKind.replace("intTable divBy ", "");
		System.out.println(divisor);
		String test = "start,hu,vt,vi,CF,pk,CF,pk,CF,ae,CF,qu,CF,end";
		System.out.println(test.length());
	}
}
