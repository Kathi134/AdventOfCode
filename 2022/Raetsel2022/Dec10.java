package Raetsel2022;

import Base.InputKind;

public class Dec10 extends Puzzle2022
{
	public Dec10()
	{
		super(10);
		readInput(InputKind.StringList);
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Cathode-Ray Tube", "signal strengths", "drawn code");
	}

	public int x = 1;
	public int cycle = 1;
	public int res = 0;
	char[][] g = new char[6][40];
	
	@Override
	public void solveTask1()
	{
		for (int i=0; i<lines; i++)
		{
			if(inputStringList[i].equals("noop"))
			{
				draw();
				cycle();
			}
			else
			{
				int arg = Integer.parseInt(inputStringList[i].split(" ")[1]);
				draw();
				cycle();
				draw();
				x += arg;
				cycle();
			}
		}
		erg1 = res;
	}
	
	public void print()
	{
		for(int i=0; i<g.length; i++)
		{
			String line = "";
			for (int j=0; j<g[i].length; j++)
			{
				line += g[i][j];
			}
			System.err.println(line);
		}
	}
	
	//EHPZPJGL
	public void draw()
	{
		int batch = x%40;
		int currPix = (cycle-1)%40;
		int row = (cycle-1)/40;
		if(Math.abs(batch-currPix)<=1)
		{
			g[row][currPix] = '#';
		}
		else
		{
			g[row][currPix] = '.';
		}
	}

	
	public void cycle()
	{
		cycle++;
		if(cycle==20 || (cycle-20) % 40 == 0)
		{
			res += (x * cycle);
		}
	}

	@Override
	public void solveTask2()
	{
		print();
		ergStr2 = "EHPZPJG";
	}
	
	public static void main(String[] args)
	{
		new Dec10();
	}
}


