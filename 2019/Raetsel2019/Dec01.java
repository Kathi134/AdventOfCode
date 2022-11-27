package Raetsel2019;

import Base.*;

public class Dec01 extends Puzzle2019
{
	public Dec01()
	{
		super(1);
		readInput(InputKind.intList);
		
		computeSolution(1);
		computeSolution(2);

		printFormattedSolution(
				"The Tyranny of the Rocket Equation", 
				"model fuel", 
				"fuel including fuel mass"
		);
	}

	@Override
	public void solveTask1()
	{
		int sum = 0;
		for(int i=0; i<inputIntList.length; i++)
		{
			sum += computeFuel(inputIntList[i]);
		}
		erg1 = sum;
	}

	@Override
	public void solveTask2()
	{
		int sum = 0;
		for(int i=0; i<inputIntList.length; i++)
		{
			int currSum = 0;
			int curr = inputIntList[i];
			do
			{
				curr = computeFuel(curr);
				currSum += curr;
			}
			while(curr>0);
			sum += currSum;
		}
		erg2 = sum;
	}
	
	private int computeFuel(int i)
	{
		return Integer.max(i/3 -2, 0);
	}
	
	public static void main(String[] args)
	{
		new Dec01();
	}
}


