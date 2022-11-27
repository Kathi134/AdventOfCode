package Raetsel2021;

import java.io.IOException;

public class Dec24 extends Puzzle
{
	int[] addNumbersOnX = new int[14];
	int[] addNumbersOnW = new int[14];
	
	int[] inputData = new int[7]; //all possible moduleNumbers for type 1 operations
	int z = 0;
	
	int[] result = new int[14];
	String erg = "";
	String erg2 = "";
	
	public void read() throws IOException
	{
		super.readInput("StringList");		
			
		//numbers on x: index 5 of each cycle
		int j=0;
		for(int i=5; i<inputStringList.length; i+=18)
		{
			addNumbersOnX[j++] = Integer.parseInt(inputStringList[i].split(" ")[2]); 
		}
		
		//numbers on w: index 15 of each cycle
		j=0;
		for(int i=15; i<inputStringList.length; i+=18)
		{
			addNumbersOnW[j++] = Integer.parseInt(inputStringList[i].split(" ")[2]);
		}
	}
	
	public Dec24() throws IOException
	{
		super(24);
		read();
		
		computeSolution(1);
		erg1 = Long.parseLong(erg);
		computeSolution(2);
		super.erg2 = Long.parseLong(this.erg2);
		//printSolution();
		
		printFormattedSolution("Arithmetic Logic Unit",
				"highest valid model-number",
				"smallest valid model-number");
	}
	
	public void solve(int taskNumber)
	{
		int modelNumber = taskNumber == 1 ? 9999999 : 1111111;
		
		while(true)
		{
			//System.err.println(modelNumber);
			String tmp = modelNumber + "";
			for(int i=0; i<inputData.length; i++)
			{
				inputData[i] = Integer.parseInt(tmp.charAt(i)+"");
			} //aktualisiere das input feld
			
			if(run())
				break; //falls erfolgreich, hör auf
			
			do
			{
				modelNumber += taskNumber == 1 ? -1 : 1;
				tmp = modelNumber + "";
			}
			while(tmp.contains("0")); //nimm die nächst kleinere modelnumebr
		}
		
		for(int i=0; i<result.length; i++)
		{
			if(taskNumber == 1)
			{
				erg += result[i];
			}
			else
			{
				erg2 += result[i];
			}
		}
	}
		
	public void solveTask1()
	{
		solve(1);
	}
	
	public boolean run()
	{
		int cntType1 = 0;
		z = 0;
		
		for(int i=0; i<14; i++)
		{
			if(addNumbersOnX[i] > 0) //type 1 -> try all digits 1..9
			{
				z = z * 26 + inputData[cntType1] + addNumbersOnW[i];
				result[i] = inputData[cntType1];
				cntType1++;
			}
			else //tpye 2 -> decrement necessary -> fullfill x==w
			{
				int x = (z%26) + addNumbersOnX[i]; 
				result[i] = x; //let w be equal to x	
				z /= 26;
				
				if(x<1 || x>9) //falls nicht im wertebereich -> false
				{
					return false;
				}
			}
		}
		
		if(z==0)
		{
			return true;
		}
		return false;
	}
	
	public void solveTask2()
	{
		solve(2);
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec24();
	}
}
