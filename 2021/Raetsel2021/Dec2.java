package Raetsel2021;

import java.io.IOException;

public class Dec2 extends Puzzle
{
	public Dec2() throws IOException
	{
		super(2);
		readInput("StringTable divBy  ");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Dive!", 
				"position code", 
				"position code");
	}
	
	
	public void solveTask1()
	{
		int depth = 0;
		int width = 0;
		for(int i=0; i<inputStringTable.length; i++)
		{
			String operator = inputStringTable[i][0];
			int value = Integer.parseInt(inputStringTable[i][1]);
			
			if(operator.equals("forward"))
			{
				 width += value;
			}
			else if(operator.equals("down"))
			{
				depth += value;
			}
			else
			{
				depth -= value;
			}	
		}
		erg1 = width * depth;
	}
	
	
	public void solveTask2()
	{
		int aim = 0;
		int width = 0;
		int depth = 0;
		for(int i=0; i<inputStringTable.length; i++)
		{
			String operator = inputStringTable[i][0];
			int value = Integer.parseInt(inputStringTable[i][1]);
			
			if(operator.equals("forward"))
			{
				 width += value;
				 depth += aim*value;
			}
			else if(operator.equals("down"))
			{
				aim += value;
			}
			else
			{
				aim -= value;
			}	
		}
		erg2 = width * depth;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec2 d = new Dec2();
	}
}
