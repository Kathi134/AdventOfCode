package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December10th extends Puzzle
{
	private static int erg1 = 0;

	public static void solveTask1()
	{
		int cnt1difs = 0;
		int cnt3difs = 0;

		if(inputIntList[0]==1) //betrachte den untershcied von steckdose 0 zum ersten
		{
			cnt1difs++;
		}
		else if(inputIntList[0]==3)
		{
			cnt3difs++;
		}

		for(int i=1; i<inputIntList.length; i++)
		{
			int dif = inputIntList[i]-inputIntList[i-1];
			switch (dif)
			{
			case 1: cnt1difs++; break;
			case 3: cnt3difs++; break;
			}
		}

		cnt3difs++; //das letzte ist immer ein 3dif
		System.err.println("1jolts: "+cnt1difs+"\t3jolts: "+cnt3difs);
		erg1 = cnt1difs*cnt3difs;
	}

	public static void solveTask2()
	{

	}

	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December" + 10 + "th_Input");
		prepare();
		readInput("intList");
		inputIntList=sort(inputIntList);
		printArr();

		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- multiply 3jolts with 1jolts: "+erg1);
		System.out.println("Task 2 -- ");
	}

	public static void printArr()
	{
		for (int element : inputIntList)
		{
			System.err.println(element);
		}
	}

	public static int[] sort(int[] array)
	{
		int[] sorted = new int[array.length];

		for(int k=0; k<sorted.length; k++)
		{
			int min=array[0];
			int minPos=0;
			for(int j=0; j<array.length; j++)
			{
				if (array[j]<min)
				{
					min = array[j];
					minPos = j;
				}
			}
			sorted[k]=min;

			//nächsten Schleifendurchlauf vorbereiten
			int tmp = array[array.length-1];
			array[minPos] = tmp;
			array[array.length-1] = min;

			//schneide den letzten feldeintrag ab
			int[] copy = new int[array.length-1];
			for(int i=0; i<copy.length; i++)
			{
				copy[i] = array[i];
			}
			array = copy;
		}

		return sorted;
	}
}
