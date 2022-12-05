package Raetsel2022;

import Base.InputKind;

public class Dec04 extends Puzzle2022
{
	int[][][] data = new int[lines][2][2];
	
	public Dec04()
	{
		super(4);
		readInputDivided(InputKind.StringTable, ",");
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Camp Cleanup", "containings", "overlaps");
	}
	
	public void read() 
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			data[i][0]  = Base.InputReader.splitToIntegerArr(inputStringTable[i][0], "-");
			data[i][1]  = Base.InputReader.splitToIntegerArr(inputStringTable[i][1], "-");
		}
	}

	@Override
	public void solveTask1()
	{
		read();
		
		int sum = 0;
		for(int i=0; i<inputStringTable.length; i++)
		{
			boolean b =contains(data[i]);
			sum += b ? 1 : 0;
		}
		erg1 = sum;
	}
	
	public boolean contains(int[][] d)
	{
		int l[] = d[0];
		int r[] = d[1];
		
		if(l[0]<=r[0] && l[1]>=r[1])
			return true;
		else if(r[0]<=l[0] && r[1]>=l[1])
			return true;
		else
			return false;
	}
	
	public boolean overlaps(int[][] d)
	{
		int hl = Integer.max(d[0][0], d[1][0]);
		int ou = hl == d[0][0] ? d[1][1] : d[0][1];
		
		if(hl<=ou)
			return true;
//		else if(ll<=ru)
//			return true;
		else
			return false;
	}

	@Override
	public void solveTask2()
	{
		int sum = 0;
		for(int i=0; i<inputStringTable.length; i++)
		{
			boolean b = overlaps(data[i]);
			sum += b ? 1 : 0;
			
		}
		erg2 = sum;
	}
	
	public static void main(String[] args)
	{
		new Dec04();
	}
}

