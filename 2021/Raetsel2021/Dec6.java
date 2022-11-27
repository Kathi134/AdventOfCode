package Raetsel2021;

import java.io.IOException;

public class Dec6 extends Puzzle
{
	private long[] cntFish;
	
	public Dec6() throws IOException
	{
		super(6);
		readInput("intList divBy ,");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Lanternfish", 
				"number of lantern fish after 80 days", 
				"number of lantern fish after 256 days");
	}
	
	public void solveTask1()
	{
		execute(80);
		erg1 = cntFish();
	}
	
	public void solveTask2()
	{
		execute(256);
		erg2 = cntFish();
	}
	
	
	public void execute(int numberOfDays)
	{
		cntFish = new long[9]; //speichert für jede zahl, wie viele fische es davon gibt
		for (int i=0; i<inputIntList.length; i++)
		{
			cntFish[inputIntList[i]]++; //die zahl, die fish i hat, wird in cntFish erhöht
		}
		
		for (int i=0; i<numberOfDays; i++)
		{
			doCycle();
		}
	}
	
	public void doCycle()
	{
		long[] arr = new long[9];
		for(int i=0; i<arr.length-1; i++)
		{
			arr[i] = cntFish[i+1];
		}
		long givingBirth = cntFish[0];
		arr[8] = givingBirth;
		arr[6] += givingBirth;
		cntFish = clone(arr);
	}
	
	public long cntFish()
	{
		long numberOfFish = 0;
		for(int i=0; i<cntFish.length; i++)
		{
			numberOfFish += cntFish[i];
		}
		return numberOfFish;
	}

	/*public void sub1()
	{
		for(int i=0; i<inputIntList.length; i++)
		{
			inputIntList[i]--;
			if(inputIntList[i]==-1)
			{
				inputIntList[i]=6;
				createNewFish();
			}
			
		}
	}
	
	public void createNewFish()
	{
		int[] arr = new int[inputIntList.length+1];
		for(int i=0; i<inputIntList.length; i++)
		{
			arr[i]=inputIntList[i];
		}
		arr[arr.length-1] = 9;
		inputIntList = clone(arr);
	}*/
	
	public void readIntList() throws IOException
	{
		String all = "";
		String line = br.readLine();
		while(line!=null)
		{
			all+=line;
			line = br.readLine();
		}
		inputIntList = splitToIntegerArr(all, ",");
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec6 d = new Dec6();
	}
}
