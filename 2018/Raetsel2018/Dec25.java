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

		printSolution();
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
		
		constellations = new ArrayList<>();
		
		for(int i=0; i<points.size(); i++)
		{
			Point p = points.get(i);
			
			boolean firstAdd = true;
			
			for(int j=0; j<constellations.size(); j++)
			{
				List<Point> c = constellations.get(j);
				
				for(int k=0; k<c.size(); k++)
				{
					Point s = c.get(k);
					if(firstAdd) //p is added to the first constellation matched
					{
						if(p.computeManhattan(s) <= 3)
						{
							c.add(p);
						}
					}
					else //p connects two constellations
					{
						
					}
				}
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
		 * 
		 * 
		 */
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
	int w;
	int x;
	int y;
	int z;
	int[] coords;
	
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
		return sumMan + 1; //count yourself
	}
}
