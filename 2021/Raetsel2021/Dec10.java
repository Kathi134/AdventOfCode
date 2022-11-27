package Raetsel2021;

import java.io.IOException;
import java.util.Arrays;

public class Dec10 extends Puzzle
{
	private char[] stack;
	private int top;
	
	public Dec10() throws IOException
	{
		super(10);
		readInput("StringList");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Syntax Scoring",
				"total syntax error score", 
				"middle score of incompletes");
	}
	
	
	public void solveTask1()
	{
		for(int i=0; i<inputStringList.length; i++)
		{
			stack = new char[inputStringList[i].length()];
			top = 0;
			
			for(int j=0; j<inputStringList[i].length(); j++)
			{
				char c = inputStringList[i].charAt(j);
			
				if(workOffChar(c))
				{
					inputStringList[i] = "";
					break;
				}
			}
		}
	}
	
	public char getOpenedBracket(char closedBracket)
	{
		switch(closedBracket)
		{
		case ')': return '(';
		case ']': return '[';
		case '}': return '{';
		case '>': return '<';
		}
		return '.'; //never reached
	}
	
	public int getBracketScore(char closedBracket)
	{
		switch(closedBracket)
		{
		case ')': return 3;
		case ']': return 57;
		case '}': return 1197;
		case '>': return 25137;
		}
		return '.'; //never reached
	}
	
	public boolean workOffChar(char c)
	{
		boolean exit = false; 
		
		if(c=='(' || c=='[' || c=='{' || c=='<')
		{
			stack[top] = c;
			top++;			
		}
		else //schließende klammer
		{
			if(top==0 || stack[top-1] != getOpenedBracket(c)) //passt nicht zur erwarteten schlussklammer auf dem stack 
		    {
				erg1 += getBracketScore(c);
				exit = true;
			}
			else
			{
				top--;
			}
		}
		return exit;
	}
	
	
	public void solveTask2()
	{
		long[] scores = new long[countIncorrects()];
		int scoresIndex = 0;
		
		for(int i=0; i<inputStringList.length; i++)
		{
			if(inputStringList[i]=="") 
			{
				continue; //wenn es eine corrupted line ist mach die nächste line
			}
			
			stack = new char[inputStringList[i].length()];
			top = 0;
			for(int j=0; j<inputStringList[i].length(); j++)
			{
				workOffChar(inputStringList[i].charAt(j));
			}
			
			//nur noch ungeschlossene sind auf dem stack
			String extention = "";
			for(int k=top-1; k>=0; k--) //arbeite den stack von oben ab
			{
				extention += stack[k] + "";
			}
			scores[scoresIndex] = getScore(extention);
			scoresIndex++;			
		}
		
		//alle Scores sind befüllt
		Arrays.sort(scores);
		erg2 = scores[scores.length/2];
	}
	
	public int countIncorrects()
	{
		int cnt = 0;
		for(int i=0; i<inputStringList.length; i++)
		{
			if(inputStringList[i]!="")
			{
				cnt++; 
			}
		}
		return cnt;
	}
	
	public long getScore(String brackets)
	{
		long score = 0;
		for(int i=0; i<brackets.length(); i++)
		{
			score *= 5;
			switch(brackets.charAt(i))
			{
			case '(': score += 1; break;
			case '[': score += 2; break;
			case '{': score += 3; break;
			case '<': score += 4; break;
			}
		}
		return score;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec10 d = new Dec10();
	}
}