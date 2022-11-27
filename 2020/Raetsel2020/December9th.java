package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December9th extends Puzzle
{
	private static int indexFirstWrong = -1;
	private static int indexFirstSummand = -1;
	private static int numberSummands = -1;
	private static long addExtrems = -1;
	
	public static void solveTask1()
	{
		for (int i=25; i<inputLongList.length; i++) //für jede zeile ab 25
		{
			boolean possible = checkPreviousLinesOnAddition(i);
			if(!possible)
			{
				indexFirstWrong = i;
				break;
			}
		}
		
	}
	
	public static boolean checkPreviousLinesOnAddition(int i)
	{
		//schau alle 25 'p'revious zeilen durch (i-25), ob zwei davon addiert input[i] ergeben
		for(int p=i-25; p<i; p++)
		{
			if(inputLongList[p]<=inputLongList[i]) //nur wenns kleiner ist - sonst eh unmöglich da nur positive zahlen
			{
				for(int j=p+1; j<i; j++)
				{
					if(inputLongList[p]+inputLongList[j]==inputLongList[i] && i!=j)
					{
						//System.err.println(inputLongList[p]+" + "+inputLongList[j]+" = "+inputLongList[i]+"\t(indices "+p+"+"+j+")");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void solveTask2()
	{
		findContiguasSummands();
		System.err.println("Die Zahl "+inputLongList[indexFirstSummand] + " an Index "+indexFirstSummand+" "
				+ "und die folgenden "+numberSummands+" Zeilen ergeben addiert "+inputLongList[indexFirstWrong]);
		long maxInRange = 0;
		long minInRange = inputLongList[indexFirstWrong];
		for(int i=indexFirstSummand; i<indexFirstSummand+numberSummands+1; i++)
		{
			if(inputLongList[i]<minInRange)
			{
				minInRange = inputLongList[i];
			}
			if(inputLongList[i]>maxInRange)
			{
				maxInRange = inputLongList[i];
			}
		}
		System.err.println("innerhalb der Range kleinste:"+minInRange+"\t größte:"+maxInRange);
		addExtrems = minInRange+maxInRange;
	}
	
	public static void findContiguasSummands()
	{
		for(int i=0; i<inputLongList.length; i++) //schaue in jeder Zeile
		{
			long sum = 0;
			int cnt = 0;
			while(sum<inputLongList[indexFirstWrong])
			{
				sum += inputLongList[i+cnt];
				cnt++;
			}
			if(sum==inputLongList[indexFirstWrong])
			{
				indexFirstSummand = i;
				numberSummands = cnt-1;
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December9th_Input");
		prepare();
		readInput("longList");
		//printInput();
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- first Wrong: " + inputLongList[indexFirstWrong] + " at index " + indexFirstWrong);
		System.out.println("Task 2 -- sum of extrems in contiguas Range: " + addExtrems);
	}
	
	public static void printInput()
	{
		for (int i=0; i<inputLongList.length; i++)
		{
			System.err.println(inputLongList[i]);
		}
	}
}
