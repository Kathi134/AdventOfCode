package Raetsel2022;
import java.util.ArrayList;
import java.util.List;

import Base.InputKind;

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

		printSolution();
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

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec09();
	}
}


class Position implements Comparable<Position>
{
	int row;
	int col;
	
	public Position(int r, int c)
	{
		row = r;
		col = c;
	}
	
	private boolean isAdjacent(Position p)
	{
		if(p.row-1 == row || p.row == row || p.row+1 == row) 
		{
			if(p.col+1 == col || p.col == col || p.col-1 == col)
			{
				return true;
			}
		}
		return false;
	}
	
	//move a tail towards a head
	public void moveAdjacent(Position head)
	{
		if(isAdjacent(head)) //falls noch adjacent -> nix moven
		{
			return;
		}
		
		if(row != head.row && col != head.col) //move adjacent first
		{
			row += (head.row > row) ? +1 : -1;
			col += (head.col > col) ? +1 : -1;
			return;
		}
		
		if(row == head.row)
		{
			col += (head.col > col) ? +1 : -1;
			return;
		}
		
		if(col == head.col)
		{
			row += (head.row > row) ? +1 : -1;
			return;
		}
	}
	
	@Override
	public int compareTo(Position o)
	{
		return (row == o.row && col == o.col) ? 0 : -1;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Position p = (Position) o;
		boolean rowEqual = (row == p.row);
		boolean colEqual = (col == p.col);
		boolean equal = rowEqual && colEqual;
		return equal;
	}
	
	public String toString()
	{
		return "row: " + row + "\tcol: " + col;
	}
}
