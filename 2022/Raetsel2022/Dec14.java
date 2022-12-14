package Raetsel2022;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Base.InputKind;
import Base.InputPrinter;
import Base.Position;


public class Dec14 extends Puzzle2022
{
	public Dec14()
	{
		super(14);
		readInputDivided(InputKind.StringTable, " -> ");
		
		computeSolution(1);
		Base.InputPrinter.printCharTable(cave);
		computeSolution(2);
		Base.InputPrinter.printCharTable(cave);

		printFormattedSolution("Regolith Reservoir", "falls without infinty", "falls on ground");
	}

	char[][] cave;
	
	List<List<Position>> pathPoints = new ArrayList<>();
	Position sand = new Position(0, 500);
	int minCol = sand.col;
	int maxCol = sand.col;
	int maxRow = sand.row;
	
	public void createCave()
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			List<Position> currPath = new ArrayList<Position>();
			for(int j=0; j<inputStringTable[i].length; j++)
			{
				String cmd = inputStringTable[i][j];
				int[] tmp = Base.InputReader.splitToIntegerArr(cmd, ",");
				Position p = new Position(tmp[1], tmp[0]);
				currPath.add(p);
				
				minCol = Math.min(minCol, tmp[0]);
				maxCol = Math.max(maxCol, tmp[0]);
				maxRow = Math.max(maxRow, tmp[1]);
			}
			pathPoints.add(currPath);
		}
		
		sand.col-=minCol;
		cave = new char[maxRow+1][(maxCol-minCol)+1];
		// mapping cols: col - minCol
		for(int i=0; i<cave.length; i++)
		{
			for(int j=0; j<cave[i].length; j++)
			{
				if(i==sand.row && j==(sand.col))
				{
//					cave[i][j] = '+';
					cave[i][j] = '.';
				}
				else
				{
					cave[i][j] = '.';					
				}
			}
		}

		
		for(List<Position> path: pathPoints)
		{
			Position init = path.get(0);
			init.col = init.col - minCol;
			cave[init.row][init.col] = '#';
			
			for(int i=1; i<path.size(); i++)
			{
				Position point = path.get(i);
				point.col = point.col - minCol;
				
				if(point.col == init.col) //hoirzontally aligned
				{
					int start = Math.min(init.row, point.row);
					int end = Math.max(init.row, point.row);
					for(int r=start; r<=end; r++)
					{
						cave[r][init.col] = '#';
					}
				}
				else if(point.row == init.row) //vertically aligned
				{
					int start = Math.min(init.col, point.col);
					int end = Math.max(init.col, point.col);
					for(int c=start; c<=end; c++)
					{
						cave[init.row][c] = '#';
					}
				}
				
				init = point;
			}
		}
	}
	
	public void createGorundedCave()
	{
		minCol = sand.col - (maxRow+2);
		maxCol = sand.col + (maxRow+2);
		maxRow = maxRow+2;
		
		sand.col-=minCol;
		cave = new char[maxRow+1][(maxCol-minCol)+1];
		// mapping cols: col - minCol
		for(int i=0; i<cave.length-1; i++)
		{
			for(int j=0; j<cave[i].length; j++)
			{				
				if(i==sand.row && j==(sand.col))
				{
//					cave[i][j] = '+';
					cave[i][j] = '.';
				}
				else
				{
					cave[i][j] = '.';					
				}
			}
		}
		for(int j=0; j<cave[cave.length-1].length; j++)
		{
			cave[cave.length-1][j] = '#';
		}

		
		for(List<Position> path: pathPoints)
		{
			Position init = path.get(0);
			init.col = init.col - minCol;
			cave[init.row][init.col] = '#';
			
			for(int i=1; i<path.size(); i++)
			{
				Position point = path.get(i);
				point.col = point.col - minCol;
				
				if(point.col == init.col) //hoirzontally aligned
				{
					int start = Math.min(init.row, point.row);
					int end = Math.max(init.row, point.row);
					for(int r=start; r<=end; r++)
					{
						cave[r][init.col] = '#';
					}
				}
				else if(point.row == init.row) //vertically aligned
				{
					int start = Math.min(init.col, point.col);
					int end = Math.max(init.col, point.col);
					for(int c=start; c<=end; c++)
					{
						cave[init.row][c] = '#';
					}
				}
				
				init = point;
			}
		}
	}
	
	HashMap<Position,Position> firstBlocks = new HashMap<>();
	int falls = 0;
	
	@Override
	public void solveTask1()
	{
		createCave();
		
		boolean cont = true;
		while(cont)
		{
			cont = fallSand();
		}
		
		erg1 = falls;
	}
	

	public boolean fallSand()
	{
		lastLookUpPosition = sand;
		
		Position blockInCol = getFirstBlock(sand);
		
		Position fallPos = getFinalSand(blockInCol);
		if(!fallPos.equals(new Position(-1, -1)))
		{
			cave[fallPos.row][fallPos.col] = 'o';
			
			// update firstBlocks
			firstBlocks.get(lastLookUpPosition).row--;
			falls++;
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	public Position getFirstBlock(Position after) 
	{
//		if(!firstBlocks.containsKey(after)) 
		{
			int i = after.row;
			while(cave[i][after.col] == '.')
			{
//				cave[i][after.col] = 'o';
//				InputPrinter.printCharTable(cave);
//				try
//				{
//					Thread.sleep(300);					
//				}
//				catch(Exception e) {}
//				cave[i][after.col] = '.';
				i++;
			}		
			firstBlocks.put(after, new Position(i, after.col));
		}
		
		return firstBlocks.get(after);
	}
	
	
	Position lastLookUpPosition;
	public Position getFinalSand(Position p)
	{
		try
		{
			if(cave[p.row][(p.col-1)] == '.') //left is free for sand
			{
				Position drop = new Position(p.row, (p.col-1));
				Position blockInNewCol = getFirstBlock(drop);
				
				lastLookUpPosition = drop;
				return getFinalSand(blockInNewCol);
			}
			else if(cave[p.row][(p.col+1)] == '.') //right is free for sand
			{
				Position drop = new Position(p.row, (p.col+1));
				Position blockInNewCol = getFirstBlock(drop);
				
				lastLookUpPosition = drop;
				return getFinalSand(blockInNewCol);
			}
			else
			{
				return new Position(p.row-1, p.col);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			return new Position(-1, -1);
		}
	}
	

	@Override
	public void solveTask2()
	{
		firstBlocks = new HashMap<>();
		falls = 0;
		
		createGorundedCave();
		
		boolean cont = true;
		while(cont)
		{
			cont = fallSand();
		}
		
		erg2 = falls;
	}
	
	public static void main(String[] args)
	{
		new Dec14();
	}
}


