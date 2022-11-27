package Raetsel2021;

import java.io.IOException;

public class Dec9 extends Puzzle
{
	private boolean[][] visited;
	
	public Dec9() throws IOException
	{
		super(9);
		readInput("charTable");
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Smoke Basin",
				"sum of lowest basins", 
				"size of three largest basins");
	}
	
	
	public void solveTask1()
	{
		int cnt = 0;
		int sum = 0;
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				if(countLowerAdjacents(i, j)==0)
				{
					cnt++;
					sum += Character.getNumericValue(inputCharTable[i][j]);
				}
			}
		}
		erg1 = sum + cnt;
	}

	public int countLowerAdjacents(int i, int j)
	{
		getAdjacentParameters(i, j, "charTable");
		int a = adjacentsParameters[0]; 
		int b = adjacentsParameters[1]; 
		int r = adjacentsParameters[2]; 
		int l = adjacentsParameters[3]; 
		int cnt = 0; //anzahl der kleineren
		int middleDigit = Character.getNumericValue(inputCharTable[i][j]);
		
		if(middleDigit!=9)
		{
			if(Character.getNumericValue(inputCharTable[a][j]) < middleDigit)				
			{
				cnt++;
			}
			if(Character.getNumericValue(inputCharTable[b][j]) < middleDigit)
			{
				cnt++;
			}
			if(Character.getNumericValue(inputCharTable[i][l]) < middleDigit)
			{
				cnt++;
			}
			if(Character.getNumericValue(inputCharTable[i][r]) < middleDigit)
			{
				cnt++;
			}
		}
		else
		{
			cnt=1;
		}
		
		return cnt;
	}
	
	
	public void solveTask2()
	{
		int[] biggestBasins = {0,0,0};
		int minBasin = 0;
		int minPos = 0;
		initializeVisited();
		
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				if(countLowerAdjacents(i, j) == 0)
				{
					int basinSize = getBasinSize(i,j);
					if(basinSize>minBasin)
					{
						biggestBasins[minPos] = basinSize;
						minBasin = getMinValue(biggestBasins);
						minPos = getMinPos(biggestBasins);
					}
				}
			}
		}
		//System.err.println(biggestBasins[0] + " " + biggestBasins[1] + " " + biggestBasins[2]);
		erg2 = biggestBasins[0] * biggestBasins[1] * biggestBasins[2];
	}
	
	public void initializeVisited()
	{
		visited = new boolean[lines][columns];
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				if(inputCharTable[i][j]=='9')
				{
					visited[i][j] = true;
				}
			}
		}
	}
	
	public int getMinValue(int[] arr)
	{
		int minV = arr[0];
		for(int i=1; i<arr.length; i++)
		{
			if(arr[i]<minV)
			{
				minV = arr[i];
			}
		}
		return minV;
	}

	public int getMinPos(int[] arr)
	{
		int minV = getMinValue(arr);
		for(int i=0; i<arr.length; i++)
		{
			if(arr[i] == minV)
			{
				return i;
			}
		}
		return -1;
	}

	public int countIncrementAdjacents(int i, int j)
	{
		getAdjacentParameters(i, j, "charTable");
		int a = adjacentsParameters[0]; //rowAbove
		int b = adjacentsParameters[1]; //rowBelow
		int r = adjacentsParameters[3]; //columnLeft
		int l = adjacentsParameters[2]; //columnRight
		
		visited[i][j] = true;
		int cntIncrements = 0;
		int middleDigit = Character.getNumericValue(inputCharTable[i][j]);
		if(middleDigit!=9)
		{
			if(!visited[a][j])				
			{
				cntIncrements += 1+countIncrementAdjacents(a, j);
			}
			if(!visited[b][j])
			{
				cntIncrements += 1+countIncrementAdjacents(b, j);
			}
			if(!visited[i][l])
			{
				cntIncrements += 1+countIncrementAdjacents(i, l);
			}
			if(!visited[i][r])
			{
				cntIncrements += 1+countIncrementAdjacents(i, r);
			}
		}
		return cntIncrements;
	}
	
	public int getBasinSize(int i,int j)
	{
		int basinSize = countIncrementAdjacents(i, j) +1;
		return basinSize;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec9 d = new Dec9();
	}
}
