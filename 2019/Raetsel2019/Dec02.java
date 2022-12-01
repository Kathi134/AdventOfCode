package Raetsel2019;
import Base.InputKind;

public class Dec02 extends Puzzle2019
{
	private int[] origin;
	
	public Dec02()
	{
		super(2);
		readInputDivided(InputKind.intList, ",");
		origin = clone(inputIntList);
		
		computeSolution(1);
		computeSolution(2);

		printFormattedSolution("1202 Program Alarm", "program output", "noun and verb");
	}
	
	//code 1
	private void add(int first, int second, int store)
	{
		inputIntList[store] = inputIntList[first] + inputIntList[second];
	}
	
	//code 2
	private void multiply(int first, int second, int store)
	{
		inputIntList[store] = inputIntList[first] * inputIntList[second];
	}
	 

	@Override
	public void solveTask1()
	{
		runProgram(12,2);
		
		erg1 = inputIntList[0];
	}
	
	private void runProgram(int noun, int verb)
	{
		int cursor = 0;
		inputIntList = clone(origin);
		
		inputIntList[1] = noun;
		inputIntList[2] = verb;
		
		while(true)
		{
			int op = inputIntList[cursor];
			int arg1 = inputIntList[cursor +1];
			int arg2 = inputIntList[cursor +2];
			int store = inputIntList[cursor +3];
			
			if(op == 1)
				add(arg1, arg2, store);
			else if(op == 2)
				multiply(arg1, arg2, store);
			else if(op == 99)
				break;
			else
				System.err.println("error: wrong operation code " + op);
			
			cursor += 4;
		}
	}

	@Override
	public void solveTask2()
	{
		for(int noun=0; noun<=99; noun++)
		{
			for(int verb=0; verb<=99; verb++)
			{
				runProgram(noun, verb);
				if(inputIntList[0] == 19690720)
				{
					erg2 = 100 * noun + verb;
				}
				
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Dec02();
	}
}


