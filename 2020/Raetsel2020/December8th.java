package Raetsel2020;

import java.io.IOException;
import java.io.File;

public class December8th extends Puzzle
{
	private static int accumulator = 0;
	private static int indexNextCmd = 0;
	private static boolean[] visited;
	private static int erg1 = 0;
	private static int erg2 = 0;
	private static int errorLine = -1;
	
	public static void solveTask1()
	{
		visited = new boolean[lines];
		runCommand(0, inputStringTable);
	}
	
	public static void runCommand(int currCmdIndex, String[][] input)
	{
		if(indexNextCmd<lines)
		{
			if(visited[currCmdIndex] == false)
			{
				visited[currCmdIndex] = true;
				String operator = input[currCmdIndex][0];
				switch (operator)
				{
				case "nop": nop(input); break;
				case "acc": acc(currCmdIndex, input); break;
				case "jmp": jmp(currCmdIndex, input); break;
				}
			}
			else
			{
				System.err.println("Erg vor Loop:\t" + accumulator);
				erg1 = accumulator;
			}
		}
		else
		{
			System.err.println("Erg:\t" + accumulator);
			erg2 = accumulator;
			errorLine = currCmdIndex;
			
		}
	}
	
	public static void nop(String[][] table)
	{
		indexNextCmd++;
		runCommand(indexNextCmd, table);
	}
	
	public static void acc(int currCmdIndex, String[][] table)
	{
		accumulator += Integer.parseInt(inputStringTable[currCmdIndex][1]);
		indexNextCmd++;
		runCommand(indexNextCmd, table);
	}
	
	public static void jmp(int currCmdIndex, String[][] table)
	{
		indexNextCmd += Integer.parseInt(inputStringTable[currCmdIndex][1]);
		runCommand(indexNextCmd, table);
	}
	
	public static void solveTask2()
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			visited = new boolean[lines];
			if(inputStringTable[i][0].equals("nop")) //versuche nops mit jmp zu ersetzen
			{
				String cloneNOP[][] = clone(inputStringTable); //klon funktioniert nicht für 2-dimensionale arrays
				cloneNOP[i][0] = "jmp";
				runCommand(0, cloneNOP);
				accumulator = 0;
				indexNextCmd = 0;
			}
			else if(inputStringTable[i][0].equals("jmp"))
			{
				String cloneJMP[][] = clone(inputStringTable);
				cloneJMP[i][0] = "nop";
				runCommand(0, cloneJMP);
				accumulator = 0;
				indexNextCmd = 0;
			}
		}
	}
	
	public static String[][] clone(String[][] original)
	{
		String[][] copy = new String[original.length][];
		for(int i=0; i<copy.length; i++)
		{
			copy[i] = new String[original[i].length];
			for(int j=0; j<original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December" + 8 + "th_Input");
		prepare();
		readInput("StringTable divBy  ");
		//printStringTable();
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- value of the accumulator before entering loop: " + erg1);
		System.out.println("Task 2 -- when fixing line " + errorLine + " the programm terminates with value " + erg2);
	}
}