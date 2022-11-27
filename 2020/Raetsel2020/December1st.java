package Raetsel2020;

import java.io.*;

public class December1st extends Puzzle
{
	private static int[] input;
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December1st_Input");
		prepare();
		readInput("intList");
		input = inputIntList;		
		
		for (int i=0; i<input.length; i++)
		{
			for (int j=i+1; j<input.length; j++)
			{
				for (int k=j+1; k<input.length; k++)
				{
					if(input[i]+input[j]+input[k] == 2020)
					{
						System.out.print(input[i]*input[j]*input[k]);
					}
				}
			}
		}
	}

}

