package Raetsel2022;

import java.util.ArrayList;

import Base.InputKind;
import Base.Position;

public class Dec17 extends Puzzle2022
{
	public Dec17()
	{
		super(17);
		readInput(InputKind.String);
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	
	ArrayList<Position> existingRocks = new ArrayList<>();
	int globalHighest = 0;

	@Override
	public void solveTask1()
	{}
	
	public boolean getSchnittmenge(ArrayList<Position> rock)
	{
		for(Position p: rock)
		{
			if(existingRocks.contains(p))
			{
				return true;
			}
		}
		return false;
	}
	
	public void updateExistingRocks(ArrayList<Position> rock)
	{
		int newRockHighest = Integer.MIN_VALUE;
		for(Position p: rock)
		{
			if(!existingRocks.contains(p))
			{
				existingRocks.add(p);
			}
			newRockHighest = Math.max(newRockHighest, p.row);
		}
		
		globalHighest = Math.max(newRockHighest, globalHighest);
	}
	
	public ArrayList<Position> appear(int type, int row)
	{
		ArrayList<Position> rock = new ArrayList<>();
		if(type == 0)
		{
//			####
			rock.add(new Position(row, 2));
			rock.add(new Position(row, 3));
			rock.add(new Position(row, 4));
			rock.add(new Position(row, 5));
		}
		else if(type == 1)
		{
//			.#.
//			###
//			.#.
			rock.add(new Position(row, 3));
			rock.add(new Position(row+1, 2));
			rock.add(new Position(row+1, 3));
			rock.add(new Position(row+1, 4));
			rock.add(new Position(row+2, 3));
		}
		else if(type == 2)
		{
//			..#
//			..#
//			###
			rock.add(new Position(row, 2));
			rock.add(new Position(row, 3));
			rock.add(new Position(row, 4));
			rock.add(new Position(row+1, 4));
			rock.add(new Position(row+2, 4));
		}
		else if(type == 3)
		{
//			#
//			#
//			#
//			#
			rock.add(new Position(row, 2));
			rock.add(new Position(row+1, 2));
			rock.add(new Position(row+2, 2));
			rock.add(new Position(row+3, 2));
		}
		else if(type == 4)
		{
//			##
//			##
			rock.add(new Position(row, 2));
			rock.add(new Position(row, 3));
			rock.add(new Position(row+1, 2));
			rock.add(new Position(row+1, 3));
		}
		
		return rock;
	}
	
	public ArrayList<Position> pushRight(ArrayList<Position> rock)
	{
		for(Position p: rock)
		{
			if(p.col==6)
			{
				return rock;
			}
		}
		for(Position p: rock)
		{
			p.col++;
		}
		return rock;
	}
	
	public ArrayList<Position> pushLeft(ArrayList<Position> rock)
	{
		for(Position p: rock)
		{
			if(p.col==0)
			{
				return rock;
			}
		}
		for(Position p: rock)
		{
			p.col--;
		}
		return rock;
	}
	
	public ArrayList<Position> fall(ArrayList<Position> rock)
	{
		for(Position p: rock)
		{
			p.row--;
		}
		return rock;
	}
	
	public ArrayList<Position> moveUp(ArrayList<Position> rock)
	{
		for(Position p: rock)
		{
			p.row++;
		}
		return rock;
	}
	

	ArrayList<Snapshot> pattern = new ArrayList<>();
	
	@Override
	public void solveTask2()
	{
		for(int i=0; i<7; i++)
		{
			existingRocks.add(new Position(0, i));
		}

		int cursor = 0; 
		long i = 0;
		final long END = 1000000000000L;
		long skippedHeightUnits = 0;
		
		while(i<END)
		{
			ArrayList<Position> rock = appear((int)(i%5), globalHighest+4);
			
			while(true)
			{
				if(inputString.charAt(cursor) == '<')
				{
					rock = pushLeft(rock);
					if(getSchnittmenge(rock))
					{
						rock = pushRight(rock);
					}
				}
				else
				{
					rock = pushRight(rock);
					if(getSchnittmenge(rock))
					{
						rock = pushLeft(rock);
					}
				}
				
				cursor = (cursor+1)%inputString.length();
				rock = fall(rock);
				if(getSchnittmenge(rock))
				{
					rock = moveUp(rock);
					updateExistingRocks(rock);
					
					Snapshot s = new Snapshot(getSnapshot(cursor, (int)(i%5)));
					
					
					if(pattern.contains(s) && i>=2022)
					{
						Snapshot old = pattern.get(pattern.indexOf(s));
						long difHeight = globalHighest - old.value.highest;
						long difIteration = i-old.value.i;
						
						long skippedIntervals = (END-i) / difIteration;
						skippedHeightUnits += skippedIntervals * difHeight;
						
						i += skippedIntervals * difHeight;
						
						if(i>=END)
						{
							erg2 = globalHighest + skippedHeightUnits;
							return;
						}
					}
					
					s.setValue(new SnapshotValue(i, globalHighest));
					pattern.add(s);
					
					break;
				}
				
			}
			i++;
			if(i==2022)
			{
				erg1 = globalHighest;
			}
		}
		
		erg2 = globalHighest + skippedHeightUnits;
	}
	
	public SnapshotKey getSnapshot(int cursor, int type)
	{
		int currHighest = Integer.MIN_VALUE;
		
		ArrayList<Position> res = new ArrayList<>();
		for(Position p: existingRocks)
		{
			if(currHighest - p.row <= 30)
			{
				res.add(new Position(currHighest-p.row, p.col));
			}
		}
		
		SnapshotKey ss = new SnapshotKey(cursor, type, res);
		return ss;
	}
	
	class SnapshotKey
	{
		int cursor;
		int type;
		ArrayList<Position> snapshot;
		
		public SnapshotKey(int cursor, int type, ArrayList<Position> snapshot)
		{
			this.cursor = cursor;
			this.type = type;
			this.snapshot = snapshot;
		}
		
		@Override
		public boolean equals(Object o)
		{
			SnapshotKey s = (SnapshotKey) o;
			
			if(s.snapshot.size() != snapshot.size())
				return false;
			
			for(Position p: this.snapshot)
			{	
				if(!s.snapshot.contains(p))
				{
					return false;
				}
			}
			
			return type == s.type && cursor == s.cursor;
		}
	}
	
	class SnapshotValue
	{
		long i;
		int highest;
		
		public SnapshotValue(long i, int highest)
		{
			this.i = i;
			this.highest = highest;
		}
	}
	
	class Snapshot
	{
		SnapshotKey key;
		SnapshotValue value;
		
		public Snapshot(SnapshotKey key)
		{
			this.key = key;
		}
		
		public void setValue(SnapshotValue val)
		{
			this.value = val;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			Snapshot s = (Snapshot) obj;
			return key.equals(s.key);
		}
	}
	
	public static void main(String[] args)
	{
		new Dec17();
	}
}

