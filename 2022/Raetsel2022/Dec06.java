package Raetsel2022;

import Base.InputKind;

public class Dec06 extends Puzzle2022
{
	public Dec06()
	{
		super(6);
		readInput(InputKind.String);

		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Tuning Trouble", "loops für 4 Startzeichen", "loops für 14 Startzeichen");
	}

	@Override
	public void solveTask1()
	{
		erg1 = solve(4);
	}

	public int solve(int size)
	{
		String buffer="";
		int cursor = 0;
		
		do
		{
			char next  = inputString.charAt(cursor);
			if(buffer.length() == size) 
			{
				break;
			}
			else if(buffer.contains(next+""))
			{
				int i = buffer.indexOf(next);
				buffer = buffer.substring(i+1);
			}
			buffer += inputString.charAt(cursor++);
			
		}
		while(true);
		
		return cursor;
	}

	@Override
	public void solveTask2()
	{
		erg2 = solve(14);
	}

	public static void main(String[] args)
	{
		new Dec06();
	}
}
