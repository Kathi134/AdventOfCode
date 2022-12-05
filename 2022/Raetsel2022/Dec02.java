package Raetsel2022;

import Base.InputKind;

public class Dec02 extends Puzzle2022
{
	public Dec02()
	{
		super(2);
		readInput(InputKind.charTable);
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Rock Paper Scissors", "total score with response", "total score with outcome");
	}
//	A X for Rock,
//	B Y for Paper, and 
//	C Z for Scissors.
	
	@Override
	public void solveTask1()
	{		
		int round = 0;
		for(int i=0; i<inputCharTable.length; i++)
		{
			char me = inputCharTable[i][2];
			switch(me)
			{
				case 'X' : round += 1; break;
				case 'Y': round += 2;break;
				case 'Z': round += 3;break;
			}
			char elve = inputCharTable[i][0];
			
			if((elve == 'A' && me == 'X') ||
			   (elve == 'B' && me == 'Y') ||
			   (elve == 'C' && me == 'Z'))
			{
				round += 3;
			}
			else if((elve == 'A' && me == 'Y') ||
					(elve == 'B' && me == 'Z') ||
					(elve == 'C' && me == 'X')) //rock paper
			{
				round += 6; 
			}
			else if((elve == 'A' && me == 'Z') ||
					(elve == 'B' && me == 'X') ||
					(elve == 'C' && me == 'Y')) //r c
			{
				round += 0;
			}
		}
		
		erg1= round;
	}

	@Override
	public void solveTask2()
	{
		int round = 0;
		for(int i=0; i<inputCharTable.length; i++)
		{
			char res = inputCharTable[i][2];
			switch(res)
			{
				case 'X': round += 0; break;
				case 'Y': round += 3;break;
				case 'Z': round += 6;break;
			}
			char elve = inputCharTable[i][0];
			
			if((elve == 'A' && res == 'Y') ||
			   (elve == 'B' && res == 'X') ||
			   (elve == 'C' && res == 'Z'))
			{// put rock
				round += 1;
			}
			else if((elve == 'A' && res == 'Z') ||
					(elve == 'B' && res == 'Y') ||
					(elve == 'C' && res == 'X')) //rock paper
			{//put paper
				round += 2; 
			}
			else if((elve == 'A' && res == 'X') ||
					(elve == 'B' && res == 'Z') ||
					(elve == 'C' && res == 'Y')) //r c
			{
				round += 3;
			}
		}
		
		erg2= round;
	}
	
	public static void main(String[] args)
	{
		new Dec02();
	}
}