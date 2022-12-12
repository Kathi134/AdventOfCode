package Raetsel2022;
import java.util.ArrayList;
import java.util.List;

import Base.InputKind;
import Base.Position;

public class Dec09 extends Puzzle2022
{
	List<Position> tailPositions = new ArrayList<>();
	Position[] tails = new Position[9];
	Position tail = new Position(0, 0);
	Position head = new Position(0, 0);
	
	public Dec09()
	{
		super(9);
		readInputDivided(InputKind.StringTable, " ");
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Rope Bridge", "positions visited by tail", "positions visited with whole snake");
	}

	@Override
	public void solveTask1()
	{		
		for(int i=0; i<lines; i++)
		{
			//use normales koordinatensystem (oben mehr)
			moveHead(inputStringTable[i][0], Integer.parseInt(inputStringTable[i][1]));
		}
		erg1 = tailPositions.size();
	}
	
	public void moveHead(String dir, int range)
	{
		for(int i=0; i<range; i++)
		{
			//n mal moven
			switch (dir)
			{
				case "R": head.col++; break;
				case "L": head.col--; break;
				case "U": head.row++; break;
				case "D": head.row--; break;
			}
			
			tail.moveAdjacent(head);
			
			if(!tailPositions.contains(tail))
			{
				tailPositions.add(new Position(tail.row, tail.col));
			}
		}
	}
	
	
	public void moveWholeHead(String dir, int range)
	{
		for(int i=0; i<range; i++)
		{
			//n mal moven
			switch (dir)
			{
				case "R": head.col++; break;
				case "L": head.col--; break;
				case "U": head.row++; break;
				case "D": head.row--; break;
			}

			for(int t=0; t<tails.length; t++)
			{
				Position specHead = (t==0) ? head : tails[t-1];
				tails[t].moveAdjacent(specHead);
			}
			
			tail = tails[8];
			if(!tailPositions.contains(tail))
			{
				tailPositions.add(new Position(tail.row, tail.col));
			}
		}
	}

	@Override
	public void solveTask2()
	{
		for(int i=0; i<tails.length; i++)
		{
			tails[i] = new Position(0, 0);
		}
		tailPositions.clear();
		head = new Position(0, 0);
		
		for(int i=0; i<lines; i++)
		{
			//use normales koordinatensystem (oben mehr)
			moveWholeHead(inputStringTable[i][0], Integer.parseInt(inputStringTable[i][1]));
		}
		erg2 = tailPositions.size();
	}
	
	public static void main(String[] args)
	{
		new Dec09();
	}
}


