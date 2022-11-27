package Raetsel2021;

import java.io.IOException;

public class Dec5 extends Puzzle
{
	private int [][] solutionTable;
	private int cntOverlaps = 0;
	
	public Dec5() throws IOException
	{
		super(5);
		readInput("StringList");
		format();
		//printIntTable();
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Hydrothermal Venture", 
				"only horizontal/vertical overlapping smoke-points", 
				"including diagonal overlapping smoke-points");
		
	}
	
	public void solveTask1()
	{
		createSolutionTable();
		for(int i=0; i<inputIntTable.length; i++) //für jede anweisung
		{
			if(inputIntTable[i][0] == inputIntTable[i][2]) //x koordinate gleiche
			{
				drawVertical(i);
			}
			else if(inputIntTable[i][1] == inputIntTable[i][3]) //y koordinate gleiche
			{
				drawHorizontal(i);
			}
		}
		countOverlaps();
		erg1 = cntOverlaps;
	}
	
	public void drawHorizontal(int i)
	{
		int y = inputIntTable[i][1];
		int maxX = Integer.max(inputIntTable[i][2], inputIntTable[i][0]); 
		int minX = Integer.min(inputIntTable[i][2], inputIntTable[i][0]); 
		
		for(int j=minX; j<=maxX; j++)
		{
			solutionTable[y][j]++;
		}
	}
	
	public void drawVertical(int i) //i = commandIndex
	{
		int x = inputIntTable[i][0];
		int maxY = Integer.max(inputIntTable[i][3], inputIntTable[i][1]); 
		int minY = Integer.min(inputIntTable[i][3], inputIntTable[i][1]); 
		
		for(int j=minY; j<=maxY; j++)
		{
			solutionTable[j][x]++;
		}
	}
	
	public void createSolutionTable()
	{
		int maxX = -1;
		int maxY = -1;
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int x=0; x<inputIntTable[i].length; x+=2)
			{
				if(inputIntTable[i][x]>maxX)
				{
					maxX = inputIntTable[i][x];
				}
			}
			for(int y=1; y<inputIntTable[i].length; y+=2)
			{
				if(inputIntTable[i][y]>maxY)
				{
					maxY = inputIntTable[i][y];
				}
			}
		}
		solutionTable = new int[maxY+1][maxX+1];
		for(int i=0; i<solutionTable.length; i++)
		{
			for(int j=0; j<solutionTable[i].length; j++)
			{
				solutionTable[i][j] =0;
			}
		}
	}
	
	public void countOverlaps()
	{
		cntOverlaps=0;
		for(int i=0; i<solutionTable.length; i++)
		{
			for(int j=0; j<solutionTable[i].length; j++)
			{
				if(solutionTable[i][j]>1)
				{
					cntOverlaps++;
				}
			}
		}
	}
	
	
	public void solveTask2()
	{
		createSolutionTable();
		for(int i=0; i<inputIntTable.length; i++) //für jede anweisung
		{
			if(inputIntTable[i][0] == inputIntTable[i][2]) //x koordinate gleiche
			{
				drawVertical(i);
			}
			else if(inputIntTable[i][1] == inputIntTable[i][3]) //y koordinate gleiche
			{
				drawHorizontal(i);
			}
			else //TASK 2
			{
				drawDiagonal(i);
			}
		}
		countOverlaps();
		erg2 = cntOverlaps;
	}
	
	public void drawDiagonal(int i)
	{
		if(inputIntTable[i][0]<inputIntTable[i][2] && inputIntTable[i][1]>inputIntTable[i][3]
		|| inputIntTable[i][0]>inputIntTable[i][2] && inputIntTable[i][1]<inputIntTable[i][3]) //Steigung /
		{
			int x = Integer.max(inputIntTable[i][0], inputIntTable[i][2]); //beginne rechts
			int y = Integer.min(inputIntTable[i][1], inputIntTable[i][3]); //oben
			
			int endX = Integer.min(inputIntTable[i][0], inputIntTable[i][2]); //ende links
			int endY = Integer.max(inputIntTable[i][1], inputIntTable[i][3]); //unten
			
			while(y<=endY && x>=endX)
			{
				solutionTable[y][x]++;
				y+=1;
				x-=1;
			}
		}
		else if(inputIntTable[i][0]<inputIntTable[i][2] && inputIntTable[i][1]<inputIntTable[i][3]
			 || inputIntTable[i][0]>inputIntTable[i][2] && inputIntTable[i][1]>inputIntTable[i][3]) //Steigung \
		{
			int x = Integer.min(inputIntTable[i][0], inputIntTable[i][2]); //beginne links
			int y = Integer.min(inputIntTable[i][1], inputIntTable[i][3]); //oben
			
			int endX = Integer.max(inputIntTable[i][0], inputIntTable[i][2]); //ende links
			int endY = Integer.max(inputIntTable[i][1], inputIntTable[i][3]); //unten
			
			while(y<=endY && x<=endX)
			{
				solutionTable[y][x]++;
				y+=1;
				x+=1;
			}
		}
	}

	public void format()
	{
		inputIntTable = new int[inputStringList.length][];
		for(int i=0; i<inputIntTable.length; i++)
		{
			String line = inputStringList[i].replace(" -> ", ",");
			inputIntTable[i] = splitToIntegerArr(line, ",");
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec5 d = new Dec5();
	}
	
}
