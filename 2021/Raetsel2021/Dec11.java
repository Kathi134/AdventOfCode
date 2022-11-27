package Raetsel2021;

import java.io.IOException;

public class Dec11 extends Puzzle
{
	private int cntFlashes = 0;	
	
	public Dec11() throws IOException
	{
		super(11);
		readInput("intTable");

		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Dumbo Octopus", 
				"flashes after 100 rounds",
				"first round to flash simultaneously");
	}
	
	
	public void solveTask1()
	{
		for (int k=0; k<100; k++) //100 Runden
		{
			execute();
		}
		erg1 = cntFlashes;	
	}
	
	public void execute()
	{
		//increase
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				inputIntTable[i][j]++;
			}
		}
		
		//flash
		while(anyFlashable())
		{
			for(int i=0; i<inputIntTable.length; i++)
			{
				for(int j=0; j<inputIntTable[i].length; j++)
				{
					if(flashable(i, j))
					{
						flash(i, j);
					}
				}
			}	
		}
		
		//flashed->0
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				if(inputIntTable[i][j] >= 11)
				{
					inputIntTable[i][j] = 0;
					cntFlashes++;
				}
			}
		}
	}
	
	public boolean flashable(int i, int j)
	{
		return (inputIntTable[i][j]==10);
	}
	
	public boolean anyFlashable()
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				if(flashable(i, j))
				{
					return true;
				}	
			}
		}
		return false;
	}
	
	public void flash(int i, int j)
	{
		inputIntTable[i][j]++; //11 ist kennzeichen für flashed
		getAdjacentParameters(i, j, "intTable");
		
		for(int row=adjacentsParameters[0]; row<=adjacentsParameters[1]; row++)
		{
			for(int col=adjacentsParameters[2]; col<=adjacentsParameters[3]; col++)
			{
				//erhöhe jeden adjacenten octopus aber höchstens auf 10 -> 10 ist kennzeichen für flashable
				if(inputIntTable[row][col] < 10)
				{
					inputIntTable[row][col]++;
				}
			}
		}		
	}
	
	
	public void solveTask2()
	{
		
		int round = 100;
		while(!allFlashed())
		{
			execute();
			round++;
		}
		erg2 = round;
	}
	
	public boolean allFlashed()
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				if(inputIntTable[i][j]!=0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec11 d11 = new Dec11();
	}
}
