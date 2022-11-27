package Raetsel2021;

import java.io.File;
import java.io.IOException;

/*\ Transparent Origami \*/
public class Dec13 extends Puzzle
{
	private char[][] paper;
	
	public Dec13() throws IOException
	{
		super(13);
		readInput("intTable divBy ,");
		//printIntTable();
		f = new File("2021\\Raetsel2021\\inputs\\Dec" + 13 + "Folds");
		prepare();
		readInput("StringList");
		//printStringList();
		
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		//printPaper();
		
		printFormattedSolution("Transparent Origami", 
				"number of points after one field", 
				"pattern after folding all steps: (see image below)");
		printPaper();
	}
	
	public void solveTask1()
	{
		paper = new char[getMaxY()+1][getMaxX()+1]; //breite des felds durch x beschreiebn
		readPoints();
		
		foldOneStep(0);
		erg1 = countPoints();
	}
	
	public int countPoints()
	{
		int cnt = 0;
		for(int i=0; i<paper.length; i++)
		{
			for(int j=0; j<paper[i].length; j++)
			{
				if(paper[i][j]=='#')
				{
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public void foldOneStep(int index)
	{
		String tmp = inputStringList[index].replace("fold along ", "");
		String[] command = {tmp.charAt(0) + "", tmp.substring(2)};
		
		if(command[0].equals("x"))
		{
			foldVertically(Integer.parseInt(command[1]));
		}
		else if(command[0].equals("y"))
		{
			foldHorizontally(Integer.parseInt(command[1]));
		}
	}
	
	public void foldVertically(int axis)
	{
		for(int j=axis+1; j<paper[0].length; j++)
		{
			int diff = j-axis;
			int newX = axis-diff;
			
			for(int i=0; i<paper.length; i++)
			{
				if(paper[i][j] == '#')
				{
					paper[i][newX] = paper[i][j];			
				}
			}
		}
		
		char[][] cut = new char[paper.length][axis];
		for (int i=0; i<cut.length; i++)
		{
			for (int j=0; j<cut[i].length; j++)
			{
				cut[i][j] = paper[i][j];
			}
		}
		
		paper = cut.clone();
	}
	
	public void foldHorizontally(int axis)
	{
		for(int i=axis+1; i<paper.length; i++)
		{
			int diff = i-axis;
			int newY = axis-diff;
			
			for(int j=0; j<paper[i].length; j++)
			{
				if(paper[i][j] == '#')
				{
					paper[newY][j] = paper[i][j];			
				}
			}
		}
		
		char[][] cut = new char[axis][];
		for (int i=0; i<cut.length; i++)
		{
			cut[i] = new char[paper[i].length];
			for (int j=0; j<paper[i].length; j++)
			{
				cut[i][j] = paper[i][j];
			}
		}
		
		paper = cut.clone();
	}
	
	public void readPoints()
	{
		for(int i=0; i<paper.length; i++) //mach grundsätzlich überall leer hin
		{
			for(int j=0; j<paper[i].length; j++)
			{
				paper[i][j] = '.';
			}
		}
		
		for(int i=0; i<inputIntTable.length; i++) //werte die inputs ein
		{
			int currX = inputIntTable[i][0];
			int currY = inputIntTable[i][1];
			paper[currY][currX] = '#';
		}
	}
	
	public int getMaxX()
	{
		int maxX = 0;
		for(int i=0; i<inputIntTable.length; i++)
		{
			if(inputIntTable[i][0] > maxX)
			{
				maxX = inputIntTable[i][0];
			}
		}
		return maxX;
	}
	
	public int getMaxY()
	{
		int maxY = 0;
		for(int i=0; i<inputIntTable.length; i++)
		{
			if(inputIntTable[i][1] > maxY)
			{
				maxY = inputIntTable[i][1];
			}
		}
		return maxY;
	}
	
	public void solveTask2()
	{
		paper = new char[getMaxY()+1][getMaxX()+1]; //breite des felds durch x beschreiebn
		readPoints();
		
		for(int i=0; i<inputStringList.length; i++)
		{
			foldOneStep(i);	
		}
	}
	
	public void printPaper()
	{
		for(int i=0; i<paper.length; i++)
		{
			for (int j=0; j<paper[i].length; j++)
			{
				System.err.print(paper[i][j]+" ");
			}
			System.err.println();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec13 d = new Dec13();
	}
}
