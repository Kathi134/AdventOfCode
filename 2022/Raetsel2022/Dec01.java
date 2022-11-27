package Raetsel2022;
import Base.InputKind;

public class Dec01 extends Puzzle2022
{
	public Dec01()
	{
		super(1);
		readInput(InputKind.intList);
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	@Override
	public void solveTask1()
	{
		
	}

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec01();
	}
}


