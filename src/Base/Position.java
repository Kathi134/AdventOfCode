package Base;

public class Position implements Comparable<Position>
{
	public int row;
	public int col;
	
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