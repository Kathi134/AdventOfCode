package Raetsel2022;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

import Base.InputKind;
import Base.Position;

public class Dec15 extends Puzzle2022
{
	public Dec15()
	{
		super(15);
		readInputDivided(InputKind.StringTable, ":");

		if(lines > 20)
		{
			ROW_TO_SEARCH_IN = 2000000;
			COORD_LIMITATION = 4000000;
		}
		System.err.println("Takes about 10 seconds to compute");
		computeSolution(1);
		computeSolution(2);

		printFormattedSolution("Beacon Exclusive Zone", "occupied positions in row "+ROW_TO_SEARCH_IN, "tuning frequency of final Beacon");
	}
	

	public void read()
	{
		scanners = new ArrayList<>();
		
		for(String[] set : inputStringTable)
		{
			String coords = set[0].replace("Sensor at x=", "").replace(" y=", "");
			int[] tmp = Base.InputReader.splitToIntegerArr(coords, ",");
			Position scPos = new Position(tmp[1], tmp[0]);
			
			coords = set[1].replace(" closest beacon is at x=", "").replace(" y=", "");
			tmp = Base.InputReader.splitToIntegerArr(coords, ",");
			Position beacPos = new Position(tmp[1], tmp[0]);
			
			scanners.add(new Scanner(scPos, new Beacon(beacPos)));
		}
	}
	
	int ROW_TO_SEARCH_IN = 10;
	int COORD_LIMITATION = 20;

	List<Scanner> scanners;
	Map<Integer, List<HorizontalRange>> scanPerRow;

	@Override
	public void solveTask1()
	{
		read();
		scanPerRow = new TreeMap<>();
		
		for(Scanner s: scanners)
		{
			s.scan();
		}
		
		erg1 = getImpossibleBeacons(ROW_TO_SEARCH_IN);
	}
	
	public int getImpossibleBeacons(int row)
	{
		List<HorizontalRange> list = scanPerRow.get(row);
		
		Map<Integer, Boolean> isOccupied = new TreeMap<>();
		
		for(HorizontalRange h: list)
		{
			for(int j=h.colStart; j<=h.colEnd; j++)
			{
				isOccupied.putIfAbsent(j, true);
			}
		}
		
		for(Scanner s: scanners)
		{
			if(s.p.row == row)
			{
				isOccupied.putIfAbsent(s.p.col, true);
			}
			if(s.closestBeac.p.row == row)
			{
				isOccupied.remove(s.closestBeac.p.col);
			}
		}
		
		return isOccupied.size();
	}
	
	boolean [][] scanned = new boolean[COORD_LIMITATION+1][COORD_LIMITATION+1];
	
	@Override
	public void solveTask2()
	{
		Beacon finalBeacon;
		
		for(int i=0; i<=COORD_LIMITATION; i++)
		{
			//for(int j=0; j<=COORD_LIMITATION; j++)
			List<HorizontalRange> list = scanPerRow.get(i);
			if(list.size() == 2)
			{
				int posX = list.get(0).colEnd+1;
				finalBeacon = new Beacon(new Position(i, posX));
//				System.out.println(finalBeacon.p);
//				System.out.println("row:" +i + ", col:" + list);
				
				erg2 = (long)posX*4000000L +i;
			}
		}
	}

	public static void main(String[] args)
	{
		new Dec15();
	}
	
	class Beacon
	{
		Position p;
		
		public Beacon(Position p)
		{
			this.p = p;
		}
	}

	class Scanner
	{
		Position p;
		Beacon closestBeac;
		int man;
		
		public Scanner(Position p, Beacon closestBeac)
		{
			this.p = p;
			this.closestBeac = closestBeac;
		}

		public void scan()
		{
			man = p.computeManhattan(closestBeac.p);
			for(int i=0; i<=man; i++) 
			{
				List<HorizontalRange> l = scanPerRow.getOrDefault(p.row-i, new ArrayList<>());
//				l.add(new HorizontalRange(this, p.row-i, (p.col-man)+i, (p.col+man)-i));
				addRange(l, new HorizontalRange(this, p.row-i, (p.col-man)+i, (p.col+man)-i));
				scanPerRow.put(p.row-i, l);
				
				l = scanPerRow.getOrDefault(p.row+i, new ArrayList<>());
//				l.add(new HorizontalRange(this, p.row+i, (p.col-man)+i, (p.col+man)-i));
				addRange(l, new HorizontalRange(this, p.row+i, (p.col-man)+i, (p.col+man)-i));
				scanPerRow.put(p.row+i, l);
			}
		}
		
		private void addRange(List<HorizontalRange> l, HorizontalRange r)
		{
			l.add(r);
				
			mergeIfPossible(l);
		}
		
		private void mergeIfPossible(List<HorizontalRange> l)
		{
			boolean canMerge = l.size() > 1;
			while(canMerge)
			{
				for(HorizontalRange storedR: l)
				{
					canMerge = false;
					boolean merged = false;
					for(HorizontalRange possMerge : l)
					{
						if(storedR!=possMerge)
						{
							if(storedR.colEnd == possMerge.colStart-1)
							{
								//rechts ran pappen
								storedR.colEnd = possMerge.colEnd;
								l.remove(possMerge);
								merged = true;
								break;
							}
							else if(storedR.colStart == possMerge.colEnd+1)
							{
								//links ran pappen
								storedR.colStart = possMerge.colStart;
								l.remove(possMerge);
								merged = true;
								break;
							}
							else if(storedR.colStart <= possMerge.colStart && storedR.colEnd >= possMerge.colEnd)
							{
								//fully contained
								l.remove(possMerge);
								merged = true;
								break;
							}
							else if(storedR.colStart <= possMerge.colStart && storedR.colEnd >= possMerge.colStart)
							{
								//possMerge start is inside storedR
								storedR.colEnd = possMerge.colEnd;
								l.remove(possMerge);
								merged = true;
								break;
							}
							else if(storedR.colStart <= possMerge.colEnd && storedR.colEnd >= possMerge.colEnd)
							{
								//possMerge end is inside storedR
								storedR.colStart = possMerge.colStart;
								l.remove(possMerge);
								merged = true;
								break;
							}
						}
					}
					if(merged)
					{
						canMerge = true;
						break;
					}
				}
			}	
		}
	}
	
	class HorizontalRange
	{
		Scanner comesFrom;
		int row;
		int colStart;
		int colEnd;
		
		public HorizontalRange(Scanner comesFrom, int row, int colStart, int colEnd)
		{
			this.comesFrom = comesFrom;
			this.row = row;
			this.colStart = colStart;
			this.colEnd = colEnd;
		}
		
		public String toString()
		{
			return colStart + "-" + colEnd;
		}
	}
}

