package Raetsel2021;

import java.io.IOException;

public class Dec4 extends Puzzle
{
	private int firstBingoDigit = -1;
	private int firstBingoSum = 0;
	private int firstBingoSumAfterTask1 = 0;
	
	private int lastBingoDigit = -1;
	private int lastBingoSum = 0;
	
	private boolean[] bingos;
	
	public Dec4() throws IOException
	{
		super(4);
		readSeperatedStringList();
		resetReader();
		format();
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Giant-Squid", 
				"digit:" + firstBingoDigit + " * " + "bingoSum:" + firstBingoSumAfterTask1 + " ",
				"digit:" + lastBingoDigit + " * " + "bingoSum:" + lastBingoSum + " ");
	}
	
	public void format() throws IOException
	{
 		inputIntTable = new int[inputStringList.length][];
		inputIntTable[0] = readFirstLine();
 		for(int i=1; i<inputStringList.length; i++)
 		{
 			while(inputStringList[i].charAt(inputStringList[i].length()-1)==' ')
			{
				inputStringList[i] = inputStringList[i].substring(0, inputStringList[i].length()-1);
			}
 			while(inputStringList[i].charAt(0)==' ')
 			{
 				inputStringList[i] = inputStringList[i].substring(1);
 			}
 			while(inputStringList[i].contains("  "))
 			{
 				inputStringList[i] = inputStringList[i].replaceAll("  ", " ");
 			}
 			inputIntTable[i] = splitToIntegerArr(inputStringList[i], " "); //jede zeile der records wird in eine intList zerlegt
 		}

	}
	
	public int[] readFirstLine() throws IOException
	{
		String[] tmp = br.readLine().split(",");
 		int[] digitsComing = new int[tmp.length];
 		for (int i=0; i<digitsComing.length; i++)
 		{
 			digitsComing[i] = Integer.parseInt(tmp[i]);
 		}
 		return digitsComing;
	}
	
	
	public void solveTask1()
	{
		erg1 = 0;
		firstBingoDigit = -1;
		firstBingoSum = 0;
		firstBingoSumAfterTask1 = 0;
		
		bingos = new boolean[inputIntTable.length];
		boolean bingo = false;
		//input: ein IntegerTable, wo in jeder zeile die 25 zahlen gelistet hintereinander stehen
		for(int i=0; i<inputIntTable[0].length; i++) //gehe alle eingabe zahlen durch
		{
			int currentDigit = inputIntTable[0][i];
			for(int j=1; j<inputIntTable.length; j++) //hake alle bingo tafeln ab
			{
				tickOffDigit(currentDigit, j); //alle abgehakten -> -1
			}
			//printIntTable();
			
			for(int k=1; k<inputIntTable.length; k++)
			{
				if(checkBingo(k))
				{ 
					bingo = true;
					firstBingoDigit = currentDigit;
				}
			}
			if(bingo)
			{
				erg1 = firstBingoSum * firstBingoDigit;
				firstBingoSumAfterTask1 = firstBingoSum;
				break;
			}
		}
	}
	
	public boolean checkBingo(int line)
	{
		boolean v = checkVerticals(line);
		boolean h = checkHorizontals(line);
		
		if(h||v)
		{
			bingos[line] = true;
			return true;
		}
		return false;		//sobald iwo ein bingo in der tafel -> true
	}
	
	public boolean checkHorizontals(int line)
	{
		for(int i=0; i<inputIntTable[line].length; i+=5) //alle zeilen
		{
			boolean row = true;
			for(int k=i; k<i+5; k++) //alle zeilen einträge
			{
				if(inputIntTable[line][k]!=-1)
				{
					row = false;
				}
			}
			if(row)
			{
				getFirstBingoSum(line);
				return true;
			}
		}
		return false;
	}
	
	public boolean checkVerticals(int line)
	{
		for (int k=0; k<5; k++) //alle spalten
		{
			boolean column = true;
			for (int i=k; i<inputIntTable[line].length; i+=5) //alle Spalteneintrage
			{
				if(inputIntTable[line][i]!=-1)//soblad ein wert nicht abgehakt falsch
				{
					column = false;
					break;
				}
			}
			if(column)
			{
				getFirstBingoSum(line);
				return true;
			}
		}
		return false;
	}
	
	public void getFirstBingoSum(int lineIndex)
	{
		for(int i=0; i<inputIntTable[lineIndex].length; i++)
		{
			if(inputIntTable[lineIndex][i]!=-1)
			{
				firstBingoSum+= inputIntTable[lineIndex][i];
			}
		}
	}
	
	public void tickOffDigit(int digit, int line)
	{
		for(int i=0; i<inputIntTable[line].length; i++) //gehe die zeile durch
		{
			if(inputIntTable[line][i] == digit)
			{
				inputIntTable[line][i] = -1;
				break;
			}
		}
	}
	
	
	public void solveTask2()
	{
		erg2 = 0;
		lastBingoDigit = -1;
		lastBingoSum = 0;
		
		bingos = new boolean[inputIntTable.length];
		bingos[0] = true; //die erste zeile der eingaben lasse außen vor
		
		//input: ein IntegerTable, wo in jeder zeile die 25 zahlen gelistet hintereinander stehen
		for(int i=0; i<inputIntTable[0].length; i++) //gehe alle eingabe zahlen durch
		{
			int currentDigit = inputIntTable[0][i];
			for(int j=1; j<inputIntTable.length; j++) //hake alle bingo tafeln ab
			{
				tickOffDigit(currentDigit, j); //alle abgehakten -> -1
			}
			//printIntTable();
			
			for(int k=1; k<inputIntTable.length; k++)
			{
				if(checkBingo(k)) //falls die aktuelle bingotafel ein bingo hat
				{
					bingos[k] = true;
					if(noMoreUnsolvedBingos())
					{
						lastBingoDigit = currentDigit; //merke dir den aktuellen wert
						getLastBingoSum(k);
						erg2 = lastBingoSum * lastBingoDigit;
						break;
					}
				}
			}
			if(noMoreUnsolvedBingos())
			{
				break;
			}
			
		}
	}
	
	public void getLastBingoSum(int lineIndex)
	{
		for(int i=0; i<inputIntTable[lineIndex].length; i++)
		{
			if(inputIntTable[lineIndex][i]!=-1)
			{
				lastBingoSum+= inputIntTable[lineIndex][i];
			}
		}
	}
	
	public boolean noMoreUnsolvedBingos()
	{
		for(int i=1; i<bingos.length; i++)
		{
			if(bingos[i]==false)
			{
				return false;
			}
		}
		return true;
	}
	
	public void printThisIntTable()
	{
		for(int k=0; k<25; k++)
		{
			System.err.print(k+"\t");
		}
		System.err.println();
		for(int i=1; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				System.err.print(inputIntTable[i][j]+"\t");
			}
			System.err.println();
		}
		System.out.println();
	}

	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings(value = { "unused" })
		Dec4 d = new Dec4();
	}
}
