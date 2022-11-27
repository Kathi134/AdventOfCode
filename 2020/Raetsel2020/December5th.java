package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December5th extends Puzzle
{
	private static int highestID = -1;
	private static int lowestID = 1000;
	private static int missingID = -1;
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December5th_input");
		prepare();
		readInput("StringList");
		//solveTask1();
		solveTask2();
		System.out.println("highest ID: " + highestID);
		System.out.println("lowest ID: " + lowestID);
		System.out.println("missing ID: " + missingID);		
	}

	public static void solveTask2()
	{
		int[] IDs = new int[inputStringList.length];
		for (int i=0; i<inputStringList.length; i++)
		{
			String rowCode = inputStringList[i].substring(0, 7);
			String columnCode = inputStringList[i].substring(7);
			int seatID = 8*getRowIndex(rowCode) + getColumnIndex(columnCode);
			
			if (seatID > highestID)
			{
				highestID = seatID;
			}
			if (seatID < lowestID)
			{
				lowestID = seatID;
			}
			IDs[i] = seatID;
		}
		findMissingID(IDs);
	}
	
	public static void findMissingID(int[] IDs)
	{
		for(int id=lowestID; id<=highestID; id++)
		{
			boolean isValid = false;
			for(int j=0; j<IDs.length; j++)
			{
				if(id == IDs[j])
				{
					isValid = true;
				}
			}
			
			if(!isValid)
			{
				missingID = id;
			}
		}
	}
	
	public static void solveTask1()
	{
		for (int i=0; i<inputStringList.length; i++)
		{
			String rowCode = inputStringList[i].substring(0, 7);
			String columnCode = inputStringList[i].substring(7);
			int rowIndex = getRowIndex(rowCode);
			int columnIndex = getColumnIndex(columnCode);
			int seatID = 8*rowIndex + columnIndex;
			System.out.println("row:" + rowIndex + "\tcolumn:" + columnIndex + "\tseatId:" + seatID);
			if(seatID > highestID)
			{
				highestID = seatID;
			}
		}
	}
	
	public static int getRowIndex(String code)
	{
		int max = 127;
		int min = 0;
		
		return getIndex(code, min, max, 'B', 'F');
	}
	
	public static int getColumnIndex(String code)
	{
		int max = 7;
		int min = 0;
		
		return getIndex(code, min, max, 'R', 'L');
	}
	
	public static int getIndex(String code, int min, int max, char upperBR, char lowerFL)
	{
		for (int i=0; i<code.length()-1; i++)
		{
			if (code.charAt(i)==lowerFL)
			{
				max = (max-min)/2 + min;
			}
			else if (code.charAt(i)==upperBR)
			{
				min = (max-min+1)/2 + min;
			}
			//System.out.print(min+"-"+max+" ");
		}
		char lastChar = code.charAt(code.length()-1);
		 
		if (lastChar==lowerFL)
		{
			return min;
		}
		else 
		{
			return max;
		}	
	}
}
