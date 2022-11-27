package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December3rd extends Puzzle
{
	private static int opens = 0;
	private static int trees = 0;
	private static int erg = 1;

	public static void main (String args[]) throws IOException
	{
		String date = "3rd";
		f = new File("src\\Raetsel2020\\inputs\\December" + date + "_Input");

		prepare();
		readInput("charTable");

		//printInput();
		solve(1, 1);
		solve(3, 1); //Aufgabenteil 1
		solve(5, 1);
		solve(7, 1);
		solve(1, 2);
		System.out.println("erg: " + erg);
	}



	public static void solve(int right, int down)
	{
		int l=0;
		int c=0;
		while(l<lines)
		{
			if(inputCharTable[l][c]=='#')
			{
				trees++;
			}
			else if(inputCharTable[l][c]=='.')
			{
				opens++;
			}
			l=l+down;
			c=(c+right)%columns;
		}
		System.out.println("r" + right +", d" + down + ": " + trees + " crashes and " + opens + " fun sliding");
		erg *= trees;
		trees = 0;
		opens = 0;
	}

	public static void printInput()
	{
		for (int l=0; l<lines; l++)
		{
			for (int c=0; c<columns; c++)
			{
				System.out.print(inputCharTable[l][c]);
			}
			System.out.print("\n");
		}
	}
}
