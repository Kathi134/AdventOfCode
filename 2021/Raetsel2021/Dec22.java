package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dec22 extends Puzzle
{
	private ArrayList<Instruction> instructions = new ArrayList<>();
	boolean[][][] cube = new boolean[100][100][100]; //immer -50 -> 0=-50, 100=50
	long cntOn = 0;
	
	public void format()
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			String[] tmp = inputStringTable[i][1].split(","); //[x=1..45][y=-37..12][z=-24..22]
			for(int j=0; j<tmp.length; j++)
			{
				tmp[j] = tmp[j].substring(2, tmp[j].length());
			}
			int[] x = splitToIntegerArr(tmp[0], "\\.\\.");
			int[] y = splitToIntegerArr(tmp[1], "\\.\\.");
			int[] z = splitToIntegerArr(tmp[2], "\\.\\.");
			//int index = i;
			instructions.add(instructions.size(), new Instruction(inputStringTable[i][0].equals("on"), x[0], x[1], y[0], y[1], z[0], z[1]));
		}
	}
	
	public Dec22() throws IOException
	{
		super(22);
		readInput("StringTable divBy  ");
		format();
		
		computeSolution(1);
		erg1 = cntOn;
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Reactor Reboot",
				"number of light points in range -50..50", 
				"number of light points after all instructions");
	}
	
	public void solveTask1()
	{
		//convention: [y],[x],[z]
		for(int i=0; i<=19; i++)
		{
			Instruction instr = instructions.get(i);
			
			for(int y=instr.y[0]; y<=instr.y[1]; y++)
			{
				for(int x=instr.x[0]; x<=instr.x[1]; x++)
				{
					for(int z=instr.z[0]; z<=instr.z[1]; z++)
					{
						if(-50<=y && y<=50 && -50<=x && x<=50 && -50<=z && z<=50)
						{
							cube[y+50][x+50][z+50] = instr.on;
						}
					}
				}
			}
			//System.err.println(i);
		}
		
		for(int y=0; y<cube.length; y++)
		{
			for(int x=0; x<cube[y].length; x++)
			{
				for(int z=0; z<cube[y][x].length; z++)
				{
					if(cube[y][x][z])
					{
						cntOn++;
					}
				}
			}
		}
	}
	
	public void solveTask2()
	{
		ArrayList<Cube> lightCubes = new ArrayList<>();
		
		//arbeite alle instructions ab
		for (int i=0; i<instructions.size(); i++)
		{
			Instruction instr = instructions.get(i);
			Cube instrCube = new Cube (instr.x, instr.y, instr.z);
			
			//gehe alle leuchtenden cubes durch, und prüfe ob overlap
			for(int k=lightCubes.size()-1; k>=0; k--)
			{
				Cube c = lightCubes.get(k);
				if(c.overlap(instrCube))
				{
					lightCubes.remove(k); //entferne den alten cube
					lightCubes.addAll(c.split(instrCube)); //füge alle cubes die auf alten gesplittet werdne hinzu
				}
			}
			
			if(instr.on) //wenn hell, dann füge hinzu
			{
				lightCubes.add(instrCube);
			}
		}
		
		//berechne die anzahl light-points
		getNumberLits(lightCubes);
	}
	
	public void getNumberLits(ArrayList<Cube> lightCubes)
	{
		erg2 = 0;
		for(int i=0; i<lightCubes.size(); i++)
		{
			erg2 += lightCubes.get(i).getVolume();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec22();
	}
}

class Instruction
{
	int[] x;
	int[] y;
	int[] z;
	boolean on;
	
	public Instruction(boolean on, int startX, int endX, int startY, int endY, int startZ, int endZ)
	{
		x = new int[] {startX, endX};
		y = new int[] {startY, endY};
		z = new int[] {startZ, endZ};
		this.on = on;
	}
}

class Cube 
{
    public int xMin;
    public int xMax;
    public int yMin;
    public int yMax;
    public int zMin;
    public int zMax;

    public Cube(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) 
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }
    
    public Cube(int[] x, int[] y, int[] z)
    {
    	this.xMin = x[0];
    	this.xMax = x[1];
    	this.yMin = y[0];
    	this.yMax = y[1];
    	this.zMin = z[0];
    	this.zMax = z[1];
    }
    
    public boolean overlap(Cube c)
    {
    	boolean x = this.xMin <= c.xMax && this.xMax >= c.xMin;
    	boolean y = this.yMin <= c.yMax && this.yMax >= c.yMin;
    	boolean z = this.zMin <= c.zMax && this.zMax >= c.zMin;
    	
    	return x && y && z;    	
    }
    
    //erzeugt eine Liste mit allen cubes, die sich ergeben, wenn der andere hinzugefügt wird
    public List<Cube> split(Cube c)
    {
    	 List<Cube> cubes = new ArrayList<>();

         if (this.xMin < c.xMin) 
         {
             cubes.add(new Cube(this.xMin, c.xMin - 1, this.yMin, this.yMax, this.zMin, this.zMax));
             this.xMin = c.xMin;
         }
         if (this.xMax > c.xMax) 
         {
             cubes.add(new Cube(c.xMax + 1, this.xMax, this.yMin, this.yMax, this.zMin, this.zMax));
             this.xMax = c.xMax;
         }
         if (this.yMin < c.yMin) 
         {
             cubes.add(new Cube(this.xMin, this.xMax, this.yMin, c.yMin - 1, this.zMin, this.zMax));
             this.yMin = c.yMin;
         }
         if (this.yMax > c.yMax) 
         {
             cubes.add(new Cube(this.xMin, this.xMax, c.yMax + 1, this.yMax, this.zMin, this.zMax));
             this.yMax = c.yMax;
         }
         
         if (this.zMin < c.zMin)
         {
         	cubes.add(new Cube(this.xMin, this.xMax, this.yMin, this.yMax, this.zMin, c.zMin - 1));	
         }
         if (this.zMax > c.zMax)
         {
             cubes.add(new Cube(this.xMin, this.xMax, this.yMin, this.yMax, c.zMax + 1, this.zMax));
         }
         
         return cubes;
    }
    
    public long getVolume()
    {
    	long xLength = xMax - xMin + 1L;
    	long yLength = yMax - yMin + 1L;
    	long zLength = zMax - zMin + 1L;
    	return xLength * yLength * zLength;
    }
}