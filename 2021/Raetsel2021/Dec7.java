package Raetsel2021;

import java.io.IOException;

public class Dec7 extends Puzzle
{
	private int minValue; 
	private int maxValue;
	
	public Dec7() throws IOException
	{
		super(7);
		readInput("intList divBy ,");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("The Treachery of Whales", 
				"fuel spent moving to one crab's position", 
				"exponential fuel spent by the crabs");
	}
	
	
	public void solveTask1()
	{
		setMinMax();
		
		long minFuel = sumFuelCosts1(minValue);
		for (int i=minValue; i<maxValue; i++) //prüfe fpür alle werte ob sie ideal sind
		{
			long fuel = sumFuelCosts1(i);
			if(fuel < minFuel)
			{
				minFuel = fuel;
			}
		}
		erg1 = minFuel;
	}
	
	public void setMinMax()
	{
		minValue = inputIntList[0];	maxValue = inputIntList[0];
		for (int i=0; i<inputIntList.length; i++)
		{
			if (inputIntList[i]>maxValue)
			{
				maxValue = inputIntList[i];
			}
			else if(inputIntList[i]<minValue)
			{
				minValue = inputIntList[i];
			}
		}
	}
	
	public long sumFuelCosts1(int i)
	{
		long fuelCosts = 0;
		for (int j=0; j<inputIntList.length; j++)
		{
			fuelCosts += Math.abs(inputIntList[j]-i);
		}
		return fuelCosts;
	}
	
	
	public void solveTask2()
	{
		setMinMax();
		
		long minFuel = sumFuelCosts2(minValue);
		for (int i=minValue; i<maxValue; i++) //prüfe fpür alle werte ob sie ideal sind
		{
			long fuel = sumFuelCosts2(i);
			if(fuel < minFuel)
			{
				minFuel = fuel;
			}
		}
		erg2 = minFuel;
	}
	
	public long sumFuelCosts2(int i)
	{
		long fuelCosts = 0;
		for (int j=0; j<inputIntList.length; j++)
		{
			int currCrabMoves = Math.abs(inputIntList[j]-i);
			int currCrabFuel = addLowers(currCrabMoves);
			fuelCosts += currCrabFuel;
		}
		return fuelCosts;
	}
	
	public int addLowers(int i)
	{
		int sum = 0;
		for(int j=i; j>0; j--)
		{
			sum += j;
		}
		return sum;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec7 d = new Dec7();
	}
  }
