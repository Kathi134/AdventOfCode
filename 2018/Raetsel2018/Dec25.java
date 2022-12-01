package Raetsel2018;

import java.util.ArrayList;
import java.util.List;

import Base.InputKind;

public class Dec25 extends Puzzle2018
{
	List<Point> points;
	List<List<Point>> constellations;
	
	public Dec25()
	{
		super(25);
		readInputDivided(InputKind.intTable, ",");
		
		computeSolution(1);
		computeSolution(2);

		//printSolution();
		printFormattedSolution("Four-Dimensional Adventure", "Number of Constellations", "");
	}
	
	public void convertToPoints()
	{
		points = new ArrayList<>();
		for(int[] k: inputIntTable)
		{
			points.add(new Point(k));
		}
	}

	@Override
	public void solveTask1()
	{
		convertToPoints();
		
		constellations = new ArrayList<List<Point>>();
		
		for(int i=0; i<points.size(); i++)
		{
			Point p = points.get(i);
			
			boolean matchConst = false;
			List<Point> firstConstellationAdded = null;
			
			for(int j=0; j<constellations.size(); j++)
			{
				List<Point> c = constellations.get(j);
				
				for(int k=0; k<c.size(); k++)
				{
					Point s = c.get(k);
					
					if(p.computeManhattan(s) <= 3)
					{
						if(firstConstellationAdded == null)
						{
							c.add(p);
							matchConst = true;
							firstConstellationAdded = c;
						}
						else
						{
							firstConstellationAdded.addAll(c);
							constellations.remove(c);
							matchConst = true; // Angstcode -> müssste immer schon true sein wenn cs gemergt werden
						}
						break; //as soon as added to c -> jump to next c
					}
				}
			}
			
			if(!matchConst)
			{
				List<Point> newConst = new ArrayList<Point>();
				newConst.add(p);
				constellations.add(newConst);
			}
		}
		
		/*
		 * for each point:
		 * 		for any constellation:
		 * 			for any star:
		 * 				if star.manhatten < 3
		 * 				add this point to constellation
		 * 			keep looking in the following constellations -> dont break out
		 * 				(if any is connected, add whole collection to constellation)
		 * 		else:
		 * 			create new constellation
		 */
		
		erg1 = constellations.size();
	}
	

	@Override
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Dec25();
	}
}

class Point
{
	int[] coords = new int[4];
	
	public Point(int[] coords)
	{
		int i=0;
		for(int k: coords)
		{
			this.coords[i++] = k;
		}
	}
	
	public int computeManhattan(Point p)
	{
		//for each coord: how many steps to go to corresponding p.coord
		int sumMan = 0;
		for(int i=0; i<4; i++)
		{
			sumMan += Math.abs(this.coords[i] - p.coords[i]);
		}
		return sumMan; //count yourself
	}
}
