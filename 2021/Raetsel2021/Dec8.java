package Raetsel2021;

import java.io.IOException;

public class Dec8 extends Puzzle
{
	public Dec8() throws IOException
	{
		super(8);
		readInput("StringTable divBy  ");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Seven Segment Search", 
				"number of unique-length-digits in output", 
				"sum of all output-numeric-values");
		
	}
	
	public void solveTask1()
	{
		int cntOutput1478 = 0;
		for(int i=0; i<inputStringTable.length; i++) //für jeden durchlauf
		{
			for(int j=11; j<inputStringTable[i].length; j++)
			{
				int length = inputStringTable[i][j].length();
				if(length == 2 || length == 3 || length == 4 || length == 7)
				{
					cntOutput1478++;
				}
			}
		}
		erg1 = cntOutput1478;
	}
	
	
	public void solveTask2()
	{
		int sumValues = 0;
		for(int i=0; i<inputStringTable.length; i++) //für jeden durchlauf
		{
			String numberOfLine = "";
			String[] pattern = findPattern(i);
			
			for(int j=11; j<inputStringTable[i].length; j++)
			{
				numberOfLine += "" + findDigit(inputStringTable[i][j], pattern);
			}
			
			//System.err.println(toString(pattern) + " " + numberOfLine);
			sumValues += Integer.parseInt(numberOfLine);
		}
		erg2 = sumValues;
	}
	
	public String toString(String[] pattern)
	{
		String r = "";
		for(int i=0; i<pattern.length; i++)
		{
			r += "[" + pattern[i] + "] ";
		}
		return r;
	}
	
	public String[] findPattern(int line)
	{
		String[] pattern = new String[7];
		String one = ""; String four = ""; String seven = "";
		
		for(int i=0; i<10; i++)
		{
			String currWord = inputStringTable[line][i];
			int length = currWord.length();
			
			switch (length)
			{
			case 2: one = currWord; break;
			case 3: seven = currWord; break;
			case 4: four = currWord; break;
			}
		}
		
		//Diagramm Teil 1
		pattern[2] = one; pattern[5] = one;
		
		//Diagramm Teil 2
		String letterAt0 = getDif(seven, one);
		pattern[0] = letterAt0;
		
		//Diagramm Teil 3
		pattern[1] = getDif(four, one); pattern[3] = pattern[1];
		
		//Diagramm Teil 4
		String smallNine = pattern[0] + pattern[1] + pattern[2];
		for(int i=0; i<10; i++)
		{
			String currWord = inputStringTable[line][i];
			int length = currWord.length();
			if(length == 6)
			{
				if(getDif(currWord, smallNine).length() == 1)
				{
					pattern[6] = getDif(currWord, smallNine);
					break;
				}
			}
		}
		
		//Diagramm Teil 5
		String smallFive = pattern[0] + pattern[6] + pattern[1];
		for (int i=0; i<10; i++)
		{
			String currWord = inputStringTable[line][i];
			int length = currWord.length();
			if(length == 5)
			{
				if(getDif(currWord, smallFive).length() == 1)
				{
					pattern[5] = getDif(currWord, smallFive);
					pattern[2] = getDif(pattern[2], pattern[5]);
					break;
				}
			}
		}
		
		//Diagramm Teil 6
		String smallThree = pattern[0] + pattern[2] + pattern[5] + pattern[6];
		for (int i=0; i<10; i++)
		{
			String currWord = inputStringTable[line][i];
			int length = currWord.length();
			if(length == 5)
			{
				if(getDif(currWord, smallThree).length() == 1)
				{
					pattern[3] = getDif(currWord, smallThree);
					pattern[1] = getDif(pattern[1], pattern[3]);
					break;
				}
			}
		}
		
		//Diagramm Teil 7
		String setLetters = "";
		for(int i=0; i<pattern.length; i++)
		{
			setLetters += pattern[i];
		}
		pattern[4] = getDif("abcdefg", setLetters);
		
		return pattern;
		
	}
	
	public String getDif(String big, String small)
	{
		boolean[] possibleIndex = new boolean[big.length()];
		for(int i=0; i<possibleIndex.length; i++)
		{
			possibleIndex[i] = true;
		}
		
		for(int i=0; i<small.length(); i++)
		{
			char c = small.charAt(i);
			if(big.indexOf(c)!=-1)
			{
				possibleIndex[big.indexOf(c)] = false; //jedes zeichen aus small wird in big auf false gesetzt
			}
		}
		
		String dif = "";
		for(int i=0; i<possibleIndex.length; i++)
		{
			if(possibleIndex[i])
			{
				dif += big.charAt(i);
			}
		}
		return dif;
	}
	
	public String findDigit(String input, String[] pattern)
	{
		int length = input.length();
		switch (length)
		{
		case 2: return "1"; 
		case 3: return "7"; 
		case 4: return "4"; 
		case 7: return "8"; 
		
		case 5: if(input.contains(pattern[1])) return "5";
				else if(input.contains(pattern[4])) return "2";
				else return "3"; 
		
		case 6: if(!input.contains(pattern[2])) return "6";
				else if(input.contains(pattern[4])) return "0";
				else return "9";
		}
		return "E";
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec8 d = new Dec8();
	}
	
}
