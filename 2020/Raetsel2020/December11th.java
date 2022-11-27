package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December11th extends Puzzle
{
	private static int erg1 = 0;
	
	public static void solveTask1()
	{
		executeRound();
	}
	
	public static void executeRound()
	{
		char[][] currentRound = clone(inputCharTable);
		int changes = 0;
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				if(inputCharTable[i][j] == 'L') //freier Sitz
				{
					int cnt = getOccupiedAdjacents(i, j);
					if (cnt == 0)
					{
						currentRound[i][j]='#';
						changes++;
					}
				}
				else if(inputCharTable[i][j] == '#') //besetzter Sitz
				{
					int cnt = getOccupiedAdjacents(i, j);
					if(cnt >= 4) 
					{
						currentRound[i][j]='L';
						changes++;
					}
				}
			}
		}
		if(changes!=0)
		{
			inputCharTable = clone(currentRound);
			printCharTable();
			executeRound();
			
		}
		else
		{
			countOccupiedSeats(currentRound);
		}
	}
	
	public static int getOccupiedAdjacents(int i, int j)
	{
		int rowAbove = i;
		int rowBelow = i;
		int columnRight = j;
		int columnLeft = j;
		
		if(i!=0) rowAbove = i-1; 
		if(i!=inputCharTable.length-1) rowBelow = i+1;
		if(j!=0) columnLeft = j-1;
		if(j!=inputCharTable[i].length-1) columnRight = j+1;
		
		return countOccupiedAdjacents(rowAbove, i, rowBelow, columnLeft, j, columnRight);
	}
	
	public static int countOccupiedAdjacents(int a, int i, int b, int l, int j, int r)
	{
		int cnt = 0;
		for(int row=a; row<=b; row++)
		{
			for(int col=l; col<=r; col++)
			{
				if(row!=i || col!=j)
				{
					if(inputCharTable[row][col]=='#')
					{
						cnt++;
					}
				}
			}
		}
		return cnt;
	}
	
	public static void countOccupiedSeats(char[][] table)
	{
		int cnt = 0;
		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[i].length; j++)
			{
				if(table[i][j] == '#')
				{
					cnt++;
				}
			}
		}
		erg1 = cnt;
	}
	
	public static char[][] clone(char[][] original)
	{
		char[][] copy = new char[original.length][];
		for(int i=0; i<copy.length; i++)
		{
			copy[i] = new char[original[i].length];
			for(int j=0; j<original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	
	public static void solveTask2()
	{
		
	}
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December" + 11 + "th_Input");
		prepare();
		readInput("charTable");
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- occupied Seats: " + erg1);
		System.out.println("Task 2 -- ");
	}
}
