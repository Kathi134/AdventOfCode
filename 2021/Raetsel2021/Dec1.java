package Raetsel2021;

import java.io.IOException;

public class Dec1 extends Puzzle
{
	public Dec1() throws IOException
	{
		super(1);
		readInput("intList");
				
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Sonar-Sweep", 
				"number of increases", 
				"number of 3-set-sum increases");
	}

	public void solveTask1()
	{
		for (int i=1; i<inputIntList.length; i++)
		{
			if(inputIntList[i]>inputIntList[i-1])
			{
				//System.out.println(inputIntList[i]);
				erg1++;
			}
		}
			
	}
	
	public void solveTask2()
	{
		erg2 = -1;
		int previousSum = 0;
		for (int i=0; i<inputIntList.length-2; i++)
		{
			int sum = inputIntList[i]+inputIntList[i+1]+inputIntList[i+2];
			if(sum>previousSum)
			{
				erg2++;
			}
			previousSum = sum;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec1 d = new Dec1();
	}
}
