package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

/*\ Sea Cucumber \*/
public class Dec25 extends Puzzle
{
	ArrayList<VerticalPoint> verticalPoints = new ArrayList<>();
	
	public Dec25() throws IOException
	{
		super(25);
		readInput("charTable");
			
		computeSolution(1);
		//printSolution();
		
		printFormattedSolution("Sea Cucumber", 
				"no moves after step",
				"no task 2 (");
	}
	
	public void solveTask1()
	{
		int steps = 0;
		boolean moveable = true;
		while(moveable)
		{
			steps++;
			moveable = cycle();
			if(!moveable)
			{
				break;
			}
		}
		erg1 = steps;
	}
	
	public boolean cycle()
	{
		boolean changed = false;
		
		char[][] copy = clone(inputCharTable);
		//first, move the verticals
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				if(inputCharTable[i][j] == '.')
				{
					continue;
				}
				 
				if(inputCharTable[i][j] == '>')
				{
					int newJ = (j+1)%inputCharTable[i].length;
					char faced = inputCharTable[i][newJ];
					if(faced!='.')
					{
						continue;
					}
					copy[i][newJ] = '>';
					copy[i][j] = '.';
					changed = true;
					j++;
					continue;
				}
				//to economize runtime, save where vertiacls are
				verticalPoints.add(new VerticalPoint(i, j));
			}
		}
		inputCharTable = clone(copy);
		
		//for(int i=0; i<verticalPoints.size(); i++)
		while(!verticalPoints.isEmpty())
		{
			VerticalPoint p = verticalPoints.remove(0);
			int newY = (p.y+1)%inputCharTable.length;
			char faced = inputCharTable[newY][p.x];
			if(faced!='.')
			{
				continue;
			}
			copy[newY][p.x] = 'v';
			copy[p.y][p.x] = '.';
			changed = true;
		}
		inputCharTable = clone(copy);
		
		return changed;
	}
	
	
	public void solveTask2(){}
	
	public static void main(String[] args) throws IOException
	{
		new Dec25();
	}
}

class VerticalPoint
{
	int y;
	int x;
	
	public VerticalPoint(int y, int x)
	{
		this.x = x;
		this.y = y;
	}
}
