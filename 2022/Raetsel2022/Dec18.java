package Raetsel2022;
import java.util.ArrayList;
import java.util.List;

import Base.InputKind;

//try filling each airpocket by adding the needed cube, then get surface again

public class Dec18 extends Puzzle2022
{
	public Dec18()
	{
		super(18);
		readInputDivided(InputKind.intTable, ",");
		
		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	ArrayList<Cube> cubes = new ArrayList<>();
	
	@Override
	public void solveTask1()
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			cubes.add(new Cube(inputIntTable[i][0], inputIntTable[i][1], inputIntTable[i][2]));
		}
		
		int res = 0;
		for(Cube c: cubes)
		{
			res += c.getEmptySides(cubes);
		}
		erg1 = res;
	}

	@Override
	public void solveTask2()
	{
//		replaceAirPockets();
		int res = 0;
		for(Cube c: cubes)
		{
			res += c.getEmptySides(cubes);
		}
		erg2 = res;
	}
	
	public void replaceAirPockets()
	{
		for(int x=0; x<= 25; x++)
		{
			for(int y=0; y<= 25; y++)
			{
				for(int z=0; z<= 25; z++)
				{
					Cube c = new Cube(x, y, z);
					System.out.println(c);
					if(cubes.contains(c))
						continue;
					
					boolean isPocket = Cube.isAirPocket(c, cubes);
					
					if(isPocket)
					{
						List<Cube> queue = new ArrayList<>();
						queue.add(c);
						
						while(queue.size()>0)
						{
							Cube pocket = queue.remove(0);
							if(!cubes.contains(pocket))
								cubes.add(pocket);
							
							for(Cube neighbour: pocket.getRequiredSides())
							{
								if(!cubes.contains(neighbour) && !queue.contains(neighbour))
									queue.add(neighbour);
							}
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Dec18();
	}
}

class Cube
{
	int x;
	int y;
	int z;
	Cube[] requiredSides = new Cube[6];
	
	public Cube (int x, int y, int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Cube[] getRequiredSides()
	{
		if(requiredSides[0] != null)
			return requiredSides;
			
		requiredSides[0] = new Cube(x-1,y,z);
		requiredSides[1] = new Cube(x+1,y,z);
		requiredSides[2] = new Cube(x,y-1,z);
		requiredSides[3] = new Cube(x,y+1,z);
		requiredSides[4] = new Cube(x,y,z-1);
		requiredSides[5] = new Cube(x,y,z+1);
		
		return requiredSides;
	}
	
	public int getEmptySides(ArrayList<Cube> cubes)
	{		
		int res = 0;
		
		for(Cube c: getRequiredSides())
		{
			if(!cubes.contains(c) && isAirPocket(c, cubes))
			{
				res++;
			}
		}
		
		return res;
	}
	
	public static boolean isAirPocket(Cube c, ArrayList<Cube> cubes)
	{
		boolean[] sides = new boolean[6];
		
		int i = 1;
		while(i<=25)
		{
			if(sides[0]==false && cubes.contains(new Cube(c.x-i, c.y, c.z)))
			{
				sides[0] = true;
			}
			if(sides[1]==false && cubes.contains(new Cube(c.x+i, c.y, c.z)))
			{
				sides[1] = true;
			}
			if(sides[2]==false && cubes.contains(new Cube(c.x, c.y-i, c.z)))
			{
				sides[2] = true;
			}
			if(sides[3]==false && cubes.contains(new Cube(c.x, c.y+i, c.z)))
			{
				sides[3] = true;
			}
			if(sides[4]==false && cubes.contains(new Cube(c.x, c.y, c.z-i)))
			{
				sides[4] = true;
			}
			if(sides[5]==false && cubes.contains(new Cube(c.x, c.y, c.z+i)))
			{
				sides[5] = true;
			}
			i++;
		}
		
		for(boolean s: sides)
		{
			if(!s)
				return s;
		}
		System.out.println(c);
		return true;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Cube c = (Cube) obj;
		return c.x == x && c.y==y && c.z == z;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y +"," + z + ")";
	}
}


