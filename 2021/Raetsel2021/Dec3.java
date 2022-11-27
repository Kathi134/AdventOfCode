package Raetsel2021;

import java.io.IOException;

public class Dec3 extends Puzzle
{
	private String mostGamma = "";
	private String leastEpsilon = "";
	
	public Dec3() throws IOException
	{
		super(3);
		readInput("charTable");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Binary-Diagnostic", 
				"energy consumption", 
				"life support rate");
	}
	
	public void solveTask1()
	{		
		for(int i=0; i<inputCharTable[0].length; i++)
		{
			int cnt1 = 0;
			int cnt0 = 0;
			for(int j=0; j<inputCharTable.length; j++)
			{
				if(inputCharTable[j][i] == '0')
				{
					cnt0++;
				}
				else
				{
					cnt1++;
				}
			}
			if(cnt1>=cnt0)
			{
				mostGamma+="1";
				leastEpsilon+="0";				
			}
			else
			{
				mostGamma+="0";
				leastEpsilon+="1";
			}
		}
		//System.err.println(mostGamma + "\t" + leastEpsilon);
		erg1 = getDecimal(mostGamma) * getDecimal(leastEpsilon);
	}
	
	public int getDecimal(String umwandeln)
	{
		int dezimaleZahl = 0;
		String umzuwandeln = umwandeln + "";
				
		for(int i=0; i<umzuwandeln.length(); i++)
		{
			int tmp = -1;
			int exp = umzuwandeln.length()-i-1;
			tmp = Character.getNumericValue(umzuwandeln.charAt(i));		
			int dieserWert = tmp * potenz(2, exp);
			dezimaleZahl = dezimaleZahl + dieserWert;  
		}
		
		return dezimaleZahl;
	}
	
	public int potenz(int basis, int exponent)
	{
		int erg = 1;
		for(int i=0; i<exponent; i++)
		{
			erg = erg * basis;
		}
		return erg;
	}
	
	
	public void solveTask2()
	{
		char[][] updatedTableCO2 = CO2();
		char[][] updatedTableOx = Ox();
		String CO2 = "";
		String Ox = "";
		
		for(int c=0; c<inputCharTable[0].length; c++)
		{
			Ox += "" + updatedTableOx[0][c];
			CO2 += "" + updatedTableCO2[0][c];
		}
		//System.err.println(CO2 + "\t" + Ox);
		erg2 = getDecimal(CO2) * getDecimal(Ox);
	}
	
	public char[][] Ox()
	{
		char[][] updatedTableOx = inputCharTable.clone();
		for(int i=0; i<updatedTableOx[0].length; i++)
		{
			int cnt1 = 0;
			int cnt0 = 0;
			for(int j=0; j<updatedTableOx.length; j++)
			{
				if(updatedTableOx[j][i] == '0')
				{
					cnt0++;
				}
				else
				{
					cnt1++;
				}
			}
			if(cnt1>=cnt0)
			{
				updatedTableOx = updateTable(i, '1', cnt1, updatedTableOx).clone();
			}
			else
			{
				updatedTableOx= updateTable(i, '0', cnt0, updatedTableOx).clone();
			}
			
			if(updatedTableOx.length == 1)
			{
				break;
			}
		}
		return updatedTableOx;
	}
	
	public char[][] CO2()
	{
		char[][] updatedTableCO2 = inputCharTable.clone();
		for(int i=0; i<updatedTableCO2[0].length; i++)
		{
			int cnt1 = 0;
			int cnt0 = 0;
			for(int j=0; j<updatedTableCO2.length; j++)
			{
				if(updatedTableCO2[j][i] == '0')
				{
					cnt0++;
				}
				else
				{
					cnt1++;
				}
			}
			if(cnt1>=cnt0)
			{
				updatedTableCO2 = updateTable(i, '0', cnt0, updatedTableCO2).clone();
			}
			else
			{
				updatedTableCO2 = updateTable(i, '1', cnt1, updatedTableCO2).clone();
			}
			
			if(updatedTableCO2.length == 1)
			{
				break;
			}
		}
		return updatedTableCO2;
	}
	
	public char[][] updateTable(int bit, char digit, int cnt, char[][] input)
	{
		char[][] updatedTable = new char[cnt][input[0].length];
		int lineUpT = 0;
		for (int i=0; i<input.length; i++)
		{
			
				if(input[i][bit]==digit)
				{
					for(int c=0; c<updatedTable[0].length; c++)
					{
						updatedTable[lineUpT][c] = input[i][c];
					}
					lineUpT++;
				}
			
		}
		return updatedTable;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec3 d = new Dec3();
	}
}
