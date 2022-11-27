package Raetsel2020;

import java.io.*;

public class December2nd extends Puzzle
{
	static int min;
	static int max;
	static char letter;
	static String password;
	static int validKeys1 = 0;
	static int validKeys2 = 0;
	//im Input-Feld soll pro zeile gespeichert werden: [0]-min [1]-max [2]-letter [3]-password
	static String input[][];
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December2nd_Input");
		prepare();
			
		for (int i=0; i<lines; i++)
		{
			String line = br.readLine();
			find(line);		
			solve1();
			solve2();						
		}
		
		System.out.println(validKeys1 +"\n" + validKeys2);	
	}
	
	public static void solve2()
	{
		if (password.charAt(min-1) == letter ^ password.charAt(max-1) == letter)
		{
			validKeys2++;
		}
		
	}
	
	public static void solve1()
	{
		int cnt = 0;
		for (int j=0; j<password.length(); j++)
		{
			if (password.charAt(j) == letter)
			{
				cnt ++;
			}
		}
		
		if (cnt>=min && cnt <=max)
		{
			validKeys1++;
		}
		
	}
	
	public static void find(String line)
	{
		int indexEnd = line.indexOf('-');
		String minStr = "";
		for (int i=0; i<indexEnd; i++)
		{
			minStr += line.charAt(i);
		}
		min = Integer.parseInt(minStr);
		
		String cut = line.substring(indexEnd+1);
		findMax(cut);
	}
	
	public static void findMax (String line)
	{
		int indexEnd = line.indexOf(' ');
		String maxStr = "";
		for (int i=0; i<indexEnd; i++)
		{
			maxStr += line.charAt(i);
		}
		max = Integer.parseInt(maxStr);
		
		String cut = line.substring(indexEnd+1);
		findRest(cut);
	}
	
	public static void findRest(String line)
	{
		letter = line.charAt(0);
		password = line.substring(3);		
	}
}
